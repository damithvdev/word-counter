package com.damithvidanage.wordcounter.service;

import java.util.List;
import java.util.Locale;

public interface WordCounterService {
    int addWords(List<String> words, String language);

    int getWordCount(String word, String language);
}
