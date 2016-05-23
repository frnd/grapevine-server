package es.frnd;

import es.frnd.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {WebMvcContext.class, WebSocketConfig.class})
public class Chat4allApplication implements CommandLineRunner {

    @Autowired
    private MessageRepository messagesRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Chat4allApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {


    }


}
