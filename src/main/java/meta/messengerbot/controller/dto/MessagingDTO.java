package meta.messengerbot.controller.dto;

import java.util.Map;
import lombok.Data;

@Data
public class MessagingDTO {
    private SenderDTO sender;
    private RecipientDTO recipient;
    private long timestamp;
    private PostbackDTO postback;
    private MessageContentDTO message;
}
