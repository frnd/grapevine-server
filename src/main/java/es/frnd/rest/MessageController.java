package es.frnd.rest;

import es.frnd.model.Message;
import es.frnd.model.MessagePreview;
import es.frnd.model.Subscription;
import es.frnd.notif.PushCGM;
import es.frnd.repository.MessageRepository;
import es.frnd.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

/**
 * Messages controller
 */
@RestController
@RequestMapping("/messages")
@SuppressWarnings("unused")
public class MessageController {

    private MessageRepository messageRepository;

    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository, SubscriptionRepository subscriptionRepository) {
        this.messageRepository = messageRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Message> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "50") Integer size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort("serverDate"));
        return messageRepository.findByParentIsNull(pageRequest);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Message getById(@PathVariable("id") Message resource) {
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Message post(@RequestBody Message message) {
        Message loadedResource = messageRepository.findByUriAndParentIsNull(message.getUri());
        if (loadedResource == null) {
            loadedResource = messageRepository.save(message);
        }
        return loadedResource;
    }

    @RequestMapping(value = "/{id}/responses", method = RequestMethod.GET)
    public Page<Message> getAllMessagesByResource(@PathVariable("id") Message parent,
                                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "50") Integer size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort("serverDate"));

        return messageRepository.findAllByParent(parent, pageRequest);
    }

    @PushCGM
    @MessageMapping("/messages/{id}/responses")
    @RequestMapping(value = "/{id}/responses", method = RequestMethod.POST)
    public Message send(@PathVariable("id") @DestinationVariable("id") String id,
                        @RequestBody @Payload Message message) {
        // NOTE: Using a formatter or a converter is not working in websokets
        Message parent = messageRepository.findOne(id);

        // Save message
        message.setParent(parent);
        message = messageRepository.save(message);

        // Update parent
        MessagePreview messagePreview = new MessagePreview(message.getId(),
                message.getUri(),
                message.getUser(),
                message.getText(),
                message.getSentDate(),
                message.getTags());
        parent.getLatest().add(messagePreview);
        messageRepository.save(parent);

        return message;
    }

    @RequestMapping(value = "/{id}/subscriptions", method = RequestMethod.POST)
    public Message post(@PathVariable("id") Message resource, String deviceId) {

        Subscription subscription = new Subscription(resource, null, deviceId);
        subscriptionRepository.save(subscription);


        return resource;
    }
}
