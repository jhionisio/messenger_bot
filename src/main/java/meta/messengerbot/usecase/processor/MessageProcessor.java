package meta.messengerbot.usecase.processor;

import meta.messengerbot.domain.Message;
import meta.messengerbot.domain.enums.MessageType;
import meta.messengerbot.usecase.ResponseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private final ResponseUseCase responseUseCase;

    @Autowired
    public MessageProcessor(ResponseUseCase responseUseCase) {
        this.responseUseCase = responseUseCase;
    }

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
        responseUseCase.sendButtonMessage(recipientId, "Escolha uma opção:", List.of(
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

        List<String> textMessages = new ArrayList<>();
        List<Map<String, String>> buttonMessages = new ArrayList<>();

        try {
            switch (payload) {
                case "OI_PAYLOAD":
                    buttonMessages.add(Map.of("type", "postback", "title", "TUDO BEM COM VOCÊ?", "payload", "TUDO_BEM_COM_VC_PAYLOAD"));
                    buttonMessages.add(Map.of("type", "postback", "title", "TCHAU", "payload", "TCHAU_PAYLOAD"));
                    textMessages.add("Tudo bem com você?");
                    break;
                case "TCHAU_PAYLOAD":
                    textMessages.add("Tchau");
                    break;
                case "QUAL_SEU_NOME_PAYLOAD":
                    textMessages.add("Meu nome é Meta Messenger Bot mas me chamam de William prazer.");
                    buttonMessages.add(Map.of("type", "postback", "title", "TUDO BEM COM VOCÊ?", "payload", "TUDO_BEM_COM_VC_PAYLOAD"));
                    buttonMessages.add(Map.of("type", "postback", "title", "TCHAU", "payload", "TCHAU_PAYLOAD"));
                    break;
                case "PRA_QUE_VOCE_SERVE_PAYLOAD":
                    textMessages.add("Eu sou um bot criado para responder mensagens e demonstrar funcionalidades de um chatbot.");
                    break;
                default:
                    logger.warn("Unhandled payload: {}", payload);
                    break;
            }

            if (!textMessages.isEmpty()) {
                for (String textMessage : textMessages) {
                    responseUseCase.sendTextMessage(recipientId, textMessage);
                }
            }

            if (!buttonMessages.isEmpty()) {
                responseUseCase.sendButtonMessage(recipientId, textMessages.get(0), buttonMessages);
            }

        } catch (Exception e) {
            logger.error("Error processing postback message", e);
        }
    }

}