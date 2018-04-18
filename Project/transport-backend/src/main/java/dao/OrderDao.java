package dao;

import common.DBService;
import common.Global;
import common.Order;
import common.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class OrderDao {

    public static ArrayList<Order> getOrders(int minPriceFilter, int maxPriceFilter, String serviceName) {
        ArrayList<Order> orders = new ArrayList<>();
        String query;
        int id;
        Date dateCreated;
        int customerId;
        int serviceId;
        try {
            query = "SELECT id, date_created, customer_id, service_id FROM \"order\"";

            query += " WHERE service_id IN (SELECT id FROM service WHERE price BETWEEN " + minPriceFilter + " AND " + maxPriceFilter + ")";

            if (!serviceName.equals(Global.NONE)) {
                query += " AND " + "service_id IN (SELECT id FROM service WHERE name LIKE ('%" + serviceName + "%'))";
            }

            ResultSet res = DBService.getConnection().createStatement().executeQuery(query);
            while (res.next()) {
                id = res.getInt("id");
                dateCreated = res.getDate("date_created");
                customerId = res.getInt("customer_id");
                serviceId = res.getInt("service_id");
                orders.add(new Order(id, dateCreated, customerId, serviceId));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return orders;
    }

    public static ArrayList<Service> getServicesInOrder(String login) {
        String ids = "";
        boolean isFirst = true;
        try {
            System.out.println("Start getServicesInOrder");
            int custId = CustomerDao.getCustomerId(login);
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "SELECT service_id FROM \"service_order\"" +
                            " WHERE \"order_id\" = (SELECT id FROM \"order\" WHERE customer_id = ?);"
            );
            statement.setInt(1, custId);
            ResultSet res = statement.executeQuery();
            System.out.println("End getServicesInOrder");
            while (res.next()) {
                ids += isFirst ? "" : ",";
                ids += res.getInt("service_id");
                isFirst = false;
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return ServiceDao.getServices(ids, 0, 999999, Global.NONE, -1);
    }

    public static void clearShopCart(String login) {
        try {
            int custId = CustomerDao.getCustomerId(login);
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "DELETE FROM service_order WHERE \"order_id\" = (SELECT id FROM \"order\" WHERE customer_id = ?);"
            );
            statement.setInt(1, custId);
            statement.executeQuery();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public static void deleteProductFromCart(String login, int productId) {
        try {
            int custId = CustomerDao.getCustomerId(login);
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "DELETE FROM service_order WHERE service_id = ? AND \"order_id\" = (SELECT id FROM \"order\" WHERE customer_id = ?);"
            );
            statement.setInt(1, productId);
            statement.setInt(2, custId);
            statement.executeQuery();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public static void addToCart(String login, int productId) {
        int count = 0;
        int orderId = 0;
        try {
            System.out.println("Start");
            int custId = CustomerDao.getCustomerId(login);
            PreparedStatement searchStatement = DBService.getConnection().prepareStatement(
                    "SELECT COUNT(*) FROM \"order\"" +
                            " WHERE customer_id = ?;"
            );
            searchStatement.setInt(1, custId);
            ResultSet searchRes = searchStatement.executeQuery();
            if (searchRes.next()) {
                count = searchRes.getInt("count");
            }
            System.out.println("Search end");
            if (count == 0) {
                PreparedStatement createCartStatement = DBService.getConnection().prepareStatement(
                        "INSERT INTO \"order\" (customer_id, service_id)" +
                                " VALUES (?, ?);"
                );
                createCartStatement.setInt(1, custId);
                createCartStatement.setInt(2, productId);
                createCartStatement.executeQuery();
            }
            System.out.println("Create end");
            PreparedStatement getIdCartOrderStatement = DBService.getConnection().prepareStatement(
                    "SELECT id FROM \"order\"" +
                            " WHERE customer_id = ?"
            );
            getIdCartOrderStatement.setInt(1, custId);
            ResultSet cartOrderRes = getIdCartOrderStatement.executeQuery();
            if (cartOrderRes.next()) {
                orderId = cartOrderRes.getInt("id");
            }
            System.out.println("Get end");
            PreparedStatement addProductToOrder = DBService.getConnection().prepareStatement(
                    "INSERT INTO service_order (service_id, \"order_id\")" +
                            " VALUES (?, ?);"
            );
            addProductToOrder.setInt(1, productId);
            addProductToOrder.setInt(2, orderId);
            addProductToOrder.executeQuery();
            System.out.println("End");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
