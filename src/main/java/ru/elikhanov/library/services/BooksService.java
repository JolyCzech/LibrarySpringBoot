package ru.elikhanov.library.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.elikhanov.library.models.Book;
import ru.elikhanov.library.models.Person;
import ru.elikhanov.library.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;

    public List<Book> findAllBooks() {
        return booksRepository.findAll();
    }

    public List<Book> findPagedBooks(int page, int books_per_page) {
        return booksRepository.
                findAll(PageRequest.of(page, books_per_page)).getContent();
    }

    public List<Book> findPagedBooksSortedByYear(int page, int books_per_page) {
        return booksRepository.
                findAll(PageRequest.of(page, books_per_page, Sort.by("yearOfRelease"))).getContent();
    }

    public List<Book> findSortedPages() {
        return booksRepository.
                findAll(Sort.by("yearOfRelease"));
    }


    public List<Book> searchByTitle(String startingWith) {
        return booksRepository.findByTitleStartingWith(startingWith);
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        book.setDateBookPicked(new Date());
        booksRepository.save(book);

    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }


    @Transactional
    public void deleteBookUser(int id) {
        Optional<Book> book = booksRepository.findById(id);
        book.ifPresent(value -> value.setUser(null));
        book.ifPresent(value -> value.setDateBookPicked(null));
    }

    @Transactional
    public Optional<Person> getUser(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return Optional.ofNullable(book.get().getUser());
    }

    @Transactional
    public void addBookUser(Person newUser, int id) {
        Optional<Book> book = booksRepository.findById(id);
        book.ifPresent(value -> value.setUser(newUser));
        book.ifPresent(value -> value.setDateBookPicked(new Date()));
    }

}
