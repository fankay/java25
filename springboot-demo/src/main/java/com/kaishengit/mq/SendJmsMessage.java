package com.kaishengit.mq;

import com.kaishengit.entity.Movie;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class SendJmsMessage {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMovieToQueue(Movie movie) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("movie-queue");
        jmsTemplate.convertAndSend(activeMQQueue,movie);
    }

    public void sendMessageToQueue(String message) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("springboot-queue");
        jmsTemplate.send(activeMQQueue, session -> session.createTextMessage(message));
    }

    public void sendMessageToTopic(String message) {
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("springboot-topic");
        jmsTemplate.send(activeMQTopic,session -> session.createTextMessage(message));
    }

}
