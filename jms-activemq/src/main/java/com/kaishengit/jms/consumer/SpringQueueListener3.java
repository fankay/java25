package com.kaishengit.jms.consumer;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

public class SpringQueueListener3 implements SessionAwareMessageListener {

    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String text = textMessage.getText();

        if(text.startsWith("Hello,Spring")) {
         //   session.recover();
            throw new RuntimeException("故意抛出的异常:" + text);
        }

    }
}
