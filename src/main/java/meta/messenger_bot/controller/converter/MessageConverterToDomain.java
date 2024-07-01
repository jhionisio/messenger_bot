package meta.messenger_bot.controller.converter;

import meta.messenger_bot.controller.dto.MessageDTO;
import meta.messenger_bot.domain.MessageDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageConverterToDomain {

    private final ModelMapper modelMapper;

    public MessageConverterToDomain(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MessageDomain toDomain(MessageDTO messageDTO) {
        return modelMapper.map(messageDTO, MessageDomain.class);
    }

}