package meta.messengerbot.usecase.processor;

import meta.messengerbot.domain.MessageContent;
import meta.messengerbot.domain.MessageDomain;
import meta.messengerbot.domain.Messaging;
import meta.messengerbot.domain.Postback;
import meta.messengerbot.domain.enums.MessageType;
import meta.messengerbot.usecase.ResponseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Component
public class MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private final ResponseUseCase responseUseCase;
    private static final String BUTTON_TEXT = "Escolha uma opção:";
    private static final String OI_PAYLOAD = "OI_PAYLOAD";
    private static final String TCHAU_PAYLOAD = "TCHAU_PAYLOAD";
    private static final String QUAL_SEU_NOME_PAYLOAD = "QUAL_SEU_NOME_PAYLOAD";
    private static final String PRA_QUE_VOCE_SERVE_PAYLOAD = "PRA_QUE_VOCE_SERVE_PAYLOAD";
    private static final String TUDO_BEM_COM_VC_PAYLOAD = "TUDO_BEM_COM_VC_PAYLOAD";

    @Autowired
    public MessageProcessor(ResponseUseCase responseUseCase) {
        this.responseUseCase = responseUseCase;
    }

    public void process(MessageDomain messageDomain, MessageType messageType) {
        try {
            if (messageType == MessageType.TEXT) {
                processTextMessage(messageDomain);
                return;
            }
            if (messageType == MessageType.POSTBACK) {
                processPostbackMessage(messageDomain);
            }
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }

    private void processTextMessage(MessageDomain messageDomain) {
        List<Messaging> messagingList = messageDomain.getMessaging();
        for (Messaging messaging : messagingList) {
            MessageContent messageContent = messaging.getMessageContent();
            if (messageContent == null) {
                continue;
            }

            String text = (String) messageContent.getText();
            String recipientId = messaging.getSender().getId();

            if ("botões".equalsIgnoreCase(text)) {
                responseUseCase.sendButtonMessage(recipientId, BUTTON_TEXT, List.of(
                        createButton("postback", "OI", OI_PAYLOAD),
                        createButton("postback", "TCHAU", TCHAU_PAYLOAD),
                        createButton("postback", "QUAL SEU NOME", QUAL_SEU_NOME_PAYLOAD),
                        createButton("postback", "PRA QUE VOCÊ SERVE", PRA_QUE_VOCE_SERVE_PAYLOAD)
                ));
            }
        }
    }

    private void processPostbackMessage(MessageDomain messageDomain) {
        List<Messaging> messagingList = messageDomain.getMessaging();
        for (Messaging messaging : messagingList) {
            Postback postback = messaging.getPostback();
            if (postback == null) {
                continue;
            }

            String payload = (String) postback.getPayload();
            String recipientId = messaging.getSender().getId();

            try {
                switch (payload) {
                    case OI_PAYLOAD:
                        responseUseCase.sendButtonMessage(recipientId, "Tudo bem com você?", List.of(
                                createButton("postback", "TUDO BEM COM VOCÊ?", TUDO_BEM_COM_VC_PAYLOAD),
                                createButton("postback", "TCHAU", TCHAU_PAYLOAD)
                        ));
                        break;
                    case TCHAU_PAYLOAD:
                        responseUseCase.sendTextMessage(recipientId, "Tchau");
                        break;
                    case QUAL_SEU_NOME_PAYLOAD:
                        responseUseCase.sendTextMessage(recipientId, "Meu nome é Meta Messenger Bot mas me chamam de William prazer.");
                        responseUseCase.sendButtonMessage(recipientId, "Escolha uma opção:", List.of(
                                createButton("postback", "TUDO BEM COM VOCÊ?", TUDO_BEM_COM_VC_PAYLOAD),
                                createButton("postback", "TCHAU", TCHAU_PAYLOAD)
                        ));
                        break;
                    case PRA_QUE_VOCE_SERVE_PAYLOAD:
                        responseUseCase.sendTextMessage(recipientId, "Eu sou um bot criado para responder mensagens e demonstrar funcionalidades de um chatbot.");
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

    private Map<String, String> createButton(String type, String title, String payload) {
        return Map.of(
                "type", type,
                "title", title,
                "payload", payload
        );
    }
}