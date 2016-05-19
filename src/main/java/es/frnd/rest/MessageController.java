package es.frnd.rest;

import es.frnd.model.Message;
import es.frnd.model.Resource;
import es.frnd.repository.MessageRepository;
import es.frnd.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fernando on 1/05/16.
 */
@RestController
@RequestMapping("/messages")
@SuppressWarnings("unused")
public class MessageController {

    private MessageRepository messageRepository;

    private ResourceRepository resourceRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository, ResourceRepository resourceRepository) {
        this.messageRepository = messageRepository;
        this.resourceRepository = resourceRepository;
    }

    // TODO: Using a formatter or a converter is not working in websokets
    @MessageMapping("/messages/{resourceId}")
    @RequestMapping(value = "/{resourceId}", method = RequestMethod.POST)
    public Message send(@PathVariable("resourceId") @DestinationVariable("resourceId") String resourceId,
                        @RequestBody @Payload Message message) {

        Resource resource = resourceRepository.findOne(resourceId);

        message.setResource(resource);

        return messageRepository.save(message);
    }
}
