package ru.elikhanov.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.Date;

/**
 * @author Elikhanov
 */

@Entity
@Table(name = "book")
@Data
public class Book {

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "title")
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, message = "Title should longer than 2")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, message = "Author should longer than 2")
    private String author;

    @Column(name = "year_Of_Release")
    @NotNull(message = "Year Of Release should not be empty")
    @Min(value = 1800, message = "Age should be more than 1800")
    private int yearOfRelease;

    @Column(name = "date_book_picked")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBookPicked;

    @Transient
    private boolean bookExpired;


    public Book(int id, String title, String author, int yearOfRelease) {
        this.bookId = id;
        this.title = title;
        this.author = author;
        this.yearOfRelease = yearOfRelease;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "user=" + user +
                ", bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", dateBookPicked=" + dateBookPicked +
                '}';
    }


}
