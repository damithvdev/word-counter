package com.damithvidanage.wordcounter.controller;

import org.damithvidanage.WordCounterImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v2")
public class WordCounterControllerV2 {

    private final WordCounterImpl wordCounter;

    public WordCounterControllerV2(WordCounterImpl wordCounter) {
        this.wordCounter = wordCounter;
    }

    @GetMapping("/count/{word}")
    public int getWordCount(@PathVariable String word, @RequestHeader(value = "Accept-Language") String acceptLanguage) {
        return wordCounter.getCount(word, acceptLanguage);
    }

    @PostMapping("/add")
    public int addWord(@RequestBody List<String> words, @RequestHeader(value = "Accept-Language") String acceptLanguage) {
        return wordCounter.addWords(words, acceptLanguage);
    }
}
