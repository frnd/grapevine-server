package es.frnd.model;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Date;
import java.util.List;

/**
 * Message preview for the list of latest messages in {@link Message}
 */
public class MessagePreview {
    private String id;

    private String uri;

    private String user;

    private String text;

    private Date date;

    private List<String> tags;

    @PersistenceConstructor
    public MessagePreview(String id, String uri, String user, String text, Date date, List<String> tags) {
        this.id = id;
        this.uri = uri;
        this.user = user;
        this.text = text;
        this.date = date;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getTags() {
        return tags;
    }
}
