package com.ivan101454.englishcard.service.card;

import com.ivan101454.englishcard.dto.CardDTO;
import com.ivan101454.englishcard.entities.Card;
import com.ivan101454.englishcard.repositories.ICardRepository;
import com.ivan101454.englishcard.service.api.ICardService;
import com.ivan101454.englishcard.service.converters.CardToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService implements ICardService {
    @Autowired
    private ICardRepository iCardRepository;
    @Autowired
    private CardToDTOConverter cardToDTOConverter;
    public void createCard(Card card) {
        iCardRepository.save(card);
    }

    public Optional<Card> getCard(long id) {
        return iCardRepository.findById(id);
    }

    public void updateCard(long id, Card updateCard) {
        updateCard.setCardId(id);
        iCardRepository.save(updateCard);
    }

    public void deleteCard(long id) {
        iCardRepository.deleteById(id);
    }


    public List<CardDTO> getAllCard(Pageable pageable) {
        List<Card> list = iCardRepository.findAll(pageable);
        List<CardDTO> listCardDTO = new ArrayList<>();
        for (Card card: list) {
            listCardDTO.add(cardToDTOConverter.convert(card));
        }
        return listCardDTO;
    }

}
