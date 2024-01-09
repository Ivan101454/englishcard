package com.ivan101454.englishcard.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CardDTO {
    private String question;
    private String answer;
    private boolean itLearned;
}
