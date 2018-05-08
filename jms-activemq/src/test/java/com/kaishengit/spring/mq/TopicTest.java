package com.kaishengit.spring.mq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-jms-topic-3.xml")
public class TopicTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void sendMessageToTopic() throws InterruptedException {
        //指定topic的目的地
        while(true) {
            Destination destination = new ActiveMQTopic("spring-topic");
            jmsTemplate.send(destination, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage("Hello,Spring + JMS - " + new Date().getTime());
                }
            });
            Thread.sleep(1000);
        }

        /*jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("Hello,Spring + JMS - 3");
            }
        });*/
    }

    @Test
    public void consumerMessage() throws IOException {
        System.out.println("Spring start...");
        System.in.read();
    }

}
