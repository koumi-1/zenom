package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListUsersDialog extends JDialog {
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ListUsersDialog(JFrame parent, UserController userController) {
        super(parent, "Manage Users", true);

        setLayout(new BorderLayout());

        // Table Setup
        String[] columnNames = {"ID", "Name", "Email", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        refreshTable(userController);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("Edit User");
        JButton deleteButton = new JButton("Delete User");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add User Action
        addButton.addActionListener(e -> {
            UserFormPanel userForm = new UserFormPanel();
            int result = JOptionPane.showConfirmDialog(this, userForm, "Add User", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String name = userForm.getName();
                String email = userForm.getEmail();
                String role = userForm.getRole();

                if (!name.isEmpty() && !email.isEmpty() && !role.isEmpty()) {
                    User newUser = new User(userController.getUsers().size() + 1, name, email, role);
                    userController.addUser(newUser);
                    refreshTable(userController);
                    JOptionPane.showMessageDialog(this, "User added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Edit User Action
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) tableModel.getValueAt(selectedRow, 0);
                User user = userController.getUsers().stream()
                        .filter(u -> u.getId() == userId)
                        .findFirst()
                        .orElse(null);

                if (user != null) {
                    UserFormPanel userForm = new UserFormPanel();
                    userForm.setName(user.getName());
                    userForm.setEmail(user.getEmail());
                    userForm.setRole(user.getRole());

                    int result = JOptionPane.showConfirmDialog(this, userForm, "Edit User", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        user.setName(userForm.getName());
                        user.setEmail(userForm.getEmail());
                        user.setRole(userForm.getRole());
                        refreshTable(userController);
                        JOptionPane.showMessageDialog(this, "User updated successfully!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete User Action
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) tableModel.getValueAt(selectedRow, 0);
                userController.deleteUser(userId);
                refreshTable(userController);
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setSize(700, 500);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void refreshTable(UserController userController) {
        tableModel.setRowCount(0);  // Clear the table
        for (User user : userController.getUsers()) {
            tableModel.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            });
        }
    }
}
