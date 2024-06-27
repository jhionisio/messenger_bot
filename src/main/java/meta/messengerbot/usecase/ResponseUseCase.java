package meta.messengerbot.usecase;

import meta.messengerbot.domain.Message;
import meta.messengerbot.usecase.service.HttpService;
import meta.messengerbot.usecase.service.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResponseUseCase {

    private final HttpService httpService;
    private final MessageUseCase messageUseCase;
    private final MessageBuilder messageBuilder;

    @Autowired
    public ResponseUseCase(HttpService httpService, MessageUseCase messageUseCase, MessageBuilder messageBuilder) {
        this.httpService = httpService;
        this.messageUseCase = messageUseCase;
        this.messageBuilder = messageBuilder;
    }

    public void sendTextMessage(String recipientId, String text) {
        Map<String, Object> messageContent = messageBuilder.buildTextMessage(recipientId, text);
        httpService.sendMessage(messageContent);
        saveMessageHistory(messageContent);
    }

    public void sendButtonMessage(String recipientId, String text, List<Map<String, String>> buttons) {
        Map<String, Object> messageContent = messageBuilder.buildButtonMessage(recipientId, text, buttons);
        httpService.sendMessage(messageContent);
        saveMessageHistory(messageContent);
    }

    private void saveMessageHistory(Map<String, Object> messageContent) {
        Message message = new Message();
        message.setTime(System.currentTimeMillis());
        message.setMessaging(messageContent);
        message.setSentByBot(true);
        messageUseCase.addSentMessage(message);
    }
}