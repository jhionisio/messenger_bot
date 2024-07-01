package meta.messenger_bot.domain.massaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Postback {
    private String mid;
    private String payload;
}