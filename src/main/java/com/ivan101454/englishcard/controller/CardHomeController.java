package com.ivan101454.englishcard.controller;

import com.ivan101454.englishcard.dto.CardDTO;
import com.ivan101454.englishcard.entities.Card;
import com.ivan101454.englishcard.exception.StorageFileNotFoundException;
import com.ivan101454.englishcard.service.api.ICardService;
import com.ivan101454.englishcard.service.api.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/eng")
@Controller
@Slf4j
public class CardHomeController {
    private final StorageService storageService;

    @Autowired
    private ICardService iCardService;
    @Autowired
    public CardHomeController(StorageService storageService)
    {
        this.storageService = storageService;
    }

    @GetMapping("/createcard")
    public String cardForm(Model model) {
        model.addAttribute("files", storageService
                .loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(CardHomeController.class, "serveFile",
                        path.getFileName().toString()).build().toUri().toString()).collect(Collectors.toList()));
        model.addAttribute("card", new Card());
        return "cardregister";
    }
    /*@GetMapping("/card/{id}")
    public String cardGet(@PathVariable("id") long id, Model model) {
        Optional<Card> card = iCardService.getCard(id);
        model.addAttribute("card", card.get());
        return "cardView";
    } */

    @PostMapping("/createcard")
    public String cardSubmit(@RequestParam("file") MultipartFile file,

                             RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "cardregister";
    }
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        if (file==null) {
            return ResponseEntity.notFound().build();
        }
        return
            ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }
    @ExceptionHandler(StorageFileNotFoundException.class)
    public  ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/flashcard")
    public String cardGetAll(@RequestParam(required = false, defaultValue = "0") int page,
                             @RequestParam(required = false, defaultValue = "1") int size,
                             Model model) {
    List<CardDTO> getAllCard = iCardService.getAllCard(PageRequest.of(page, size));
    int previousPage = page-1;
    if (previousPage<=0) {
        previousPage = page;
    }
    int nextPage = page+1;
    log.info("This is test previousPage"+String.valueOf(previousPage));
    log.info("This is test nextPage"+String.valueOf(nextPage));
    model.addAttribute("list", getAllCard);
    model.addAttribute("nextPage", nextPage);
    model.addAttribute("previousPage", previousPage);
    return "index";
    }

}

