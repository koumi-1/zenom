package view;

import controller.BookController;
import controller.UserController;
import model.Book;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryManagementView extends JFrame {
    private final BookController bookController;
    private final UserController userController;

    private DefaultTableModel bookTableModel;
    private DefaultTableModel userTableModel;

    public LibraryManagementView(BookController bookController, UserController userController) {
        this.bookController = bookController;
        this.userController = userController;

        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLayout(new BorderLayout());

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Books Tab
        JPanel booksPanel = createBooksPanel();
        tabbedPane.addTab("Books", booksPanel);

        // Users Tab
        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("Users", usersPanel);

        add(tabbedPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createBooksPanel() {
        JPanel booksPanel = new JPanel(new BorderLayout());
        booksPanel.setBorder(BorderFactory.createTitledBorder("Books"));

        // Table Setup
        String[] bookColumnNames = {"ID", "Title", "Author", "Genre", "Year"};
        bookTableModel = new DefaultTableModel(bookColumnNames, 0);
        JTable bookTable = new JTable(bookTableModel);
        bookTable.setRowHeight(25);
        refreshBookTable();

        booksPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBookButton = new JButton("Add Book");
        JButton editBookButton = new JButton("Edit Book");
        JButton deleteBookButton = new JButton("Delete Book");

        buttonPanel.add(addBookButton);
        buttonPanel.add(editBookButton);
        buttonPanel.add(deleteBookButton);

        booksPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addBookButton.addActionListener(e -> openBookForm(null));
        editBookButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                int bookId = (int) bookTableModel.getValueAt(selectedRow, 0);
                Book book = bookController.getBooks().stream()
                        .filter(b -> b.getId() == bookId)
                        .findFirst()
                        .orElse(null);
                openBookForm(book);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteBookButton.addActionListener(e -> deleteBook(bookTable));

        return booksPanel;
    }

    private JPanel createUsersPanel() {
        JPanel usersPanel = new JPanel(new BorderLayout());
        usersPanel.setBorder(BorderFactory.createTitledBorder("Users"));

        // Table Setup
        String[] userColumnNames = {"ID", "Name", "Email", "Role"};
        userTableModel = new DefaultTableModel(userColumnNames, 0);
        JTable userTable = new JTable(userTableModel);
        userTable.setRowHeight(25);
        refreshUserTable();

        usersPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addUserButton = new JButton("Add User");
        JButton editUserButton = new JButton("Edit User");
        JButton deleteUserButton = new JButton("Delete User");

        buttonPanel.add(addUserButton);
        buttonPanel.add(editUserButton);
        buttonPanel.add(deleteUserButton);

        usersPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addUserButton.addActionListener(e -> openUserForm(null));
        editUserButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) userTableModel.getValueAt(selectedRow, 0);
                User user = userController.getUsers().stream()
                        .filter(u -> u.getId() == userId)
                        .findFirst()
                        .orElse(null);
                openUserForm(user);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteUserButton.addActionListener(e -> deleteUser(userTable));

        return usersPanel;
    }

    private void openBookForm(Book book) {
        BookFormPanel bookForm = new BookFormPanel();
        if (book != null) {
            bookForm.setTitle(book.getTitle());
            bookForm.setAuthor(book.getAuthor());
            bookForm.setGenre(book.getGenre());
            bookForm.setYear(String.valueOf(book.getYear()));
        }

        int result = JOptionPane.showConfirmDialog(this, bookForm, book == null ? "Add Book" : "Edit Book", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String title = bookForm.getTitle();
                String author = bookForm.getAuthor();
                String genre = bookForm.getGenre();
                int year = Integer.parseInt(bookForm.getYear());

                if (book == null) {
                    Book newBook = new Book(bookController.getBooks().size() + 1, title, author, genre, year);
                    bookController.addBook(newBook);
                } else {
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setYear(year);
                }
                refreshBookTable();
                JOptionPane.showMessageDialog(this, book == null ? "Book added successfully!" : "Book updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Year must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openUserForm(User user) {
        UserFormPanel userForm = new UserFormPanel();
        if (user != null) {
            userForm.setName(user.getName());
            userForm.setEmail(user.getEmail());
            userForm.setRole(user.getRole());
        }

        int result = JOptionPane.showConfirmDialog(this, userForm, user == null ? "Add User" : "Edit User", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = userForm.getName();
            String email = userForm.getEmail();
            String role = userForm.getRole();

            if (user == null) {
                User newUser = new User(userController.getUsers().size() + 1, name, email, role);
                userController.addUser(newUser);
            } else {
                user.setName(name);
                user.setEmail(email);
                user.setRole(role);
            }
            refreshUserTable();
            JOptionPane.showMessageDialog(this, user == null ? "User added successfully!" : "User updated successfully!");
        }
    }

    private void deleteBook(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = (int) bookTableModel.getValueAt(selectedRow, 0);
            bookController.deleteBook(bookId);
            refreshBookTable();
            JOptionPane.showMessageDialog(this, "Book deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) userTableModel.getValueAt(selectedRow, 0);
            userController.deleteUser(userId);
            refreshUserTable();
            JOptionPane.showMessageDialog(this, "User deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshBookTable() {
        bookTableModel.setRowCount(0); // Clear the table
        for (Book book : bookController.getBooks()) {
            bookTableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getYear()
            });
        }
    }

    private void refreshUserTable() {
        userTableModel.setRowCount(0); // Clear the table
        for (User user : userController.getUsers()) {
            userTableModel.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            });
        }
    }
}
