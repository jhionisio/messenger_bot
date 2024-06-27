package meta.messengerbot.usecase.processor;

import lombok.Setter;
import meta.messengerbot.domain.Message;
import meta.messengerbot.domain.enums.MessageType;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Setter
@Component
public class MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private BiConsumer<String, List<Map<String, String>>> sendButtonMessageFunction;
    private BiConsumer<String, String> sendTextMessageFunction;

    public void process(Message message, MessageType messageType) {
        try {
            if (messageType == MessageType.TEXT) {
                processTextMessage(message);
                return;
            }
            if (messageType == MessageType.POSTBACK) {
                processPostbackMessage(message);
            }
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }

    private void processTextMessage(Message message) {
        Map<String, Object> messaging = message.getMessaging();
        if (messaging == null) {
            return;
        }

        Map<String, Object> messageContent = (Map<String, Object>) messaging.get("message");
        if (messageContent == null) {
            return;
        }

        String text = (String) messageContent.get("text");
        String recipientId = (String) ((Map<String, Object>) messaging.get("sender")).get("id");

        if (!"botões".equalsIgnoreCase(text)) {
            return;
        }
        sendButtonMessageFunction.accept(recipientId, List.of(
                Map.of("type", "postback", "title", "OI", "payload", "OI_PAYLOAD"),
                Map.of("type", "postback", "title", "TCHAU", "payload", "TCHAU_PAYLOAD"),
                Map.of("type", "postback", "title", "QUAL SEU NOME", "payload", "QUAL_SEU_NOME_PAYLOAD"),
                Map.of("type", "postback", "title", "PRA QUE VOCÊ SERVE", "payload", "PRA_QUE_VOCE_SERVE_PAYLOAD")
        ));
    }

    private void processPostbackMessage(Message message) {
        Map<String, Object> messaging = message.getMessaging();
        if (messaging == null) {
            return;
        }

        Map<String, Object> postback = (Map<String, Object>) messaging.get("postback");
        if (postback == null) {
            return;
        }

        String payload = (String) postback.get("payload");
        String recipientId = (String) ((Map<String, Object>) messaging.get("sender")).get("id");

        try {
            switch (payload) {
                case "OI_PAYLOAD":
                    sendButtonMessageFunction.accept(recipientId, List.of(
                            Map.of("type", "postback", "title", "TUDO BEM COM VOCÊ?", "payload", "TUDO_BEM_COM_VC_PAYLOAD"),
                            Map.of("type", "postback", "title", "TCHAU", "payload", "TCHAU_PAYLOAD")
                    ));
                    break;
                case "TCHAU_PAYLOAD":
                    sendTextMessageFunction.accept(recipientId, "Tchau");
                    break;
                case "QUAL_SEU_NOME_PAYLOAD":
                    sendTextMessageFunction.accept(recipientId, "Meu nome é Meta Messenger Bot mas me chamam de William prazer.");
                    sendButtonMessageFunction.accept(recipientId, List.of(
                            Map.of("type", "postback", "title", "TUDO BEM COM VOCÊ?", "payload", "TUDO_BEM_COM_VC_PAYLOAD"),
                            Map.of("type", "postback", "title", "TCHAU", "payload", "TCHAU_PAYLOAD")
                    ));
                    break;
                case "PRA_QUE_VOCE_SERVE_PAYLOAD":
                    sendTextMessageFunction.accept(recipientId, "Eu sou um bot criado para responder mensagens e demonstrar funcionalidades de um chatbot.");
                    break;
                default:
                    logger.warn("Unhandled payload: {}", payload);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error processing postback message", e);
        }
    }
}