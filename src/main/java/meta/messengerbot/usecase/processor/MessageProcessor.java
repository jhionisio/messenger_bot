package meta.messengerbot.usecase.processor;

import meta.messengerbot.domain.Message;
import meta.messengerbot.domain.enums.MessageType;
import meta.messengerbot.usecase.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageProcessor {

    private final Map<MessageType, MessageHandler> handlers;

    @Autowired
    public MessageProcessor(Map<MessageType, MessageHandler> handlers) {
        this.handlers = handlers;
    }

    public void process(Message message, MessageType messageType) {
        MessageHandler handler = handlers.get(messageType);
        if (handler != null) {
            handler.handle(message);
        } else {
            throw new IllegalArgumentException("Unsupported message type: " + messageType);
        }
    }
}