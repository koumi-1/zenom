package controller;

import lombok.Getter;
import model.User;
import utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserController {
    private static final String USERS_FILE = "resources/users.csv";
    private List<User> users;

    public UserController() {
        this.users = new ArrayList<User>();
        this.loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        users.addAll(CSVUtils.readCSV(USERS_FILE).stream().map(row -> new User(Integer.parseInt(row[0]), row[1], row[2], row[3])).toList());
    }

    private void saveUsersToFile() {
        List<String[]> data = users.stream().map(user -> new String[]{String.valueOf(user.getId()), user.getName(), user.getEmail(), user.getRole()}).toList();
        CSVUtils.writeCSV(USERS_FILE, data);
    }

    public void addUser(User user) {
        this.users.add(user);
        this.saveUsersToFile();
        System.out.printf("User added: %s.\n", user.getName());
    }

    public void editUser(User paramUser) {
        this.users.stream().filter(user -> user.getId() == paramUser.getId()).findFirst().ifPresentOrElse(user -> {
            user.setName(paramUser.getName());
            user.setEmail(paramUser.getEmail());
            user.setRole(paramUser.getRole());
            System.out.printf("User edited: %s.\n", paramUser.getName());
        }, () -> {
            System.out.printf("User with ID %s not found.\n", paramUser.getId());
        });
        this.saveUsersToFile();
    }

    public void deleteUser(int id) {
        this.users.removeIf(user -> user.getId() == id);
        this.saveUsersToFile();
        System.out.printf("User with ID %s deleted.\n", id);
    }
}
