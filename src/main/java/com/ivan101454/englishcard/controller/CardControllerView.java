package com.ivan101454.englishcard.controller;

import com.ivan101454.englishcard.dto.CardDTO;
import com.ivan101454.englishcard.entities.Card;
import com.ivan101454.englishcard.service.api.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequestMapping("/card")
@Controller
public class CardControllerView {
    @Autowired
    private ICardService iCardService;
    @GetMapping("/createcard")
    public String cardForm(Model model) {
        model.addAttribute("card", new Card());
        return "card";
    }
    /*@GetMapping("/card/{id}")
    public String cardGet(@PathVariable("id") long id, Model model) {
        Optional<Card> card = iCardService.getCard(id);
        model.addAttribute("card", card.get());
        return "cardView";
    } */

    @PostMapping("/createcard")
    public String cardSubmit(@ModelAttribute Card card, Model model) {
        model.addAttribute("card", card);
        return "result";
    }
    @GetMapping("/getallcard")
    public String cardGetAll(@RequestParam(required = false, defaultValue = "0") int page,
                             @RequestParam(required = false, defaultValue = "10") int size,
                             Model model) {
    List<CardDTO> getAllCard = iCardService.getAllCard(PageRequest.of(page, size));
    model.addAttribute("list", getAllCard);
    return "cardView";
    }
}

