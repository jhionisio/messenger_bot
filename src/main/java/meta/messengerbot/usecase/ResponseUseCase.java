package meta.messengerbot.usecase;

import meta.messengerbot.domain.Message;
import meta.messengerbot.domain.MessageContent;
import meta.messengerbot.domain.Messaging;
import meta.messengerbot.domain.Postback;
import meta.messengerbot.repository.MessageRepository;
import meta.messengerbot.usecase.service.HttpService;
import meta.messengerbot.usecase.service.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            saveTextMessageHistory((MessageContent) messageContent, recipientId);
        } catch (Exception e) {
            logger.error("Error sending text message", e);
        }
    }

    public void sendButtonMessage(String recipientId, String text, List<Map<String, String>> buttons) {
        try {
            Map<String, Object> messageContent = messageBuilder.buildButtonMessage(recipientId, text, buttons);
            httpService.sendMessage(messageContent);
            saveButtomMessageHistory((Postback) messageContent, recipientId);
        } catch (Exception e) {
            logger.error("Error sending button message", e);
        }
    }

    private void saveTextMessageHistory(MessageContent messageContent, String recipientId) {
        Message message = new Message();
        message.setTime(System.currentTimeMillis());

        Messaging messaging = new Messaging();
        messaging.setMessageContent(messageContent);

        message.setMessaging(Collections.singletonList(messaging));
        message.setSentByBot(true);
        messageRepository.save(message);
    }

    private void saveButtomMessageHistory(Postback postback, String recipientId) {
        Message message = new Message();
        message.setTime(System.currentTimeMillis());

        Messaging messaging = new Messaging();
        messaging.setPostback(postback);

        message.setMessaging(Collections.singletonList(messaging));
        message.setSentByBot(true);
        messageRepository.save(message);
    }
}