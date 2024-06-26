package com.studia.biblioteka.dao.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.studia.biblioteka.dao.enums.CopyStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Book book;

    @Enumerated(EnumType.STRING)
    private CopyStatus status;

    private String location;

    public Copy(Long id, Book book, CopyStatus status, String location) {
        this.id = id;
        this.book = book;
        this.status = status;
        this.location = location;
    }

    public Copy() {

    }

// getters and setters
}
