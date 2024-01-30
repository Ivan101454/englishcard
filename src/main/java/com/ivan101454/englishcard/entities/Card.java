package com.ivan101454.englishcard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "englishcard", name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardId")
    private long cardId;
    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;
    @Column(name = "itLearned", columnDefinition = "boolean default false")
    private boolean itLearned;
    @Column(name = "picturePath")
    private String picturePath;
}

