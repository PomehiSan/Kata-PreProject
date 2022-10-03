package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        List<User> users = new ArrayList<>();
        users.add(new User("Сережка", "Маслов", (byte) 54));
        users.add(new User("Михаил", "Отвальный", (byte) 27));
        users.add(new User("Валерий", "Жмышенко", (byte) 54));
        users.add(new User("Алишер", "Обычный", (byte) 22));

        userService.createUsersTable();

        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.format("User с именем – %s добавлен в базу данных\n", user.getName());
        }

        List<User> usersFromDb = userService.getAllUsers();
        for (User user : usersFromDb) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
