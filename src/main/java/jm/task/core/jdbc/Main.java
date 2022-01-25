package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        User first = new User("Bob", "Spondge", (byte) 16);
        User second = new User("Tom", "Ford", (byte) 23);
        User third = new User("David", "Funny", (byte) 15);
        User forth = new User("Kate", "Nice", (byte) 33);

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser(first.getName(), first.getLastName(), first.getAge());
        System.out.println("User с именем - "+first.getName()+" добавлен в базу данных");

        userService.saveUser(second.getName(), second.getLastName(), second.getAge());
        System.out.println("User с именем - "+second.getName()+" добавлен в базу данных");

        userService.saveUser(third.getName(), third.getLastName(), third.getAge());
        System.out.println("User с именем - "+third.getName()+" добавлен в базу данных");

        userService.saveUser(forth.getName(), forth.getLastName(), forth.getAge());
        System.out.println("User с именем - "+forth.getName()+" добавлен в базу данных");

        List<User> list = userService.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}

