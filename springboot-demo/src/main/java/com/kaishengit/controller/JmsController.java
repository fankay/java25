package com.kaishengit.controller;

import com.kaishengit.entity.Movie;
import com.kaishengit.mq.SendJmsMessage;
import com.kaishengit.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JmsController {

    @Autowired
    private SendJmsMessage sendJmsMessage;
    @Autowired
    private MovieService movieService;

    @GetMapping("/jms/queue")
    public String sendMessageToQueue() {
        sendJmsMessage.sendMessageToQueue("Hello," + System.currentTimeMillis());
        return "Send Success";
    }

    @GetMapping("/jms/topic")
    public String sendMessageToTopic() {
        sendJmsMessage.sendMessageToTopic("Hi," + System.currentTimeMillis());
        return "Send to topic success";
    }

    @GetMapping("/jms/movie")
    public String sendMovieToQueue() {
        Movie movie = movieService.findById(101);
        sendJmsMessage.sendMovieToQueue(movie);
        return "Send Movie to queue success!";
    }
}
