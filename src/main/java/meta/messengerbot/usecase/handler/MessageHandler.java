package meta.messengerbot.usecase.handler;

import meta.messengerbot.domain.MessageDomain;

public interface MessageHandler {
    void handle(MessageDomain messageDomain);
}