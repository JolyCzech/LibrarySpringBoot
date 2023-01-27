package ru.elikhanov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elikhanov.library.models.Book;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer>  {

    List<Book> findByTitleStartingWith(String startingWith);


}
