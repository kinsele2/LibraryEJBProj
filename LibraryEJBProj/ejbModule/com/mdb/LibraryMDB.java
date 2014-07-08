package com.mdb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.datatype.library.Book;
import com.stateless.LibraryStatelessSessionBeanRemote;

/**
 * Message-Driven Bean implementation class for: LibraryMDB
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/bookQueue") }, mappedName = "queue/bookQueue")
public class LibraryMDB implements MessageListener {

    @Resource
    private MessageDrivenContext mdctx;

    @EJB
    LibraryStatelessSessionBeanRemote libraryBean;

    /**
     * Default constructor. 
     */
    public LibraryMDB() {

    }

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        ObjectMessage objectMessage = null;

        try {
            objectMessage = (ObjectMessage) message;
            Book book = (Book) objectMessage.getObject();

            libraryBean.processBook(book);
        } catch (JMSException e) {
            mdctx.setRollbackOnly();
        }
    }

}
