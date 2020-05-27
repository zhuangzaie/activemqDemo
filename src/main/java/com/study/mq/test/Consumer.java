package com.study.mq.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @description: 消费mq
 * @author: crane
 * @create: 2020-05-25 11:39
 **/
public class Consumer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn;
        Session session;
        MessageConsumer consumer;

        try {
            // brokerURL http://activemq.apache.org/connection-configuration-uri.html
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.150:61616");
            // 2、创建连接对象
            conn = connectionFactory.createConnection();
            conn.start(); // 一定要启动
            // 3、创建会话（可以创建一个或者多个session）
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 4、创建点对点接收的目标，queue - 点对点
            Destination destination = session.createQueue("queue1");

            // 5、创建消费者消息 http://activemq.apache.org/destination-options.html
            consumer = session.createConsumer(destination);

            // 6、接收消息(没有消息就持续等待)
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) message).getText());
            } else {
                System.out.println(message);
            }

            consumer.close();
            session.close();
            conn.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
