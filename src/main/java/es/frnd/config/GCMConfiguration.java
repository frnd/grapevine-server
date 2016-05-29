package es.frnd.config;

import com.google.android.gcm.server.Sender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

/**
 * Configures teh firebase application
 */
@Configuration
public class GCMConfiguration {

    @Value("${gcm.apiKey}")
    private String apikey;

    @Bean
    public Sender getSender() throws FileNotFoundException {
        return new Sender(apikey);
    }
}
