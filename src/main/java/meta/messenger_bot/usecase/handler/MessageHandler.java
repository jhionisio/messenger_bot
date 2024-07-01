package meta.messenger_bot.usecase.handler;

import meta.messenger_bot.domain.MessageDomain;

public interface MessageHandler {
    void handle(MessageDomain messageDomain);
}