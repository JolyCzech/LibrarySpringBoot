package ru.elikhanov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elikhanov.library.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {

    Person findByEmail(String email);
}
