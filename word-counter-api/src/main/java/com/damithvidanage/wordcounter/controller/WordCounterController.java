package com.damithvidanage.wordcounter.controller;

import com.damithvidanage.wordcounter.service.WordCounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class WordCounterController {

    private final WordCounterService wordCounterService;

    public WordCounterController(WordCounterService wordCounterService) {
        this.wordCounterService = wordCounterService;
    }

    @GetMapping("/count/{word}")
    public ResponseEntity<String> getWordCount(@PathVariable String word, @RequestHeader(value = "Accept-Language") String acceptLanguage) {
        int wordCount = wordCounterService.getWordCount(word, acceptLanguage);
        return ResponseEntity.ok(Integer.toString(wordCount));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addWord(@RequestBody List<String> words, @RequestHeader(value = "Accept-Language") String acceptLanguage) {
        int size = wordCounterService.addWords(words, acceptLanguage);
        return ResponseEntity.ok(Integer.toString(size));
    }
}
