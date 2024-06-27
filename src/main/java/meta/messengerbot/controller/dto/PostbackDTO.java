package meta.messengerbot.controller.dto;

import lombok.Data;

@Data
public class PostbackDTO {
    private String mid;
    private String payload;
}
