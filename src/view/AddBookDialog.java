package view;

import controller.BookController;
import model.Book;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class AddBookDialog extends JDialog {
    public AddBookDialog(JFrame parent, BookController bookController) {
        super(parent, "Add Book", true);

        // Use GridBagLayout for flexibility
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Fields
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField genreField = new JTextField(20);
        JTextField yearField = new JTextField(20);

        // Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Title:"), gbc);
        gbc.gridy++;
        add(new JLabel("Author:"), gbc);
        gbc.gridy++;
        add(new JLabel("Genre:"), gbc);
        gbc.gridy++;
        add(new JLabel("Year:"), gbc);

        // Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(titleField, gbc);
        gbc.gridy++;
        add(authorField, gbc);
        gbc.gridy++;
        add(genreField, gbc);
        gbc.gridy++;
        add(yearField, gbc);

        // Tooltips
        titleField.setToolTipText("Enter the title of the book.");
        authorField.setToolTipText("Enter the author's name.");
        genreField.setToolTipText("Enter the genre (e.g., Fiction, Non-Fiction).");
        yearField.setToolTipText("Enter the year of publication.");

        // Add and Cancel Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Action Listeners
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

        cancelButton.addActionListener(e -> dispose());

        yearField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateYear();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateYear();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateYear();
            }

            private void validateYear() {
                try {
                    Integer.parseInt(yearField.getText());
                    yearField.setBackground(Color.WHITE);
                } catch (NumberFormatException ex) {
                    yearField.setBackground(Color.PINK);
                }
            }
        });


        // Dialog settings
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
