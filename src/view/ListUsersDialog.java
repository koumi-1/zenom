package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListUsersDialog extends JDialog {
    public ListUsersDialog(JFrame parent, UserController userController) {
        super(parent, "List Users", true);

        String[] columnNames = {"ID", "Name", "Email", "Role"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        for (User user : userController.getUsers()) {
            tableModel.addRow(new Object[]{user.getId(), user.getName(), user.getEmail(), user.getRole()});
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
                int userId = (int) tableModel.getValueAt(selectedRow, 0);
                User user = userController.getUsers().stream()
                        .filter(u -> u.getId() == userId)
                        .findFirst()
                        .orElse(null);

                if (user != null) {
                    String newName = JOptionPane.showInputDialog(this, "Edit Name", user.getName());
                    String newEmail = JOptionPane.showInputDialog(this, "Edit Email", user.getEmail());
                    String newRole = JOptionPane.showInputDialog(this, "Edit Role", user.getRole());

                    user.setName(newName);
                    user.setEmail(newEmail);
                    user.setRole(newRole);

                    userController.addUser(user); // Update controller and file
                    tableModel.setValueAt(newName, selectedRow, 1);
                    tableModel.setValueAt(newEmail, selectedRow, 2);
                    tableModel.setValueAt(newRole, selectedRow, 3);

                    JOptionPane.showMessageDialog(this, "User updated successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete Button Action Listener
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) tableModel.getValueAt(selectedRow, 0);

                userController.deleteUser(userId); // Remove from controller and file
                tableModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(this, "User deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
