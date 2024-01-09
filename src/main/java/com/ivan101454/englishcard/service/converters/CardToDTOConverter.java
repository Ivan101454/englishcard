package com.ivan101454.englishcard.service.converters;

import com.ivan101454.englishcard.dto.CardDTO;
import com.ivan101454.englishcard.entities.Card;
import org.springframework.core.convert.converter.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CardToDTOConverter implements Converter<Card, CardDTO> {

    public CardDTO convert(Card card) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(card, CardDTO.class);
    }
}
