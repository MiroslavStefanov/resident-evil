package org.softuni.residentevil.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

    private final Gson gson;

    public GsonConfig() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public Gson getGson() {
        return this.gson;
    }
}
