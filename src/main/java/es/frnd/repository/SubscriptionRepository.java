package es.frnd.repository;

import es.frnd.model.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for the subscriptions
 */
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
}
