package ru.ratnikov.spring.LibraryBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import ru.ratnikov.spring.LibraryBoot.util.DateOfBirth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mikhail Ratnikov
 */
@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Full name should be between 2 and 100 characters")
    @Pattern(regexp = "[A-zА-я]+ [A-zА-я]+",
            message = "Full name should consist of first name and last name, like that: Albert Scott")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books;

    @Column(name = "email")
    @NotEmpty(message = "EMail should not be empty")
    @Email(message = "Email is not valid")
    private String email;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Past(message = "Date cannot be greater than today")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public void addBook(Book book) {
        if (this.books == null)
            this.books = new ArrayList<>();

        this.books.add(book);
        book.setOwner(this);
    }

    @Override
    public String toString() {
        return name + ", " + dateOfBirth + ", id " + id;
    }

}
