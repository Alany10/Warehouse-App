package db;

import bo.Item;
import bo.User;

import java.sql.*;
import java.util.ArrayList;

public class ItemDB extends bo.Item{

    private ItemDB(int id, String name, String description, int price, int balance) {
        super(id, name, description, price, balance);
    }

    public static ArrayList<Item> listAllItems() {
        ArrayList<Item> items = new ArrayList<>();

        // Deklarera resurser för stängning
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Anslutning till databasen
            connection = DBManager.getConnection();

            // Skapa en Statement
            statement = connection.createStatement();

            // Utför frågan
            resultSet = statement.executeQuery("SELECT * FROM Item");

            // Bearbeta resultatet
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                int balance = resultSet.getInt("balance");
                items.add(new ItemDB(id, name, description, price, balance));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return items;
    }


    public static ArrayList<Item> searchItemByUsername(String username, int userId) {
        ArrayList<Item> items = new ArrayList<>();

        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatementUserItems = null;
        ResultSet resultsetUserItems = null;
        PreparedStatement statementItem = null;
        ResultSet resultsetItem = null;

        try {
            // Anslutning till databasen
            connection = DBManager.getConnection();

            // Hämta alla item_id från user_item-tabellen för den specifika användaren
            String sqlUserItems = "SELECT item_id FROM user_item WHERE user_id = ?";
            preparedStatementUserItems = connection.prepareStatement(sqlUserItems);
            preparedStatementUserItems.setInt(1, userId);
            resultsetUserItems = preparedStatementUserItems.executeQuery();

            // Gå igenom varje item_id som hittas för userId
            while (resultsetUserItems.next()) {
                int itemId = resultsetUserItems.getInt("item_id");

                // Hämta motsvarande Item från Item-tabellen
                String sqlItem = "SELECT * FROM Item WHERE id = ?";
                statementItem = connection.prepareStatement(sqlItem);
                statementItem.setInt(1, itemId);
                resultsetItem = statementItem.executeQuery();

                // Om item hittas, skapa ett Item-objekt och lägg till det i listan
                if (resultsetItem.next()) {
                    String itemName = resultsetItem.getString("name");
                    String description = resultsetItem.getString("description");
                    int price = resultsetItem.getInt("price");
                    int balance = resultsetItem.getInt("balance");

                    // Skapa ett nytt Item-objekt och lägg till det i listan
                    Item item = new ItemDB(itemId, itemName, description, price, balance);
                    items.add(item);
                }

                // Stäng resurser för item query
                resultsetItem.close();
                statementItem.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Stäng resurser för user_item query
            try {
                if (resultsetUserItems != null) resultsetUserItems.close();
                if (preparedStatementUserItems != null) preparedStatementUserItems.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Returnera listan med items
        return items;
    }

    public static Item searchById(int id) {
        Item item = null; // För att hålla användarobjektet

        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Anslutning till databasen
            connection = DBManager.getConnection();

            // Förbered SQL-frågan
            String sql = "SELECT * FROM item WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id); // Sätt användarnamnet i frågan

            // Utför frågan
            resultSet = preparedStatement.executeQuery();

            // Hämta resultatet om det finns
            if (resultSet.next()) {
                item = new ItemDB(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("price"),
                        resultSet.getInt("balance"));
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

        return item; // Returnera användaren eller null om inte hittad
    }

    public static boolean updateBalance(int itemId, int balance) {
        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Anslutning till databasen
            connection = DBManager.getConnection();

            // Förbered SQL-frågan för att uppdatera balance
            String sql = "UPDATE item SET balance = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, balance); // Sätt den nya balansen
            preparedStatement.setInt(2, itemId); // Sätt itemId för att identifiera rätt rad

            // Utför uppdateringen och kolla om någon rad påverkas
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returnera true om uppdateringen lyckades
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returnera false om ett fel inträffar
        } finally {
            // Stäng resurser
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
