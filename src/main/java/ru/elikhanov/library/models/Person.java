package ru.elikhanov.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * @author Elikhanov
 */

@Entity
@Table(name = "person")
@Data
public class Person {

    @OneToMany(mappedBy = "user")
    private List<Book> books;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, message = "Name should longer than 2")
    private String name;

    @Column(name = "age")
    @NotNull(message = "Year Of Release should not be empty")
    @Range(min = 14, message = "age may not be empty or less than 14")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be as: City, Country, Postal code (6 digits)")
    private String address;


    public Person(int personId, String name, int age) {
        this.personId = personId;
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "books=" + books +
                ", personId=" + personId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

