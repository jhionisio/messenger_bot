package meta.messenger_bot.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class MessageDTO {
    private String id;
    private long time;
    private List<MessagingDTO> messaging;
}