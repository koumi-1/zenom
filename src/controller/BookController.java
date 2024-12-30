package controller;

import lombok.Getter;
import model.Book;
import utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;



@Getter
public class BookController {
    private static final String BOOKS_FILE = "resources/books.csv";
    private final List<Book> books;

    public BookController() {
        this.books = new ArrayList<Book>();
        this.loadBooksFromFile();
    }

    private void loadBooksFromFile() {
        books.addAll(CSVUtils.readCSV(BOOKS_FILE).stream().map(row -> new Book(Integer.parseInt(row[0]), row[1], row[2], row[3], Integer.parseInt(row[4]))).toList());
    }

    private void saveBooksToFile() {
        List<String[]> data = books.stream().map(book -> new String[]{String.valueOf(book.getId()), book.getTitle(), book.getAuthor(), book.getGenre(), String.valueOf(book.getId())}).toList();
        CSVUtils.writeCSV(BOOKS_FILE, data);
    }

    public void addBook(Book book) {
        this.books.add(book);
        this.saveBooksToFile();
        System.out.printf("Book added: %s.\n", book.getTitle());
    }

    public void editBook(Book paramBook) {
        this.books.stream().filter(book -> book.getId() == paramBook.getId()).findFirst().ifPresentOrElse(book -> {
            book.setTitle(paramBook.getTitle());
            book.setAuthor(paramBook.getAuthor());
            book.setYear(paramBook.getYear());
            System.out.printf("Book edited: %s.\n", paramBook.getTitle());
        }, () -> {
            System.out.printf("Book with ID %s not found.\n", paramBook.getId());
        });
    }

    public void deleteBook(int id) {
        this.books.removeIf(book -> book.getId() == id);
        System.out.printf("Book deleted: %s.\n", id);
    }

    //    public List<Book> listBooks() {
    public void listBooks() {
        if (this.books.isEmpty()) System.out.println("No books found");
        else this.books.forEach(System.out::println);
    }
}
