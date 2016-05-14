package es.frnd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 27/04/16.
 */
@SuppressWarnings("unused")
@Document
public class Resource {

    private @Id String id;

    /**
     * The resource uri.
     */
    @Indexed(unique = true)
    private String uri;

    /**
     * Description for the resource.
     */
    private String description;

    /**
     * List of tags for the resource
     */
    @Indexed
    private List<String> tags;

    /**
     * Latest messages on this resource
     */
    private List<Message> latest = new ArrayList<>();

    /**
     * Subscribers that wil receive a push notification on new comments for the resource.
     */
    private List<String> subscribers = new ArrayList<>();

    public Resource() {
    }

    public String getId() {
        return id;
    }

    public Resource(String uri, String description) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Message> getLatest() {
        return latest;
    }

    public List<String> getSubscribers() {
        return subscribers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return uri.equals(resource.uri);

    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Resource{");
        sb.append("uri='").append(uri).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", tags=").append(tags);
        sb.append(", latest=").append(latest);
        sb.append(", subscribers=").append(subscribers);
        sb.append('}');
        return sb.toString();
    }
}


