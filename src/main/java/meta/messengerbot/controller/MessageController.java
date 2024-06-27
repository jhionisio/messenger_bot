package meta.messengerbot.controller;

import meta.messengerbot.controller.converter.MessageConverterToDomain;
import meta.messengerbot.controller.dto.MessageDTO;
import meta.messengerbot.usecase.MessageUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageUseCase messageUseCase;
    private final MessageConverterToDomain messageConverter;

    @Autowired
    public MessageController(MessageUseCase messageUseCase, MessageConverterToDomain messageConverter) {
        this.messageUseCase = messageUseCase;
        this.messageConverter = messageConverter;
    }

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody MessageDTO messageDTO) {
        messageUseCase.processMessage(messageConverter.toDomain(messageDTO));
        return ResponseEntity.ok("EVENT_RECEIVED");
    }
}