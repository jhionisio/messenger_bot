package meta.messenger_bot.usecase.service;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageBuilder {

    public Map<String, Object> buildTextMessage(String recipientId, String text) {
        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("recipient", Collections.singletonMap("id", recipientId));
        messageContent.put("message", Collections.singletonMap("text", text));
        return messageContent;
    }

    public Map<String, Object> buildButtonMessage(String recipientId, String text, List<Map<String, String>> buttons) {
        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("recipient", Collections.singletonMap("id", recipientId));
        Map<String, Object> attachment = new HashMap<>();
        attachment.put("type", "template");
        Map<String, Object> payload = new HashMap<>();
        payload.put("template_type", "button");
        payload.put("text", text);
        payload.put("buttons", buttons);
        attachment.put("payload", payload);
        messageContent.put("message", Collections.singletonMap("attachment", attachment));
        return messageContent;
    }
}