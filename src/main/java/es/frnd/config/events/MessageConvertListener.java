package es.frnd.config.events;

import com.mongodb.DBObject;
import es.frnd.model.Message;
import es.frnd.model.MessagePreview;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings({"unchecked", "unused"})
public class MessageConvertListener extends AbstractMongoEventListener<Message> {

    /**
     * Max size por the preview messages list.
     */
    private int maxSize;

    @Autowired
    public MessageConvertListener(@Value("${messages.maxCacheSize}") int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Makes sure the list has at maximum of {@link MessageConvertListener#maxSize} items. Removes elements form the
     * beginning leaving latest messages.
     *
     * @param event mongo event data.
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Message> event) {
        Message message = event.getSource();

        if (message.getLatest() != null) {
            int size = message.getLatest().size();
            if (size > maxSize) {
                int from = size - maxSize;
                List<MessagePreview> choppedList = message.getLatest().subList(from, size);
                message.setLatest(choppedList);
            }
        }

    }

    @Override
    public void onBeforeSave(BeforeSaveEvent<Message> event) {
        Message source = event.getSource();
        DBObject dbObject = event.getDBObject();

        ObjectId root = null;
        if (source.getRoot() != null) {
            root = new ObjectId(source.getRoot());
        } else if (source.getParent() != null) {
            root = new ObjectId(source.getParent().getRoot());
            source.setRoot(source.getParent().getRoot());
        }
        if (root != null) {
            dbObject.put("root", root);
        }
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<Message> event) {
        DBObject dbObject = event.getDBObject();
        Message source = event.getSource();

        ObjectId root = (ObjectId) dbObject.get("root");
        Object id = dbObject.get("_id");
        source.setRoot((root != null ? root : id).toString());
    }
}