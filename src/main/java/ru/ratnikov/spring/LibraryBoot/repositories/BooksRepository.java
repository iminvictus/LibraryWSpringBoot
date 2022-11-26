package ru.ratnikov.spring.LibraryBoot.repositories;

import ru.ratnikov.spring.LibraryBoot.models.Book;
import ru.ratnikov.spring.LibraryBoot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitle(String name);

    List<Book> findByOwner(Person owner);

    List<Book> findByTitleStartingWithIgnoreCase(String startsWith);
}