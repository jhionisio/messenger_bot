package meta.messenger_bot.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meta.messenger_bot.domain.massaging.Messaging;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class MessageDomain {
    @Id
    private String collectionId;
    private String id;
    private long time;
    private List<Messaging> messaging;
    private int sentByBot;
}