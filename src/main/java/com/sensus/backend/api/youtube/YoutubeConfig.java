package com.sensus.backend.api.youtube;

import com.google.api.services.youtube.YouTube;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class YoutubeConfig {
    @Autowired
    Environment environment;

    @Bean
    public String getYoutubeDataDeveloperKey() {
        return environment.getProperty("youtube.data.api.key");
    }

    @Bean
    public YouTube getYoutubeService() {
        try {
            return YoutubeServiceProvider.getService();
        } catch (GeneralSecurityException | IOException e) {
            throw new BeanCreationException(e.getMessage(), e);
        }
    }
}
