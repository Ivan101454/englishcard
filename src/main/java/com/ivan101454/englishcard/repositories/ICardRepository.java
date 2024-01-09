package com.ivan101454.englishcard.repositories;

import com.ivan101454.englishcard.entities.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICardRepository extends CrudRepository<Card, Long> {

List<Card> findAll(Pageable pageable);
}
