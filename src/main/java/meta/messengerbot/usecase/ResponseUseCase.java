package meta.messengerbot.usecase;

import meta.messengerbot.domain.Message;
import meta.messengerbot.domain.MessageContent;
import meta.messengerbot.domain.MessageDomain;
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

    private void saveTextMessageHistory(MessageContent messageContent, String recipientId) {
        MessageDomain messageDomain = new MessageDomain();
        messageDomain.setTime(System.currentTimeMillis());

        Messaging messaging = new Messaging();
        messaging.setMessageContent(messageContent);

        messageDomain.setMessaging(Collections.singletonList(messaging));
        messageDomain.setSentByBot(true);
        messageRepository.save(messageDomain);
    }

    public void sendButtonMessage(String recipientId, String text, List<Map<String, String>> buttons) {
        try {
            Map<String, Object> messageContent = messageBuilder.buildButtonMessage(recipientId, text, buttons);
            httpService.sendMessage(messageContent);
            saveButtonMessageHistory(messageContent, recipientId);
        } catch (Exception e) {
            logger.error("Error sending button message", e);
        }
    }

    private void saveButtonMessageHistory(Map<String, Object> messageContent, String recipientId) {
        try {
            MessageDomain messageDomain = new MessageDomain();
            messageDomain.setTime(System.currentTimeMillis());

            Messaging messaging = new Messaging();
            Postback newPostback = new Postback();

            Map<String, Object> attachment = (Map<String, Object>) messageContent.get("attachment");
            Map<String, Object> payload = (Map<String, Object>) attachment.get("payload");
            String mid = (String) payload.get("mid");
            String postbackPayload = (String) payload.get("payload");

            newPostback.setMid(mid);
            newPostback.setPayload(postbackPayload);
            messaging.setPostback(newPostback);

            messageDomain.setMessaging(Collections.singletonList(messaging));
            messageDomain.setSentByBot(true);
            messageRepository.save(messageDomain);
        } catch (Exception e) {
            logger.error("Error saving button message history", e);
        }
    }

}