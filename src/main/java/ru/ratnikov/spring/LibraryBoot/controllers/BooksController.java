package ru.ratnikov.spring.LibraryBoot.controllers;

import jakarta.validation.Valid;
import ru.ratnikov.spring.LibraryBoot.models.Book;
import ru.ratnikov.spring.LibraryBoot.models.Person;
import ru.ratnikov.spring.LibraryBoot.services.BooksService;
import ru.ratnikov.spring.LibraryBoot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
                        @RequestParam(value = "books_per_page", required = false, defaultValue = "-1") int booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear,
                        Model model) {
        List<Book> books;
        if (page != -1 && booksPerPage != -1) {
            books = sortByYear ? booksService.findAndPageAndSortByYear(page, booksPerPage)
                    : booksService.findAndPage(page, booksPerPage);
        } else if (page == -1 && booksPerPage == -1 && sortByYear) {
            books = booksService.findAndSortByYear();
        } else {
            books = booksService.findAll();
        }
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findById(id);
        model.addAttribute("book", book);
        if (book.getOwner() != null)
            model.addAttribute("owner", book.getOwner());
        else
            model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.assignBook(person, id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        booksService.releaseBook(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "") String startsWith,
                         Model model) {
        model.addAttribute("startsWith", startsWith);
        if (!startsWith.isEmpty())
            model.addAttribute("results", booksService.findByTitleStartingWith(startsWith));
        return "books/search";
    }
}
