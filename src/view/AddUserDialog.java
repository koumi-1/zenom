package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class AddUserDialog extends JDialog {
    public AddUserDialog(JFrame parent, UserController userController) {
        super(parent, "Add User", true);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField roleField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Role:"));
        panel.add(roleField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String role = roleField.getText();

            userController.addUser(new User(userController.getUsers().size() + 1, name, email, role));
            JOptionPane.showMessageDialog(this, "User added successfully!");
            dispose();
        });

        panel.add(new JLabel());
        panel.add(addButton);

        add(panel);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setVisible(true);

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


    }
}
