import controller.BookController;
import controller.UserController;
import view.LibraryManagementView;

public class Main {
    public static void main(String[] args) {

        BookController bookController = new BookController();
        UserController userController = new UserController();

        new LibraryManagementView(bookController, userController);
    }
}
