package meta.messengerbot.domain;

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
}