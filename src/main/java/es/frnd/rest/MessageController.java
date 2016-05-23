package es.frnd.rest;

import com.google.common.collect.EvictingQueue;
import es.frnd.model.Message;
import es.frnd.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
 * Messages controller
 */
@RestController
@RequestMapping("/messages")
@SuppressWarnings("unused")
public class MessageController {

    private MessageRepository messageRepository;

    @Value("${messages.maxCacheSize}")
    private int maxSize;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Message> getAll(@PathParam("page") Integer page, @PathParam("size") Integer size) {
        PageRequest pageRequest = new PageRequest(page, page, new Sort("serverDate"));
        return messageRepository.findByParentIsNull(pageRequest);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Message getById(@PathVariable("id") Message resource) {
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Message post(@RequestBody Message message) {
        Message loadedResource = messageRepository.findByUri(message.getUri());
        if (loadedResource == null) {
            loadedResource = messageRepository.save(message);
        }
        return loadedResource;
    }

    @RequestMapping(value = "/{id}/responses", method = RequestMethod.GET)
    public Page<Message> getAllMessagesByResource(@PathVariable("id") Message parent, @PathParam("page") Integer page, @PathParam("size") Integer size) {
        PageRequest pageRequest = new PageRequest(page, page, new Sort("serverDate"));

        return messageRepository.findAllByParent(parent, pageRequest);
    }

    @MessageMapping("/messages/{id}/responses")
    @RequestMapping(value = "/{id}/responses", method = RequestMethod.POST)
    public Message send(@PathVariable("id") @DestinationVariable("id") String id,
                        @RequestBody @Payload Message message) {
        // XXX: Using a formatter or a converter is not working in websokets

        Message parent = messageRepository.findOne(id);

        message.setParent(parent);
        message = messageRepository.save(message);
        EvictingQueue<Message> queue = EvictingQueue.create(maxSize);
        queue.addAll(message.getLatest());
        queue.add(message);
        parent.setLatest(new ArrayList(queue));
        messageRepository.save(parent);
        System.out.println("message = " + message);
        return message;
    }

    @RequestMapping(value = "/{id}/subscriptions", method = RequestMethod.POST)
    public Message post(@PathVariable("id") Message resource, String deviceId) {

        //TODO: subscribe to a topic in GCM and create a collection to trace subscriptions.

        return resource;
    }
}
