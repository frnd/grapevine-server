package es.frnd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * A messaqe on the platform. It can be a top level message where parent is null or a reply to another message.
 */
@Document
@SuppressWarnings("unused")
public class Message {

    @Id
    private String id;

    /**
     * The resource uri.
     */
    @Indexed(unique = true)
    private String uri;

    /**
     * User that posted the message
     */
    private String user;

    /**
     * Message text
     */
    private String text;

    /**
     * List of tags for the message
     */
    @Indexed
    private List<String> tags;

    /**
     * Parent message
     */
    @DBRef(lazy = true)
    private Message parent;

    /**
     * Latest messages on this message this serve as a cache for fast loading of replies.
     */
    private List<Message> latest;

    /**
     * Date the message was sent on user timezone.
     */
    private Date sentDate;

    /**
     * Server date when teh message was processed.
     */
    private Date serverDate;

    @PersistenceConstructor
    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Message getParent() {
        return parent;
    }

    public void setParent(Message parent) {
        this.parent = parent;
    }

    public List<Message> getLatest() {
        return latest;
    }

    public void setLatest(List<Message> latest) {
        this.latest = latest;
    }

    public Date getDate() {
        return sentDate;
    }

    public void setDate(Date date) {
        this.sentDate = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("displayName='").append(user).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
