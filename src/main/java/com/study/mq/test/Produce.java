package com.study.mq.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @description: 连接测试
 * @author: crane
 * @create: 2020-05-25 11:09
 **/
public class Produce {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn;
        Session session;
        try {
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.1.150:61616");
            // 2、创建连接对象md
            conn = connectionFactory.createConnection();
            conn.start();
            // 3、创建会话
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 4、创建点对点发送的目标
            Destination destination = session.createQueue("queue1");
            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选 持久化 / 不持久化
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            // 6、创建一条文本消息
            String text = "Hello world!";
            TextMessage message = session.createTextMessage(text);
            for (int i = 0; i < 10; i++) {
                // 7、发送消息
                producer.send(message);
            }
            // 8、 关闭连接
            session.close();
            conn.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
