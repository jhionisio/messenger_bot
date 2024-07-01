package meta.messenger_bot.controller.dto;

import lombok.Data;

@Data
public class MessagingDTO {
    private SenderDTO sender;
    private RecipientDTO recipient;
    private long timestamp;
    private PostbackDTO postback;
    private MessageContentDTO message;
}
