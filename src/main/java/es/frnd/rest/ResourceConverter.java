package es.frnd.rest;

import es.frnd.model.Resource;
import es.frnd.repository.ResourceRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * Created by fernando on 18/05/16.
 */
public class ResourceConverter implements Converter<String, Resource> {

    private ResourceRepository repository;

    public ResourceConverter(ResourceRepository repository) {
        this.repository = repository;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Resource convert(String source) {
        return repository.findOne(source);
    }
}
