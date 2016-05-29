package es.frnd.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

/**
 * Subscription to a message for a user.
 */
@SuppressWarnings("unused")
public class Subscription {

    private String id;

    /**
     * THe message the user is subscribed to
     */
    @DBRef
    private Message to;

    /**
     * User that is subscribed
     */
    private String user;

    /**
     * Device Id the user used to subscribe
     */
    private String deviceId;

    /**
     * The date the user makes subscription
     */
    private Date date;

    public Subscription(Message to, String user, String deviceId) {
        this.to = to;
        this.user = user;
        this.deviceId = deviceId;
    }

    public Subscription() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message getTo() {
        return to;
    }

    public void setTo(Message to) {
        this.to = to;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
