package db;

import bo.Item;
import bo.Role;
import bo.User;

import java.sql.*;

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

}
