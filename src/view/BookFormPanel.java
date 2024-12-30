package view;

import javax.swing.*;
import java.awt.*;

public class BookFormPanel extends JPanel {
    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField genreField;
    private final JTextField yearField;
    private final JButton saveButton;
    private final JButton cancelButton;

    public BookFormPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(20);
        add(titleField, gbc);

        // Author Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        authorField = new JTextField(20);
        add(authorField, gbc);

        // Genre Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        genreField = new JTextField(20);
        add(genreField, gbc);

        // Year Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        yearField = new JTextField(20);
        add(yearField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    // Getters for fields
    public String getTitle() {
        return titleField.getText().trim();
    }

    public String getAuthor() {
        return authorField.getText().trim();
    }

    public String getGenre() {
        return genreField.getText().trim();
    }

    public String getYear() {
        return yearField.getText().trim();
    }

    // Setters for fields
    public void setTitle(String title) {
        titleField.setText(title);
    }

    public void setAuthor(String author) {
        authorField.setText(author);
    }

    public void setGenre(String genre) {
        genreField.setText(genre);
    }

    public void setYear(String year) {
        yearField.setText(year);
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
