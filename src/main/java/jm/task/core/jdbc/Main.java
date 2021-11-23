package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {

    public static void main(String[] args) {

        UserServiceImpl usi = new UserServiceImpl();
        usi.createUsersTable();
        usi.saveUser("Иван", "Черных", (byte) 27);
        usi.saveUser("Юлия", "Петрова", (byte) 67);
        usi.saveUser("Алексей", "Краснов", (byte) 42);
        usi.saveUser("Дмитрий", "Маликов", (byte) 30);
        System.out.println(usi.getAllUsers());
        usi.cleanUsersTable();
        usi.dropUsersTable();
    }
}
