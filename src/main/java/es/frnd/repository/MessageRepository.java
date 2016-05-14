package es.frnd.repository;

import es.frnd.model.Message;
import es.frnd.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fernando on 27/04/16.
 */
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    Page<Message> findAllByResource(Resource resource, Pageable pageable);
}