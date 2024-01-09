package com.ivan101454.englishcard.repositories;

import com.ivan101454.englishcard.entities.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ICardRepository extends CrudRepository<Card, Long> {

List<Card> findAll(Pageable pageable);
}
