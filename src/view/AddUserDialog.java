package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class AddUserDialog extends JDialog {
    public AddUserDialog(JFrame parent, UserController userController) {
        super(parent, "Add User", true);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Fields
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField roleField = new JTextField(20);

        // Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        add(new JLabel("Role:"), gbc);

        // Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameField, gbc);
        gbc.gridy++;
        add(emailField, gbc);
        gbc.gridy++;
        add(roleField, gbc);

        // Tooltips
        nameField.setToolTipText("Enter the full name of the user.");
        emailField.setToolTipText("Enter a valid email address.");
        roleField.setToolTipText("Enter the user's role (e.g., Member, Librarian, Admin).");

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
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String role = roleField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            userController.addUser(new User(userController.getUsers().size() + 1, name, email, role));
            JOptionPane.showMessageDialog(this, "User added successfully!");
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
