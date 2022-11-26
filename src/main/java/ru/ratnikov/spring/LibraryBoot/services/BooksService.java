package ru.ratnikov.spring.LibraryBoot.services;

import ru.ratnikov.spring.LibraryBoot.models.Book;
import ru.ratnikov.spring.LibraryBoot.models.Person;
import ru.ratnikov.spring.LibraryBoot.repositories.BooksRepository;
import ru.ratnikov.spring.LibraryBoot.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book bookInDb = booksRepository.findById(id).orElse(null);
        if (bookInDb != null) {
            book.setOwner(bookInDb.getOwner());
            book.setTakenAt(bookInDb.getTakenAt());
        }
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assignBook (Person personToAssign, int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setOwner(personToAssign);
            book.setTakenAt(new Date());
            booksRepository.save(book);
        }
    }

    @Transactional
    public void releaseBook (int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setOwner(null);
            book.setTakenAt(null);
            booksRepository.save(book);
        }
    }

    public List<Book> findByTitle (String title) {
        return booksRepository.findByTitle(title);
    }

    public List<Book> findByOwner(Person owner) {
        return booksRepository.findByOwner(owner);
    }

    public List<Book> findAndPage (int page, int itemsOnPage) {
        return booksRepository.findAll(PageRequest.of(page, itemsOnPage)).getContent();
    }

    public List<Book> findAndSortByYear() {
        return booksRepository.findAll(Sort.by("publicationYear"));
    }

    public List<Book> findAndPageAndSortByYear(int page, int itemsOnPage) {
        return booksRepository.findAll(PageRequest.of(page, itemsOnPage, Sort.by("publicationYear"))).getContent();
    }

    public List<Book> findByTitleStartingWith(String startsWith) {
        return booksRepository.findByTitleStartingWithIgnoreCase(startsWith);
    }
}
