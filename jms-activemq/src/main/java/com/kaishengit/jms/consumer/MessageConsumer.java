package com.kaishengit.jms.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class MessageConsumer {

    @JmsListener(destination = "spring-queue")
    public void queueMessageConsumer(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("获取消息内容：" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "spring-topic",containerFactory = "jmsTopicListenerContainerFactory")
    public void topicMessageConsumer(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("获取主题内容：" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
