package ru.job4j.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.library.model.Author;
import ru.job4j.library.model.Book;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            Book book1 = Book.of("Book #1");
            Book book2 = Book.of("Book #2");
            Book book3 = Book.of("Book #3");
            Book book4 = Book.of("Book #4");
            Book book5 = Book.of("Book #5");

            Author author1 = Author.of("Author #1");
            Author author2 = Author.of("Author #2");
            Author author3 = Author.of("Author #3");

            author1.addBook(book1);
            author1.addBook(book2);
            author2.addBook(book2);
            author2.addBook(book3);
            author3.addBook(book4);
            author3.addBook(book5);

            session.save(author1);
            session.save(author2);
            session.save(author3);
            
            Author author = session.get(Author.class, 1);
            session.remove(author);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
