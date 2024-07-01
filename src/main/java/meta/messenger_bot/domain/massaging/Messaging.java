package meta.messenger_bot.domain.massaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messaging {
    private Sender sender;
    private Recipient recipient;
    private long timestamp;
    private Postback postback;
    private MessageContent messageContent;
    private Map<String, Object> sentContent;
}