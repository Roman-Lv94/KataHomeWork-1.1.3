package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Николай", "Иванов", (byte) 20);
        userService.saveUser("Роза", "Петрова", (byte) 25);
        userService.saveUser("Андрей", "Чернов", (byte) 31);
        userService.saveUser("Кирилл", "Федоров", (byte) 38);

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
