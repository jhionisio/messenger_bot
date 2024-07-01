package meta.messengerbot.usecase.handler;

import meta.messengerbot.domain.MessageDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TextMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    @Override
    public void handle(MessageDomain messageDomain) {
        logger.info("Handling text message: {}", messageDomain);
        // Aqui é o espaço para desenvolver a lógica de tratamento das mensagens de texto
        // mas como está fora do escopo esperado para o teste, não foi feito nenhum
        // tratamento aos dados
    }

}