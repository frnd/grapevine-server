package es.frnd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A message on the platform. It can be a top level message where parent is null or a reply to another message.
 */
@Document
@SuppressWarnings("unused")
public class Message {

    @Id
    private String id;

    /**
     * The resource uri.
     */
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
    @JsonIgnore
    private Message parent;

    /**
     * Object Id for the initial message (root) on the messages tree.
     */
    @Transient
    private String root;

    /**
     * Latest messages on this message this serve as a cache for fast loading of replies.
     */
    private List<MessagePreview> latest;

    /**
     * Date the message was sent on user timezone.
     */
    private Date sentDate;

    /**
     * Server date when the message was processed.
     */
    private Date serverDate;

    @PersistenceConstructor
    public Message() {
        latest = new ArrayList<>();
        tags = new ArrayList<>();
    }

    public Message(String user, String text, String uri) {
        this.user = user;
        this.text = text;
        this.uri = uri;
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

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @JsonProperty(value = "date")
    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @JsonIgnore
    public Date getServerDate() {
        return serverDate;
    }

    public void setServerDate(Date serverDate) {
        this.serverDate = serverDate;
    }

    public List<MessagePreview> getLatest() {
        return latest;
    }

    public void setLatest(List<MessagePreview> latest) {
        this.latest = latest;
    }

    @Override
    public String toString() {
        return "Message{" + "displayName='" + user + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
