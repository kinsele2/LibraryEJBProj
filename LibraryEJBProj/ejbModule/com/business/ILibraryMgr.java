package com.business;

import java.util.List;

import com.datatype.library.Book;

/**
 * Interface for Library business ops
 *  
 * @author Ed
 */
public interface ILibraryMgr {

    void createOrUpdateBook(Book book);
    
    List<Book> getAllBooks();
}
