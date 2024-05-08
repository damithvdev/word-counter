package org.damithvidanage;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordCounterImplTest {
    WordCounterImpl wordCounter;

    @Mock
    Translator translator;

    @BeforeEach
    void setUp() {
        wordCounter = new WordCounterImpl(translator);
    }

    @Test
    void addAllEnglishWords() {

        List<String> words = new ArrayList<>();
        words.add("Apple");
        words.add("Banana");
        wordCounter.addWords(words, Locale.US.getLanguage());
    }

    @Test
    void addNonEnglishWords() {

        List<String> words = new ArrayList<>();
        words.add("Apfel");
        words.add("Banane");
        when(translator.translate(anyString())).thenReturn("Apple", "Banana");
        Assertions.assertEquals(2, wordCounter.addWords(words, Locale.GERMAN.getLanguage()));

    }

    @Test
    void addWordsWithNonAlphabetic() {

        List<String> words = new ArrayList<>();
        words.add("Apple!");
        Assertions.assertEquals(0, wordCounter.addWords(words, Locale.US.getLanguage()));
    }

    @Test
    void countAllEnglishWords() {

        List<String> words = new ArrayList<>();
        words.add("Apple");
        words.add("Banana");
        words.add("Banana");
        wordCounter.addWords(words, Locale.US.getLanguage());

        Assertions.assertEquals(2, wordCounter.getCount("Banana", Locale.US.getLanguage()));
    }

    @Test
    void countWordsFromOtherLanguage() {

        List<String> words = new ArrayList<>();
        words.add("Apple");
        wordCounter.addWords(words, Locale.US.getLanguage());

        when(translator.translate(anyString())).thenReturn("Apple");

        Assertions.assertEquals(1, wordCounter.getCount("Apfel", Locale.GERMAN.getLanguage()));
    }

    @Test()
    void noWordFoundException() {

        Exception exception = assertThrows(WordNotFoundException.class, () -> wordCounter.getCount(null, Locale.US.getLanguage()));

        String expected = "Word cannot be null";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

    @Test()
    void InvalidWord() {

        Exception exception = assertThrows(WordNotFoundException.class, () -> wordCounter.getCount("Apple", Locale.US.getLanguage()));

        String expected = "Cannot find word";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }
}