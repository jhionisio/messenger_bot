package meta.messengerbot.usecase;

import meta.messengerbot.domain.Message;
import meta.messengerbot.domain.enums.MessageType;
import meta.messengerbot.repository.MessageRepository;
import meta.messengerbot.usecase.processor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageUseCase {

    private static final Logger logger = LoggerFactory.getLogger(MessageUseCase.class);
    private final MessageRepository messageRepository;
    private final MessageProcessor messageProcessor;

    @Autowired
    public MessageUseCase(MessageRepository messageRepository, MessageProcessor messageProcessor) {
        this.messageRepository = messageRepository;
        this.messageProcessor = messageProcessor;
    }

    public void processMessage(Message message) {
        logger.info("Received message: {}", message);

        messageRepository.save(message);

        MessageType messageType = determineMessageType(message);
        messageProcessor.process(message, messageType);
    }

    private MessageType determineMessageType(Message message) {
        if (message.getMessaging().containsKey("message")) {
            return MessageType.TEXT;
        } else if (message.getMessaging().containsKey("postback")) {
            return MessageType.POSTBACK;
        } else {
            throw new IllegalArgumentException("Unsupported message format");
        }
    }

    public void addSentMessage(Message message) {
        message.setSentByBot(true);

        messageRepository.save(message);
    }
}