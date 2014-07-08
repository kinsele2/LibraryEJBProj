package com.jdbc;

import java.util.List;

import com.datatype.library.Book;

/**
 * Persistence class for Book objects
 * 
 * @author Ed
 */
public interface BookDAO {
    void insert(Book book);
    
    void update(Book book);
    
    List<Book> selectAllBooks();
    
    Book selectBook(Book book);
}
