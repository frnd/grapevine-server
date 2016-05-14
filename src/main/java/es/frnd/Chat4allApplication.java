package es.frnd;

import es.frnd.model.Message;
import es.frnd.model.Resource;
import es.frnd.repository.MessageRepository;
import es.frnd.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

@SpringBootApplication
@Import(value = {WebMvcContext.class, WebSocketConfig.class})
public class Chat4allApplication implements CommandLineRunner {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MessageRepository messagesRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Chat4allApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        try {
            resourceRepository.save(new Resource("http://www.google.com/hello", "test1"));
        } catch (Exception e) {

        }

        try {
            resourceRepository.save(new Resource("http://www.google.com/bye", "test1"));
        } catch (Exception e) {

        }

        Resource resource = resourceRepository.findByUri("http://www.google.com/hello");
        System.out.println("resource = " + resource);

        Message message = new Message("frnd", "hello", new Date(), resource);
        messagesRepository.save(message);

        resource = resourceRepository.findByUri("http://www.google.com/bye");
        System.out.println("resource = " + resource);

        message = new Message("frnd", "bye", new Date(), resource);
        messagesRepository.save(message);

        Page<Message> allByResource = messagesRepository.findAllByResource(resource, new PageRequest(0, 5));
        System.out.println("allByResource = " + allByResource);

    }


}
