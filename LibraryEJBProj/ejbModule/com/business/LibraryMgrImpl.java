package com.business;

import java.util.List;

import com.datatype.library.Book;
import com.jdbc.BookDAO;
import com.jdbc.BookHibernate;

/**
 * Implementation class for Library business ops
 *  
 * @author Ed
 */
public class LibraryMgrImpl implements ILibraryMgr {

    private BookDAO bookDAO;

    private List<Book> books;

    @Override
    public void createOrUpdateBook(Book book) {

        boolean bookAlreadyInLibrary = false;
        Book bookInLibrary = null;

        books = getAllBooks();
        for (Book b : books) {
            if (b.getBookName().equalsIgnoreCase(book.getBookName())) {
                bookAlreadyInLibrary = true;
                bookInLibrary = b;
                break;
            }
        }

        if (bookAlreadyInLibrary) {
            addCopyOfBook(bookInLibrary);
        } else {
            // First copy of this book in the library
            book.setQuantityInStock(1);
            bookDAO.insert(book);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDAO.selectAllBooks();
    }

    public LibraryMgrImpl() {
        bookDAO = new BookHibernate();
    }

    public void addCopyOfBook(Book book) {
        Book existingBook = bookDAO.selectBook(book);

        existingBook.setQuantityInStock(existingBook.getQuantityInStock() + 1);

        bookDAO.update(existingBook);
    }

}
