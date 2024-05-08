package com.damithvidanage.wordcounter.config;

import org.damithvidanage.Translator;
import org.damithvidanage.WordCounterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.damithvidanage"})
public class Config {

    @Bean
    public WordCounterImpl wordCounter() {

        return new WordCounterImpl(translator());
    }

    @Bean
    public Translator translator() {

        return new Translator();
    }

}
