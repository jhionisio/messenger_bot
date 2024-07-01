package meta.messengerbot.controller.converter;

import meta.messengerbot.controller.dto.MessageDTO;
import meta.messengerbot.domain.MessageDomain;
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