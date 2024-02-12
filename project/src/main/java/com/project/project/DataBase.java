package com.project.project;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private final String url = "jdbc:postgresql://localhost:5432/project";
    private final String username = "postgres";
    private final String password = "root";

    public void addItem(Product product) {
        String sql = "INSERT INTO products (item_name, item_price, item_quantity) values (?,?,?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(sql);) {

            System.out.println("Подключено");

            preparedStatement.setString(1, product.getItemName());
            preparedStatement.setBigDecimal(2, new BigDecimal(product.getItemPrice()));
            preparedStatement.setInt(3, product.getItemQuantity());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка подключения");
            e.printStackTrace();
        }

    }


    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, item_name, item_price, item_quantity FROM products";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                String itemName = rs.getString("item_name");
                double itemPrice = rs.getBigDecimal("item_price").doubleValue();
                int itemQuantity = rs.getInt("item_quantity");

                Product product = new Product(itemName, itemPrice, itemQuantity);
                product.setId(rs.getInt("id"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении данных");
            e.printStackTrace();
        }
        return products;
    }


    public void removeProduct(String itemName) {
        String sql = "DELETE FROM products WHERE item_name = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                System.out.println("Удалено");
            } else {
                System.out.println("Не найдено строк для удаления");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка удаления");
            e.printStackTrace();
        }

    }

}
