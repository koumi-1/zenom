package view;

import controller.BookController;
import model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListBooksDialog extends JDialog {
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ListBooksDialog(JFrame parent, BookController bookController) {
        super(parent, "Manage Books", true);

        setLayout(new BorderLayout());

        // Table Setup
        String[] columnNames = {"ID", "Title", "Author", "Genre", "Year"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        refreshTable(bookController);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Book");
        JButton editButton = new JButton("Edit Book");
        JButton deleteButton = new JButton("Delete Book");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Book Action
        addButton.addActionListener(e -> {
            BookFormPanel bookForm = new BookFormPanel();
            int result = JOptionPane.showConfirmDialog(this, bookForm, "Add Book", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String title = bookForm.getTitle();
                    String author = bookForm.getAuthor();
                    String genre = bookForm.getGenre();
                    int year = Integer.parseInt(bookForm.getYear());

                    Book newBook = new Book(bookController.getBooks().size() + 1, title, author, genre, year);
                    bookController.addBook(newBook);
                    refreshTable(bookController);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Year must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Edit Book Action
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int bookId = (int) tableModel.getValueAt(selectedRow, 0);
                Book book = bookController.getBooks().stream()
                        .filter(b -> b.getId() == bookId)
                        .findFirst()
                        .orElse(null);

                if (book != null) {
                    BookFormPanel bookForm = new BookFormPanel();
                    bookForm.setTitle(book.getTitle());
                    bookForm.setAuthor(book.getAuthor());
                    bookForm.setGenre(book.getGenre());
                    bookForm.setYear(String.valueOf(book.getYear()));

                    int result = JOptionPane.showConfirmDialog(this, bookForm, "Edit Book", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            book.setTitle(bookForm.getTitle());
                            book.setAuthor(bookForm.getAuthor());
                            book.setGenre(bookForm.getGenre());
                            book.setYear(Integer.parseInt(bookForm.getYear()));

                            refreshTable(bookController);
                            JOptionPane.showMessageDialog(this, "Book updated successfully!");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Year must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete Book Action
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int bookId = (int) tableModel.getValueAt(selectedRow, 0);
                bookController.deleteBook(bookId);
                refreshTable(bookController);
                JOptionPane.showMessageDialog(this, "Book deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setSize(700, 500);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void refreshTable(BookController bookController) {
        tableModel.setRowCount(0);  // Clear the table
        for (Book book : bookController.getBooks()) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getYear()
            });
        }
    }
}
