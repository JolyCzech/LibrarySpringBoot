package ru.elikhanov.library.services;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.elikhanov.library.models.Book;
import ru.elikhanov.library.models.Person;
import ru.elikhanov.library.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@AllArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;


    public List<Person> findAllPeople() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional()
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPersonId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public Optional<Person> findByEmail(String email) {
        Optional<Person> person = Optional.ofNullable(peopleRepository.findByEmail(email));
        return Optional.ofNullable(person.orElse(null));
    }


    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();
            long currentData = System.currentTimeMillis();
            for (Book book : books) {
                int difference = (int) (currentData - book.getDateBookPicked().getTime());
                if (TimeUnit.MILLISECONDS.toDays(difference) > 10)
                    book.setBookExpired(true);
            }
            return books;
        } else {
            return Collections.emptyList();
        }

    }

}
