package meta.messengerbot.usecase;

import meta.messengerbot.domain.Message;
import meta.messengerbot.usecase.service.HttpService;
import meta.messengerbot.usecase.service.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class ResponseUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUseCase.class);
    private final MessageUseCase messageUseCase;
    private final MessageBuilder messageBuilder;
    private final HttpService httpService;

    @Autowired
    public ResponseUseCase(MessageUseCase messageUseCase, MessageBuilder messageBuilder, HttpService httpService) {
        this.messageUseCase = messageUseCase;
        this.messageBuilder = messageBuilder;
        this.httpService = httpService;
    }

    public void sendTextMessage(String recipientId, String text) {
        try {
            Map<String, Object> messageContent = messageBuilder.buildTextMessage(recipientId, text);
            httpService.sendMessage(messageContent);
            saveMessageHistory(messageContent);
        } catch (Exception e) {
            logger.error("Error sending text message", e);
        }
    }

    public void sendButtonMessage(String recipientId, String text, List<Map<String, String>> buttons) {
        try {
            Map<String, Object> messageContent = messageBuilder.buildButtonMessage(recipientId, text, buttons);
            httpService.sendMessage(messageContent);
            saveMessageHistory(messageContent);
        } catch (Exception e) {
            logger.error("Error sending button message", e);
        }
    }

    private void saveMessageHistory(Map<String, Object> messageContent) {
        Message message = new Message();
        message.setTime(System.currentTimeMillis());
        message.setMessaging(messageContent);
        message.setSentByBot(true);
        messageUseCase.addSentMessage(message);
    }
}