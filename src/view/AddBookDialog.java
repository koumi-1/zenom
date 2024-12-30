package view;

import controller.BookController;
import model.Book;

import javax.swing.*;
import java.awt.*;

public class AddBookDialog extends JDialog {
    public AddBookDialog(JFrame parent, BookController bookController) {
        super(parent, "Add Book", true);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField yearField = new JTextField();

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("Genre:"));
        panel.add(genreField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            int year = Integer.parseInt(yearField.getText());

            bookController.addBook(new Book(bookController.getBooks().size() + 1, title, author, genre, year));
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            dispose();
        });

        panel.add(new JLabel());
        panel.add(addButton);

        add(panel);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setVisible(true);

        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();
            String yearText = yearField.getText().trim();

            if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || yearText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int year = Integer.parseInt(yearText);
                bookController.addBook(new Book(bookController.getBooks().size() + 1, title, author, genre, year));
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Year must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
