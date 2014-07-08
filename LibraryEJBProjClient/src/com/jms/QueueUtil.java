package com.jms;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

/**
 * Class to assist with setup of JMS queues
 * 
 * @author ekinsella
 */
public class QueueUtil {

    private Queue queue;

    private ConnectionFactory factory;

    private Connection connection;

    private Session session;

    /**
     * Send serialiazable over JMS
     * 
     * @param message
     * @throws JMSException
     */
    public void sendMessage(Serializable message) throws JMSException {
        ObjectMessage msg = null;

        try {
            if (this.session == null) {
                throw new JMSException("JMS session is null");
            }

            MessageProducer producer = this.session.createProducer(this.queue);
            msg = this.session.createObjectMessage(message);
            producer.send(msg);

        } catch (JMSException e) {
            throw e;
        }
    }

    /**
     * Initialise local variables in QueueUtil
     * 
     * @param queueName
     * @param context
     * @throws JMSException 
     */
    public QueueUtil(String queueName) throws JMSException {
        try {
            TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName());

            this.factory = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);

            //The queue name should match the jms-queue name in standalone.xml
            this.queue = HornetQJMSClient.createQueue(queueName);
            this.connection = factory.createConnection();
            this.session = connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw e;
        }

    }
}
