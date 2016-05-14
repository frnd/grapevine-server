package es.frnd.rest;

import es.frnd.model.Message;
import es.frnd.model.Resource;
import es.frnd.repository.MessageRepository;
import es.frnd.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fernando on 1/05/16.
 */
@RestController
@RequestMapping("/resources")
@SuppressWarnings("unused")
public class ResourceController {

    private ResourceRepository resourceRepository;

    private MessageRepository messagesRepository;

    @Autowired
    public ResourceController(ResourceRepository resourceRepository, MessageRepository messagesRepository) {
        this.resourceRepository = resourceRepository;
        this.messagesRepository = messagesRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Resource> getAll(Pageable pageable){
        Page<Resource> resources = resourceRepository.findAll(pageable);
        return resources;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource getById(@PathVariable("id") Resource resource){
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Resource post(@RequestBody Resource resource){

        Resource loadedResource = resourceRepository.findByUri(resource.getUri());
        if(loadedResource == null){
            loadedResource = resourceRepository.save(resource);
        }

        return loadedResource;
    }

    @RequestMapping(value = "/{id}/messages", method = RequestMethod.GET)
    public Page<Message> getAllMessagesByResource(@PathVariable("id") Resource resource, Pageable pageable){

        Page<Message> allByResource = messagesRepository.findAllByResource(resource, new PageRequest(0, 20));

        return allByResource;
    }

    @RequestMapping(value = "/{id}/subscriptors", method = RequestMethod.POST)
    public Resource post(@PathVariable("id") Resource resource, String deviceId){

        resource.getSubscribers().add(deviceId);

        resourceRepository.save(resource);

        return resource;
    }
}
