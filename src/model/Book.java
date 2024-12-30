package model;

import lombok.*;

@Data
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int year;
}
