package com.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.datatype.library.Book;

/**
 * Implementation of Book hibernate methods
 * 
 * @author Ed
 */
public class BookHibernate implements BookDAO {

    private static final String selectColumnsStmt = " SELECT b.book_id, b.book_name, b.quantity_in_stock ";

    private static final String simpleFrom = " from book b ";

    private static final String whereClauseOnBookId = " where b.book_name = :bookName ";


    @PersistenceContext(unitName = "EjbComponentPU2")
    private EntityManager entityManager;

    @Override
    @PersistenceContext(unitName = "EjbComponentPU2")
    public void insert(Book book) {
        entityManager = getEntityManager();

        entityManager.persist(book);
    }
    
    @Override
    @PersistenceContext(unitName = "EjbComponentPU2")
    public void update(Book book) {
        entityManager = getEntityManager();

        entityManager.merge(book);
    }

    @Override
    @PersistenceContext(unitName = "EjbComponentPU2")
    public Book selectBook(Book book) {
        List<Book> books = new ArrayList<Book>();

        entityManager = getEntityManager();

        Query q = entityManager.createNativeQuery(selectColumnsStmt + simpleFrom + whereClauseOnBookId, Book.class);
        q.setParameter("bookName", book.getBookName());

        books = q.getResultList();

        if (books.size() == 1) {
            return books.get(0);
        }

        return null;
    }

    @Override
    @PersistenceContext(unitName = "EjbComponentPU2")
    public List<Book> selectAllBooks() {
        List<Book> books = new ArrayList<Book>();

        entityManager = getEntityManager();
        Query q = entityManager.createNativeQuery(selectColumnsStmt + simpleFrom, Book.class);

        books = q.getResultList();

//        entityManager.close();
        return books;
    }

    public EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EjbComponentPU2");
        EntityManager entityManager = emf.createEntityManager();
        return entityManager;
    }


}
