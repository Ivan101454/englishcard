package com.ivan101454.englishcard.service.api;


import com.ivan101454.englishcard.dto.CardDTO;
import com.ivan101454.englishcard.entities.Card;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICardService {
    void createCard(Card card);
    Optional<Card> getCard(long id);
    void updateCard(long id, Card updateCard);
    void deleteCard(long id);
    List<CardDTO> getAllCard(Pageable pageable);
}
