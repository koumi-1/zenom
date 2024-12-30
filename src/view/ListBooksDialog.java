package view;

import controller.BookController;
import model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListBooksDialog extends JDialog {
    public ListBooksDialog(JFrame parent, BookController bookController) {
        super(parent, "List Books", true);

        String[] columnNames = {"ID", "Title", "Author", "Genre", "Year"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        for (Book book : bookController.getBooks()) {
            tableModel.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getYear()});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Edit Button Action Listener
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int bookId = (int) tableModel.getValueAt(selectedRow, 0);
                Book book = bookController.getBooks().stream()
                        .filter(b -> b.getId() == bookId)
                        .findFirst()
                        .orElse(null);

                if (book != null) {
                    String newTitle = JOptionPane.showInputDialog(this, "Edit Title", book.getTitle());
                    String newAuthor = JOptionPane.showInputDialog(this, "Edit Author", book.getAuthor());
                    String newGenre = JOptionPane.showInputDialog(this, "Edit Genre", book.getGenre());
                    String newYearStr = JOptionPane.showInputDialog(this, "Edit Year", book.getYear());

                    try {
                        int newYear = Integer.parseInt(newYearStr);

                        book.setTitle(newTitle);
                        book.setAuthor(newAuthor);
                        book.setGenre(newGenre);
                        book.setYear(newYear);

                        bookController.addBook(book); // Update controller and file
                        tableModel.setValueAt(newTitle, selectedRow, 1);
                        tableModel.setValueAt(newAuthor, selectedRow, 2);
                        tableModel.setValueAt(newGenre, selectedRow, 3);
                        tableModel.setValueAt(newYear, selectedRow, 4);

                        JOptionPane.showMessageDialog(this, "Book updated successfully!");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid year format!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete Button Action Listener
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int bookId = (int) tableModel.getValueAt(selectedRow, 0);

                bookController.deleteBook(bookId); // Remove from controller and file
                tableModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(this, "Book deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
