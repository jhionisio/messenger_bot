package meta.messengerbot.controller.dto;

import java.util.Map;
import lombok.Data;

@Data
public class MessageDTO {
    private String id;
    private long time;
    private Map<String, Object> messaging;
}