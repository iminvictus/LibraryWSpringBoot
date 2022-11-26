package ru.ratnikov.spring.LibraryBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ratnikov.spring.LibraryBoot.util.CurrentYear;
import java.util.Date;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty
    @Size(min = 2, max = 200, message = "Title should be between 2 and 200 characters")
    private String title;

    @Column(name = "author")
    @NotEmpty
    @Size(min = 2, max = 100, message = "Author name should be between 2 and 100 characters")
    private String author;

    @Column(name = "publication_year")
    @Min(value = 1501, message = "Publication year must be greater than 1500")
    @CurrentYear
    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "taken_at")
    private Date takenAt;

    @Transient
    private boolean isExpired;

    public Book(String title, String author, int publicationYear, Person owner) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.owner = owner;
    }

    public boolean isExpired() {
        if (takenAt == null)
            return false;
        else
            return (new Date().getTime() - takenAt.getTime()) / 86400000 > 10;
    }
}
