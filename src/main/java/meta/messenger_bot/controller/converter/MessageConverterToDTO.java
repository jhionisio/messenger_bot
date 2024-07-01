package meta.messenger_bot.controller.converter;

import meta.messenger_bot.controller.dto.MessageDTO;
import meta.messenger_bot.domain.MessageDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageConverterToDTO {

    private final ModelMapper modelMapper;

    public MessageConverterToDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MessageDTO toDTO(MessageDomain messageDomain) {
        return modelMapper.map(messageDomain, MessageDTO.class);
    }
}