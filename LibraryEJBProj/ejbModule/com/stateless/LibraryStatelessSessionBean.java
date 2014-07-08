package com.stateless;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.business.LibraryMgrImpl;
import com.datatype.library.Book;
import com.datatype.library.Book.SaveAction;

/**
 * Session Bean implementation class LibraryStatelessSessionBean
 */
@Stateless
@LocalBean
public class LibraryStatelessSessionBean implements LibraryStatelessSessionBeanRemote {

    private LibraryMgrImpl libraryMgr;

    // Books stored in the library
    List<Book> books;

    /**
     * Default constructor. 
     */
    public LibraryStatelessSessionBean() {
        books = new ArrayList<Book>();
        libraryMgr = new LibraryMgrImpl();
    }

    @Override
    public void addBook(Book book) {
        libraryMgr.createOrUpdateBook(book);
    }

    @Override
    public List<Book> getBooks() {
        return libraryMgr.getAllBooks();
    }

    @Override
    public void addCopyOfBook(Book book) {
        libraryMgr.addCopyOfBook(book);
    }

    @Override
    public void processBook(Book b) {
        if (b.getSaveAction().equals(SaveAction.BOOK_ADD)) {
            addBook(b);
        }
    }

}
