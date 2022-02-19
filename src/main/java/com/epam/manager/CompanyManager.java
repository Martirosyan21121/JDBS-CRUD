package com.epam.manager;

import com.epam.config.DBConnectionProvider;
import com.epam.managerImpl.CompanyManagerImpl;
import com.epam.model.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyManager implements CompanyManagerImpl<Company, Integer> {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    @Override
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM company");

            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setAddress(resultSet.getString("address"));
                company.setName(resultSet.getString("name"));
                company.setSize(resultSet.getInt("size"));
                company.setEmail(resultSet.getString("email"));
                company.setphoneNumber(resultSet.getInt("phoneNumber"));
                companies.add(company);
            }
        } catch (SQLException e) {
            System.out.println("OOPS, something wrong during getAll");
        }
        return companies;
    }

    @Override
    public Company getById(Integer id) {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM company WHERE id=" + id);
            if (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setAddress(resultSet.getString("address"));
                company.setName(resultSet.getString("name"));
                company.setSize(resultSet.getInt("size"));
                company.setEmail(resultSet.getString("email"));
                company.setphoneNumber(resultSet.getInt("phoneNumber"));
                return company;
            }
        } catch (SQLException e) {
            System.out.println("OOPS, something wrong during get By ID");
        }
        return null;
    }

    @Override
    public void create(Company object) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO company(address, name, size, email, phoneNumber) " +
                    "VALUE('" + object.getAddress() + "','" + object.getName() + "','" + object.getSize() + "','" + object.getEmail() + "','" + object.getphoneNumber() + "')");
        } catch (SQLException e) {
            throw new RuntimeException("OOPS, something wrong during create");
        }
    }

    @Override
    public void update(Company object) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE company SET name='" + object.getName()
                    + "',address='" + object.getAddress() + "',size='" + object.getSize() + "',email='" + object.getEmail() + "',phoneNumber='" + object.getphoneNumber() + "'WHERE id=" + object.getId());

        } catch (SQLException e) {
            throw new RuntimeException("OOPS, something wrong during update");
        }
    }


    @Override
    public void delete(Integer id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM company WHERE id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException("OOPS, something wrong during delete");
        }
    }

}
