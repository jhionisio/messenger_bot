package meta.messenger_bot.usecase;

import meta.messenger_bot.domain.MessageDomain;
import meta.messenger_bot.domain.massaging.Messaging;
import meta.messenger_bot.repository.MessageRepository;
import meta.messenger_bot.usecase.service.HttpService;
import meta.messenger_bot.usecase.service.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

import java.util.*;

@Service
public class ResponseUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUseCase.class);
    private final MessageRepository messageRepository;
    private final MessageBuilder messageBuilder;
    private final HttpService httpService;

    @Autowired
    public ResponseUseCase(MessageRepository messageRepository, MessageBuilder messageBuilder, HttpService httpService) {
        this.messageRepository = messageRepository;
        this.messageBuilder = messageBuilder;
        this.httpService = httpService;
    }

    public void sendTextMessage(String recipientId, String text) {
        try {
            Map<String, Object> messageContent = messageBuilder.buildTextMessage(recipientId, text);
            httpService.sendMessage(messageContent);
            saveTextMessageHistory(messageContent, recipientId);
        } catch (Exception e) {
            logger.error("Error sending text message", e);
        }
    }

    public void sendButtonMessage(String recipientId, String text, List<Map<String, String>> buttons) {
        try {
            Map<String, Object> messageContent = messageBuilder.buildButtonMessage(recipientId, text, buttons);
            httpService.sendMessage(messageContent);
            saveButtonMessageHistory( messageContent, recipientId);
        } catch (Exception e) {
            logger.error("Error sending button message", e);
        }
    }

    public MessageDomain returnTextMessage(String recipientId, String text) {
            Map<String, Object> messageContent = messageBuilder.buildTextMessage(recipientId, text);
            return returnTextMessageHistory( messageContent, recipientId);
    }

    public MessageDomain returnButtonMessage(String recipientId, String text, List<Map<String, String>> buttons) {
            Map<String, Object> messageContent = messageBuilder.buildButtonMessage(recipientId, text, buttons);
            return returnButtonMessageHistory( messageContent, recipientId);
    }

   private void saveTextMessageHistory(Map<String, Object> messageContent, String recipientId) {
        MessageDomain messageDomain = new MessageDomain();
        messageDomain.setCollectionId(UUID.randomUUID().toString());
        messageDomain.setTime(System.currentTimeMillis());

        Messaging messaging = new Messaging();
        messaging.setSentContent(messageContent);

        messageDomain.setMessaging(Collections.singletonList(messaging));
        messageDomain.setSentByBot(1);
        messageRepository.save(messageDomain);
    }

    private void saveButtonMessageHistory(Map<String, Object> messageContent, String recipientId) {
        try {
            MessageDomain messageDomain = new MessageDomain();
            messageDomain.setCollectionId(UUID.randomUUID().toString());
            messageDomain.setTime(System.currentTimeMillis());

            Messaging messaging = new Messaging();

            messaging.setSentContent(messageContent);

            messageDomain.setMessaging(Collections.singletonList(messaging));
            messageDomain.setSentByBot(1);
            messageRepository.save(messageDomain);
        } catch (Exception e) {
            logger.error("Error saving button message history", e);
        }
    }

    private MessageDomain returnTextMessageHistory(Map<String, Object> messageContent, String recipientId) {
        try {
            MessageDomain messageDomain = new MessageDomain();
            messageDomain.setCollectionId(UUID.randomUUID().toString());
            messageDomain.setTime(System.currentTimeMillis());

            Messaging messaging = new Messaging();
            messaging.setSentContent(messageContent);

            messageDomain.setMessaging(Collections.singletonList(messaging));
            messageDomain.setSentByBot(1);
            messageRepository.save(messageDomain);
            return messageDomain;
        } catch (Exception e) {
            logger.error("Error returning text message history", e);
            return null;
        }
    }

    private MessageDomain returnButtonMessageHistory(Map<String, Object> messageContent, String recipientId) {
        try {
            MessageDomain messageDomain = new MessageDomain();
            messageDomain.setCollectionId(UUID.randomUUID().toString());
            messageDomain.setTime(System.currentTimeMillis());

            Messaging messaging = new Messaging();
            messaging.setSentContent(messageContent);

            messageDomain.setMessaging(Collections.singletonList(messaging));
            addSentMessage(messageDomain);
            return messageDomain;
        } catch (Exception e) {
            logger.error("Error returning button message history", e);
            return null;
        }
    }

    public void addSentMessage(MessageDomain messageDomain) {
        messageDomain.setSentByBot(1);

        try {
            messageRepository.save(messageDomain);
        } catch (Exception e) {
            logger.error("Error saving sent message", e);
        }
    }

}