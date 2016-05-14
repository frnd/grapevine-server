package es.frnd.rest;

import es.frnd.model.Resource;
import es.frnd.repository.ResourceRepository;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by fernando on 10/05/16.
 */
public class ResourceFormatter implements Formatter<Resource> {

    private ResourceRepository repository;

    public ResourceFormatter(ResourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Resource parse(String s, Locale locale) throws ParseException {
        return repository.findOne(s);
    }

    @Override
    public String print(Resource resource, Locale locale) {
        return resource.getId();
    }
}
