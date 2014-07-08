package com.stateless;

import java.util.List;

import javax.ejb.Remote;

import com.datatype.library.Book;

@Remote
public interface LibraryStatelessSessionBeanRemote {
    
    void processBook(Book b);
	
	void addBook(Book b);
	
	List<Book> getBooks();

	void addCopyOfBook(Book b);
}
