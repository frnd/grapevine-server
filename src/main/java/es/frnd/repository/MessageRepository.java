package es.frnd.repository;

import es.frnd.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for messages
 */
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    Message findByUri(String uri);

    Page<Message> findByParentIsNull(Pageable pageable);

    Page<Message> findAllByParent(Message resource, Pageable pageRequest);
}
