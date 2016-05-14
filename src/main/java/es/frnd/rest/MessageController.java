package es.frnd.rest;

import es.frnd.model.Message;
import es.frnd.model.Resource;
import es.frnd.repository.MessageRepository;
import es.frnd.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fernando on 1/05/16.
 */
@RestController
@RequestMapping("/messages")
@SuppressWarnings("unused")
public class MessageController {

    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/messages/{resourceId}")
    @RequestMapping(value = "/{resourceId}", method = RequestMethod.POST)
    public Message send(@PathVariable("resourceId") Resource resource, @RequestBody Message message){

        message.setResource(resource);

        return messageRepository.save(message);
    }
}
