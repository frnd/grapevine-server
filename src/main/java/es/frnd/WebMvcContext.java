package es.frnd;

import es.frnd.repository.ResourceRepository;
import es.frnd.rest.ResourceFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by fernando on 10/05/16.
 */
@Configuration
class WebMvcContext extends WebMvcConfigurerAdapter {

    @Autowired
    private ResourceRepository resourceRepository;

    public void addFormatters(FormatterRegistry registry){
        registry.addFormatter(new ResourceFormatter(resourceRepository));
    }
}
