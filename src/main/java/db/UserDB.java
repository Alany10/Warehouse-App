package db;

import bo.Item;
import bo.Role;
import bo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDB extends bo.User{

    private UserDB(int id, String username, String password, Role role) {
        super(id, username, password, role);
    }

    public static User searchByUsername(String username) {
        User user = null; // För att hålla användarobjektet

        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Anslutning till databasen
            connection = DBManager.getConnection();

            // Förbered SQL-frågan
            String sql = "SELECT * FROM user WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username); // Sätt användarnamnet i frågan

            // Utför frågan
            resultSet = preparedStatement.executeQuery();

            // Hämta resultatet om det finns
            if (resultSet.next()) {

                user = new UserDB(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role").toUpperCase()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user; // Returnera användaren eller null om inte hittad
    }

    public static User searchById(int id) {
        User user = null; // För att hålla användarobjektet

        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Anslutning till databasen
            connection = DBManager.getConnection();

            // Förbered SQL-frågan
            String sql = "SELECT * FROM user WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id); // Sätt användarnamnet i frågan

            // Utför frågan
            resultSet = preparedStatement.executeQuery();

            // Hämta resultatet om det finns
            if (resultSet.next()) {
                user = new UserDB(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role").toUpperCase()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user; // Returnera användaren eller null om inte hittad
    }

    public static boolean addItem(int userId, int itemId) {
        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatementCheck = null;
        PreparedStatement preparedStatementInsert = null;
        ResultSet resultSet = null;

        try {
            // Anslutning till databasen
            connection = DBManager.getConnection();

            // Kontrollera om relationen redan finns
            String sqlCheck = "SELECT COUNT(*) FROM user_item WHERE user_id = ? AND item_id = ?";
            preparedStatementCheck = connection.prepareStatement(sqlCheck);
            preparedStatementCheck.setInt(1, userId);
            preparedStatementCheck.setInt(2, itemId);
            resultSet = preparedStatementCheck.executeQuery();

            // Om relationen redan finns, returnera false
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return false; // Relation finns
            }

            // Om relationen inte finns, lägg till den
            String sqlInsert = "INSERT INTO user_item (user_id, item_id) VALUES (?, ?)";
            preparedStatementInsert = connection.prepareStatement(sqlInsert);
            preparedStatementInsert.setInt(1, userId);
            preparedStatementInsert.setInt(2, itemId);
            preparedStatementInsert.executeUpdate();

            return true; // Relation tillagd framgångsrikt
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Vid fel, returnera false
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatementCheck != null) preparedStatementCheck.close();
                if (preparedStatementInsert != null) preparedStatementInsert.close();
                // connection.close(); // Gör inte detta om du använder en singleton
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean removeItem(int userId, int itemId) {
        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatementCheck = null;
        PreparedStatement preparedStatementDelete = null;
        ResultSet resultSet = null;

        try {
            // Anslutning till databasen
            connection = DBManager.getConnection();

            // Kontrollera om relationen finns
            String sqlCheck = "SELECT COUNT(*) FROM user_item WHERE user_id = ? AND item_id = ?";
            preparedStatementCheck = connection.prepareStatement(sqlCheck);
            preparedStatementCheck.setInt(1, userId);
            preparedStatementCheck.setInt(2, itemId);
            resultSet = preparedStatementCheck.executeQuery();

            // Om relationen inte finns, returnera false
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                return false; // Relation finns inte
            }

            // Om relationen finns, ta bort den
            String sqlDelete = "DELETE FROM user_item WHERE user_id = ? AND item_id = ?";
            preparedStatementDelete = connection.prepareStatement(sqlDelete);
            preparedStatementDelete.setInt(1, userId);
            preparedStatementDelete.setInt(2, itemId);
            preparedStatementDelete.executeUpdate();

            return true; // Relation borttagen framgångsrikt
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Vid fel, returnera false
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatementCheck != null) preparedStatementCheck.close();
                if (preparedStatementDelete != null) preparedStatementDelete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<User> getAllCustomersWithCarts() {
        List<User> customersWithCarts = new ArrayList<>(); // Lista för att lagra kunder och deras korgar
        HashMap<Integer, User> customerMap = new HashMap<>(); // HashMap för att hantera kunder och undvika dubbletter
        Connection connection = null; // Håll en referens till anslutningen
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Anslutning till databasen via singleton
            connection = DBManager.getConnection();

            // Steg 1: Hämta alla kunder
            String sqlCustomers = "SELECT id, username, role FROM user WHERE role = ?";
            preparedStatement = connection.prepareStatement(sqlCustomers);
            preparedStatement.setString(1, Role.CUSTOMER.name()); // Sätta rollen till CUSTOMER
            resultSet = preparedStatement.executeQuery();

            // Skapa kunder och lagra dem i HashMap
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                Role role = Role.valueOf(resultSet.getString("role")); // Hämta rollen
                User user = new UserDB(userId, username, null, role);
                customerMap.put(userId, user);
                customersWithCarts.add(user); // Lägg till kund i listan
            }

            // Steg 2: Hämta artiklar för varje kund
            for (User customer : customersWithCarts) {
                List<Item> items = getItemsForUser(customer.getId(), connection);
                for (Item item : items) {
                    customer.addItem(item); // Lägg till artiklar i kundens korg
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Stäng resurser för ResultSet och PreparedStatement
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return customersWithCarts; // Returnera lista över kunder med korgar
    }

    // Hjälpmetod för att hämta artiklar för en specifik kund
    private static List<Item> getItemsForUser(int userId, Connection connection) {
        List<Item> items = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Hämta artiklar kopplade till kunden
            String sqlItems = "SELECT i.id, i.name FROM item i " +
                    "JOIN user_item ui ON i.id = ui.item_id WHERE ui.user_id = ?";
            preparedStatement = connection.prepareStatement(sqlItems);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            // Skapa Item-objekt och lägg till i listan
            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String itemName = resultSet.getString("name");
                Item item = new ItemDB(itemId, itemName,null,0,0,null); // Anta att Item har en konstruktor som tar id och namn
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Stäng resurser för ResultSet och PreparedStatement
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return items; // Returnera listan med artiklar
    }

}
