package meta.messengerbot.controller.converter;

import meta.messengerbot.controller.dto.MessageDTO;
import meta.messengerbot.domain.Message;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageConverterToDTO {

    private final ModelMapper modelMapper;

    public MessageConverterToDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MessageDTO toDTO(Message message) {
        return modelMapper.map(message, MessageDTO.class);
    }
}