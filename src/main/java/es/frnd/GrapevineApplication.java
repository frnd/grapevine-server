package es.frnd;

import es.frnd.model.Message;
import es.frnd.rest.MessageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.Arrays;

@SpringBootApplication
public class GrapevineApplication implements CommandLineRunner {

    @Autowired
    private MessageController controller;

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(GrapevineApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Message entity = new Message();
        entity.setUri("http://localhost");
        entity.setText("Random text");
        entity.setUser("user");
        entity.setTags(Arrays.asList(new String[] {"test", "local"}));
        controller.post(entity);
    }


}
