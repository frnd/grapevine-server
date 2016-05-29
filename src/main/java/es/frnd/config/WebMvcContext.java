package es.frnd.config;

import es.frnd.repository.MessageRepository;
import es.frnd.rest.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by fernando on 10/05/16.
 */
@Configuration
class WebMvcContext extends WebMvcConfigurerAdapter {

    @Autowired
    private MessageRepository repository;

    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new MessageFormatter(repository));
    }

}
