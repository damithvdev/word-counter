package org.damithvidanage;

import java.util.List;

public interface WordCounter {

    int addWords(List<String> words, String Language);

    int getCount(String word, String Language);
}
