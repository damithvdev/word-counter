package org.damithvidanage;


import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordCounterImpl implements WordCounter{

    private final Translator translator;

    private final Map<String, Integer> wordMap = new ConcurrentHashMap<>();

    public WordCounterImpl(Translator translator) {
        this.translator = translator;
    }

    @Override
    public int addWords(List<String> words, String language) {
        if (words.isEmpty()) {
            throw new WordNotFoundException("No words found");
        }
        words.stream()
                .map(word -> getEnglishWords(word, language))
                .map(String::toLowerCase)
                .filter(this::isWord)
                .forEach(word -> wordMap.put(word, wordMap.getOrDefault(word, 0) + 1));

        return wordMap.size();
    }

    @Override
    public int getCount(String word, String language) {
        if (word == null) {
            throw new WordNotFoundException("Word cannot be null");
        }
        if (!Locale.US.getLanguage().equals(language)) {
            word = translator.translate(word);
        }

        if(!wordMap.containsKey(word.toLowerCase())) {
            throw new WordNotFoundException("Cannot find word");
        }

        return wordMap.get(word.toLowerCase());

    }

    private boolean isWord(String word) {
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private String getEnglishWords(String word, String language) {
        return Locale.US.getLanguage().equals(language) ? word.toLowerCase() : translator.translate(word).toLowerCase();
    }
}
