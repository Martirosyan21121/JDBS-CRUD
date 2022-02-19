package com.epam.manager;

import com.epam.config.DBConnectionProvider;
import com.epam.managerImpl.CompanyManagerImpl;
import com.epam.managerImpl.UserManagerImpl;
import com.epam.model.Company;
import com.epam.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements UserManagerImpl<User, Integer> {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();


    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `user`");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setSurname(resultSet.getString("surname"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone_number(resultSet.getInt("phone_number"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("OOPS, something wrong during getAll");
        }
        return users;
    }



    @Override
    public User getById(Integer id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `user` WHERE id=" + id);
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setSurname(resultSet.getString("surname"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone_number(resultSet.getInt("phone_number"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("OOPS, something wrong during get By ID");
        }
        return null;
    }


    @Override
    public void create(User object) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO `user`(surname, name, phone_number, email, password) " +
                    "VALUE('" + object.getSurname() + "','" + object.getName() + "','" + object.getPassword() + "','" + object.getEmail() + "','" + object.getPhone_number() + "')");
        } catch (SQLException e) {
            throw new RuntimeException("OOPS, something wrong during create");
        }
    }

    @Override
    public void update(User object) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE `user` SET name='" + object.getName()
                    + "',surname='" + object.getSurname() + "',password='" + object.getPassword() + "',email='" + object.getEmail() + "',phone_number='" + object.getPhone_number() + "'WHERE id=" + object.getId());

        } catch (SQLException e) {
            throw new RuntimeException("OOPS, something wrong during update");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM `user` WHERE id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException("OOPS, something wrong during delete");
        }
    }
}
