package com.damithvidanage.wordcounter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordCounterServiceImplTest {

    @Mock
    private TranslatorService translatorService;

    @InjectMocks
    private WordCounterServiceImpl wordCounterService;

    private final List<String> words = new ArrayList<>();

    @BeforeEach
    void setUp() {
        words.add("Apple");
        words.add("Ball");
    }

    @Test
    public void testAddWord_AllEnglish() {
        Assertions.assertEquals(wordCounterService.addWords(words, "en"), 2);
    }

    @Test
    public void testAddWord_NonEnglishWords() {

        List<String> nonEngWords = new ArrayList<>();

        nonEngWords.add("Blume");

        when(translatorService.translate(Mockito.any())).thenReturn("Flower");

        Assertions.assertEquals(wordCounterService.addWords(nonEngWords, "ge"), 1);
    }

    @Test
    public void testAddWord_WithNonAlphabeticCharacter() {

        List<String> nonEngWords = new ArrayList<>();
        nonEngWords.add("Apple!");
        Assertions.assertEquals(wordCounterService.addWords(nonEngWords, "en"), 0);
    }

}