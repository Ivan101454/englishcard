package com.ivan101454.englishcard.controller;

import com.ivan101454.englishcard.dto.CardDTO;
import com.ivan101454.englishcard.entities.Card;
import com.ivan101454.englishcard.service.api.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class cardController {
    @Autowired
    private ICardService iCardService;
    @PostMapping("create")
    public ResponseEntity<HttpStatus> createCard(@RequestBody Card card) {
        iCardService.createCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("get/{id}")
    public ResponseEntity<Optional<Card>> getCard(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iCardService.getCard(id));
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteCard(@PathVariable("id") long id) {
        iCardService.deleteCard(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<HttpStatus> updateCard(@PathVariable("id") long id, @RequestBody Card card) {
        iCardService.updateCard(id, card);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("getall")
    public ResponseEntity<List<CardDTO>> getAllCard(@RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "10") int size) {
        return  ResponseEntity.status(HttpStatus.OK).body(iCardService.getAllCard(PageRequest.of(page, size)));
    }

}
