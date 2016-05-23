package es.frnd.rest;

import es.frnd.model.Message;
import es.frnd.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Message> getAll(@PathParam("page") Integer page, @PathParam("size") Integer size) {
        PageRequest pageRequest = new PageRequest(page, page, new Sort("serverDate"));
        Page<Message> resources = messageRepository.findByParentIsNull(pageRequest);
        return resources;
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
        Page<Message> allByResource = messageRepository.findAllByParent(parent, pageRequest);

        return allByResource;
    }

    @RequestMapping(value = "/{id}/subscriptors", method = RequestMethod.POST)
    public Message post(@PathVariable("id") Message resource, String deviceId) {

        //TODO: subscribe to a topic in GCM and create a collection to trace subscriptions.

        return resource;
    }

    @MessageMapping("/messages/{id}")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Message send(@PathVariable("id") @DestinationVariable("id") String id,
                        @RequestBody @Payload Message message) {
        // XXX: Using a formatter or a converter is not working in websokets

        Message parent = messageRepository.findOne(id);

        message.setParent(parent);
        System.out.println("message = " + message);

        return messageRepository.save(message);
    }
}
