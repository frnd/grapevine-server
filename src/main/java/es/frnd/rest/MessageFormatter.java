package es.frnd.rest;

import es.frnd.model.Message;
import es.frnd.repository.MessageRepository;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by fernando on 10/05/16.
 */
public class MessageFormatter implements Formatter<Message> {

    private MessageRepository repository;

    public MessageFormatter(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message parse(String s, Locale locale) throws ParseException {
        return repository.findOne(s);
    }

    @Override
    public String print(Message resource, Locale locale) {
        return resource.getId();
    }
}
