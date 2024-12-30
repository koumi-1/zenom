package view;

import controller.BookController;
import controller.UserController;

import javax.swing.*;

public class LibraryManagementView extends JFrame {
    private final BookController bookController;
    private final UserController userController;

    public LibraryManagementView(BookController bookController, UserController userController) {

        LoginDialog loginDialog = new LoginDialog(this);
        if (!loginDialog.isAuthenticated()) {
            System.exit(0);
        }


        this.bookController = bookController;
        this.userController = userController;

        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Setup the menu
        setupMenu();

        setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Books Menu
        JMenu booksMenu = new JMenu("Books");
        JMenuItem addBookItem = new JMenuItem("Add Book");
        JMenuItem listBooksItem = new JMenuItem("List Books");
        booksMenu.add(addBookItem);
        booksMenu.add(listBooksItem);

        // Users Menu
        JMenu usersMenu = new JMenu("Users");
        JMenuItem addUserItem = new JMenuItem("Add User");
        JMenuItem listUsersItem = new JMenuItem("List Users");
        usersMenu.add(addUserItem);
        usersMenu.add(listUsersItem);

        // Add menus to the menu bar
        menuBar.add(booksMenu);
        menuBar.add(usersMenu);

        setJMenuBar(menuBar);

        // Add action listeners for menu items
        addBookItem.addActionListener(e -> new AddBookDialog(this, bookController));
        listBooksItem.addActionListener(e -> new ListBooksDialog(this, bookController));

        addUserItem.addActionListener(e -> new AddUserDialog(this, userController));
        listUsersItem.addActionListener(e -> new ListUsersDialog(this, userController));

    }
}
