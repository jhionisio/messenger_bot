package meta.messengerbot.usecase;

import meta.messengerbot.domain.Message;
import meta.messengerbot.domain.Messaging;
import meta.messengerbot.domain.enums.MessageType;
import meta.messengerbot.repository.MessageRepository;
import meta.messengerbot.usecase.processor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        try {
            messageRepository.save(message);

            MessageType messageType = determineMessageType(message);
            messageProcessor.process(message, messageType);
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }

    private MessageType determineMessageType(Message message) {
        try {
            List<Messaging> messagingList = message.getMessaging();
            for (Messaging messaging : messagingList) {
                if (messaging.getMessageContent() != null) {
                    return MessageType.TEXT;
                }
                if (messaging.getPostback() != null) {
                    return MessageType.POSTBACK;
                }
            }
        } catch (Exception e) {
            logger.error("Error determining message type", e);
        }
        throw new IllegalArgumentException("Unsupported message format");
    }

    public void addSentMessage(Message message) {
        message.setSentByBot(true);

        try {
            messageRepository.save(message);
        } catch (Exception e) {
            logger.error("Error saving sent message", e);
        }
    }
}