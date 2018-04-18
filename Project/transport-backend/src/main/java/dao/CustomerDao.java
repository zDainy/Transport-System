package dao;

import common.Customer;
import common.DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDao {

    public static boolean isAuthenticationSuccess(String login, String password) {
        int count = 0;
        try {
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "SELECT COUNT(*) FROM customer" +
                            " WHERE login = ?" +
                            " AND password = ?;"
            );
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                count = res.getInt("count");
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return count == 1;
    }

    public static int getCustomerId(String login) {
        int id = 0;
        try {
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "SELECT id FROM customer" +
                            " WHERE login = ?;"
            );
            statement.setString(1, login);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                id = res.getInt("id");
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return id;
    }

    public static Customer getCustomer(String login) {
        int id = 0;
        String name = "";
        String email = "";
        String phone = "";
        String address = "";
        try {
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "SELECT c.id, ci.name, ci.email, ci.phone, ci.delivery_addr FROM customer c, cust_info ci" +
                            " WHERE c.id = ci.cust_id" +
                            " AND c.login = ?;"
            );
            statement.setString(1, login);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                id = res.getInt("id");
                name = res.getString("name");
                email = res.getString("email");
                phone = res.getString("phone");
                address = res.getString("delivery_addr");
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return new Customer(id, login, name, email, phone, address);
    }

    public static boolean isCustomerExist(String login) {
        int count = 0;
        try {
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "SELECT COUNT(*) FROM customer" +
                            " WHERE login = ?;"
            );
            statement.setString(1, login);
            ResultSet searchRes = statement.executeQuery();
            if (searchRes.next()) {
                count = searchRes.getInt("count");
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return count == 1;
    }

    public static void createCustomer(String login, String password) {
        try {
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "INSERT INTO customer (login, password)" +
                            " VALUES (?, ?);"
            );
            statement.setString(1, login);
            statement.setString(2, password);
            statement.executeQuery();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public static void setCustomerInfo(String login, String name, String email, String phone, String address) {
        int count = 0;
        try {
            PreparedStatement searchStatement = DBService.getConnection().prepareStatement(
                    "SELECT COUNT(*) FROM cust_info" +
                            " WHERE id IN (SELECT id FROM customer WHERE login = ?);"
            );
            searchStatement.setString(1, login);
            ResultSet res = searchStatement.executeQuery();
            if (res.next()) {
                count = res.getInt("count");
            }
            if (count == 0) {
                int id = getCustomerId(login);
                PreparedStatement statement = DBService.getConnection().prepareStatement(
                        "INSERT INTO cust_info(name, email, phone, delivery_addr, cust_id) VALUES" +
                                " (?, ?, ?, ?, ?);"
                );
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, phone);
                statement.setString(4, address);
                statement.setInt(5, id);
                statement.executeQuery();
            } else {
                PreparedStatement statement = DBService.getConnection().prepareStatement(
                        "UPDATE cust_info SET" +
                                " name = ?," +
                                " email = ?," +
                                " phone = ?," +
                                " delivery_addr = ?" +
                                " WHERE id IN (SELECT id FROM customer WHERE login = ?);"
                );
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, phone);
                statement.setString(4, address);
                statement.setString(5, login);
                statement.executeQuery();
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}