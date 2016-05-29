package es.frnd.notif;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import es.frnd.config.logging.InjectLoger;
import es.frnd.model.Message;
import org.apache.commons.lang3.Validate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
public class GCMPushAspect {

    @InjectLoger
    private Logger logger;

    /**
     * Google GCM sender
     */
    private Sender sender;

    private ObjectMapper jacksonObjectMapper;

    private boolean active;

    private boolean delayWhileIdle;

    private boolean dryRun;

    @Value("${gcm.ttl}")
    private int ttl;

    @Autowired
    public GCMPushAspect(Sender sender,
                         ObjectMapper jacksonObjectMapper,
                         @Value("${gcm.active}") boolean active,
                         @Value("${gcm.delayWhileIdle}") boolean delayWhileIdle,
                         @Value("${gcm.dryRun}") boolean dryRun,
                         @Value("${gcm.ttl}") int ttl) {
        this.sender = sender;
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.active = active;
        this.delayWhileIdle = delayWhileIdle;
        this.dryRun = dryRun;
        this.ttl = ttl;
    }

    /**
     * Sending message to GCM topic
     *
     * @param joinPoint  execution pointcut.
     * @param annotation annotation data
     * @param message    THe message to send
     */
    @AfterReturning(pointcut = "execution(public * *(..)) && @annotation(annotation))", returning = "message")
    public void sendToGCM(JoinPoint joinPoint, PushCGM annotation, Message message) {
        if (active) {
            try {
                String id = message.getRoot();
                Validate.notBlank(id, "Root message Id can not be blank");
                logger.info("Sending push to topic {}", id);
                com.google.android.gcm.server.Message gcmMessage;
                String messageAsString = jacksonObjectMapper.writeValueAsString(message);
                gcmMessage = new com.google.android.gcm.server.Message.Builder()
                        .delayWhileIdle(delayWhileIdle)
                        .dryRun(dryRun)
                        .timeToLive(ttl)
                        .addData("message", messageAsString)
                        .build();
                Result result = sender.sendNoRetry(gcmMessage, "/topics/" + id);
                Validate.notNull(result.getMessageId(), "Error sending message %s", result.getErrorCodeName());
            } catch (IOException e) {
                logger.error("Error sending push", e);
            }
        } else {
            logger.warn("Push to GCM not enabled");
        }
    }

}