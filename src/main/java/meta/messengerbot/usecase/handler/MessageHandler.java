package meta.messengerbot.usecase.handler;

import meta.messengerbot.domain.Message;

public interface MessageHandler {
    void handle(Message message);
}