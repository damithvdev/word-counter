package com.damithvidanage.wordcounter.service;

import com.damithvidanage.wordcounter.exception.WordNotValidException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WordCounterServiceImpl implements WordCounterService{

    private final TranslatorService translatorService;

    private final Map<String, Integer> wordMap = new ConcurrentHashMap<>();

    public WordCounterServiceImpl(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @Override
    public int addWords(List<String> words, String language) throws WordNotValidException{

        if (words.isEmpty()) {
            throw new WordNotValidException("Cannot found valid word");
        }

        words.stream()
                .map(word -> getEnglishWords(word, language))
                .filter(this::isWord)
                .map(String::toLowerCase)
                .forEach(word -> wordMap.put(word, wordMap.getOrDefault(word, 0) + 1));

        return wordMap.size();
    }

    private String getEnglishWords(String word, String language) {
        return Locale.US.getLanguage().equals(language) ? word.toLowerCase() : translatorService.translate(word).toLowerCase();
    }

    private boolean isWord(String word) {
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getWordCount(String word, String language) throws WordNotValidException{

        if (word == null) {
            throw new WordNotValidException("Cannot find the word");
        }

        if (!Locale.US.getLanguage().equals(language)) {
            translatorService.translate(word);
        }

        return wordMap.get(word.toLowerCase()) != null ? wordMap.get(word.toLowerCase()) : 0;
    }
}
