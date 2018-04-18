package dao;

import common.Category;
import common.DBService;
import common.Global;
import common.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ServiceDao {
    public static Service getService(int id) {
        String name = "";
        int price = 0;
        String descr = "";
        int categoryId = 0;
        try {
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "SELECT name, price, description, category_id FROM service " +
                            " WHERE id = ?;"
            );
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                name = res.getString("name");
                price = res.getInt("price");
                descr = res.getString("description");
                categoryId = res.getInt("category_id");
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return new Service(id, name, price, descr, categoryId);
    }

    public static ArrayList<Service> getServices(String ids, int minPriceFilter, int maxPriceFilter, String serviceName, int catId) {
        ArrayList<Service> services = new ArrayList<>();
        int id;
        String name;
        int price;
        String descr;
        int categoryId;
        String query;
        try {
            query = "SELECT id, name, price, description, category_id FROM service";

            query += " WHERE price BETWEEN " + minPriceFilter + " AND " + maxPriceFilter;
            if (!serviceName.equals(Global.NONE)) {
                query += " AND " + "name LIKE '%" + serviceName + "%'";
            }
            if (!ids.equals(Global.NONE)) {
                query += " AND " + "id IN (" + ids + ")";
            }
            if (catId != -1) {
                query += " AND " + "category_id = " + catId;
            }

            ResultSet res = DBService.getConnection().createStatement().executeQuery(query);
            while (res.next()) {
                id = res.getInt("id");
                name = res.getString("name");
                price = res.getInt("price");
                descr = res.getString("description");
                categoryId = res.getInt("category_id");
                services.add(new Service(id, name, price, descr, categoryId));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return services;
    }

    public static String getCategoryName(int id) {
        String name = "";
        try {
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "SELECT name FROM service_category " +
                            " WHERE id = ?;"
            );
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                name = res.getString("name");
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return name;
    }

    public static ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        int id;
        String name;
        try {
            PreparedStatement statement = DBService.getConnection().prepareStatement(
                    "SELECT id, name FROM service_category"

            );
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                id = res.getInt("id");
                name = res.getString("name");
                categories.add(new Category(id, name));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return categories;
    }
}
