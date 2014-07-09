package com.stateless.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;

import com.client.utility.ClientUtility;
import com.datatype.library.Book;
import com.datatype.library.Book.SaveAction;
import com.jms.QueueUtil;
import com.stateless.LibraryStatelessSessionBeanRemote;


public class LibraryClient {

    private BufferedReader consoleReader;

    public static void main(String[] args) throws JMSException {
        LibraryClient client = new LibraryClient();
        LibraryStatelessSessionBeanRemote bean = client.doLookup();

        QueueUtil queueUtil = new QueueUtil("bookQueue");

        client.testStatelessEJB(bean, queueUtil);
    }

    private void testStatelessEJB(LibraryStatelessSessionBeanRemote bean, QueueUtil queueUtil) {
        int choice = 1;
        List<Book> bookList = null;
        consoleReader = new BufferedReader(new InputStreamReader(System.in));

        while (choice != 2) {
            String bookName;
            showGUI();

            try {
                String userChoice = consoleReader.readLine();
                choice = Integer.parseInt(userChoice);

                if (choice == 1) {
                    System.out.print("Enter book name: ");
                    bookName = consoleReader.readLine();

                    Book book = new Book();
                    book.setBookName(bookName);
                    book.setSaveAction(SaveAction.BOOK_ADD);

                    // Persist books via asynchronous MDB call
                    queueUtil.sendMessage(book);
                } else if (choice == 2) {
                    System.out.println("Book(s) entered so far: ");
                    bookList = bean.getBooks();
                    if (bookList.size() == 0) {
                        System.out.println("No books :(");
                    }
                    printBooks(bookList);
                    break;
                }

                System.out.println("Book(s) entered so far: ");
                bookList = bean.getBooks();
                printBooks(bookList);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JMSException e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * Print to screen the books in the library
     * 
     * @param bookList
     */
    private void printBooks(List<Book> bookList) {
        int i = 1;

        for (Book b : bookList) {
            System.out.println((i++) + ". " + b.getBookName() + "\t\t\t" + b.getQuantityInStock());
        }
    }

    private void showGUI() {
        System.out.println("**********************");
        System.out.println("Welcome to Book Store");
        System.out.println("**********************");
        System.out.print("Options \n1. Add Book\n2. Exit \nEnter Choice: ");
    }

    private LibraryStatelessSessionBeanRemote doLookup() {
        LibraryStatelessSessionBeanRemote bean = null;
        try {
            Context context = ClientUtility.getInitialContext();
            bean = (LibraryStatelessSessionBeanRemote) context.lookup(getLookupName());
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private String getLookupName() {
        return "ejb:" + "LibraryEJBProjEAR/LibraryEJBProj/LibraryStatelessSessionBean!com.stateless.LibraryStatelessSessionBeanRemote";
    }
}
