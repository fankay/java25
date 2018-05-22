package com.kaishengit.client;

import org.springframework.stereotype.Component;

@Component
public class MovieServiceClientFallback implements MovieServiceClient {
    @Override
    public String getMovieName(Integer id) {
        return "Null";
    }

    @Override
    public String saveNewMovie(String movieName, String author) {
        return "ERROR";
    }
}
