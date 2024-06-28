package meta.messengerbot.controller;

import com.mongodb.client.MongoDatabase;
import meta.messengerbot.config.MongoConfig;
import meta.messengerbot.controller.converter.MessageConverterToDomain;
import meta.messengerbot.controller.dto.MessageDTO;
import meta.messengerbot.usecase.MessageUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/webhook")
public class MessageController {

    private final MessageUseCase messageUseCase;
    private final MessageConverterToDomain messageConverterToDomain;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Value("${facebook.page.access.token}")
    private String accessToken;

    @Autowired
    private MongoConfig mongoConfig;

    @Autowired
    public MessageController(MessageUseCase messageUseCase, MessageConverterToDomain messageConverterToDomain) {
        this.messageUseCase = messageUseCase;
        this.messageConverterToDomain = messageConverterToDomain;
    }

    @GetMapping
    public String verifyWebhook(@RequestParam("hub.mode") String mode,
                                @RequestParam("hub.challenge") String challenge,
                                @RequestParam("hub.verify_token") String token) {
        if (mode.equals("subscribe") && token.equals(accessToken)) {
            return challenge;
        } else {
            return "Verification failed";
        }
    }

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody MessageDTO messageDTO) {
        try {
            messageUseCase.processMessage(messageConverterToDomain.toDomain(messageDTO));
            return ResponseEntity.ok("EVENT_RECEIVED");
        } catch (Exception e) {
            logger.error("Error receiving message", e);
            return ResponseEntity.status(500).body("ERROR");
        }
    }

}