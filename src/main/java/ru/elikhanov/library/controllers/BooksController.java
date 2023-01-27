package ru.elikhanov.library.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.elikhanov.library.models.Book;
import ru.elikhanov.library.models.Person;
import ru.elikhanov.library.services.BooksService;
import ru.elikhanov.library.services.PeopleService;


import java.util.List;
import java.util.Optional;


/**
 * @author Elikhanov
 */


@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BooksController {

    private BooksService booksService;
    private PeopleService peopleService;

    @GetMapping()
    public String index(Model model,
                        @RequestParam(name = "page") Optional<Integer> page,
                        @RequestParam(name = "books_per_page") Optional<Integer> books_per_page,
                        @RequestParam(name = "sort") Optional<Boolean> sort) {


        if (page.isPresent() && books_per_page.isPresent() && sort.isPresent()) {
            model.addAttribute("books", booksService.findPagedBooksSortedByYear(page.get(), books_per_page.get()));
        } else if (page.isPresent() && books_per_page.isPresent()) {
            model.addAttribute("books", booksService.findPagedBooks(page.get(), books_per_page.get()));
        } else if (sort.isPresent()) {
            model.addAttribute("books", booksService.findSortedPages());
        } else model.addAttribute("books", booksService.findAllBooks());


        return "books/index";
    }


    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "startingWith", required = false) String startingWith) {
        if (startingWith != null) {
            List<Book> searchBooks = booksService.searchByTitle(startingWith);
            model.addAttribute("searchBooks", searchBooks);
        } else {
            model.addAttribute("startingWith", startingWith);
        }
        return "books/search";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {

        model.addAttribute("book", booksService.findOne(id));

        Optional<Person> bookUser = booksService.getUser(id);

        if (bookUser.isPresent()) {
            model.addAttribute("user", bookUser.get());
        } else
            model.addAttribute("people", peopleService.findAllPeople());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }


    @PatchMapping("/{id}/deleteBookUser")
    public String deleteBookUser(@PathVariable("id") int id) {
        booksService.deleteBookUser(id);
        return "redirect:/books/" + id;
    }


    @PatchMapping("/{id}/addBookUser")
    public String addBookUser(@ModelAttribute("person") Person person,
                              @PathVariable("id") int id) {
        booksService.addBookUser(person, id);
        return "redirect:/books/" + id;
    }


}
