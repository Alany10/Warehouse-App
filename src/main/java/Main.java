import bo.Item;
import bo.ShoppingBagHandler;
import bo.User;
import db.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Testa getAllCustomersWithCarts
        try {
            // Anropa metoden
            List<User> customersWithCarts = ShoppingBagHandler.getAllCustomersWithCarts();

            // Skriv ut resultaten
            if (customersWithCarts != null && !customersWithCarts.isEmpty()) {
                System.out.println("Customers with carts:");
                for (User customer : customersWithCarts) {
                    System.out.println("Username: " + customer.getUsername() + ", Role: " + customer.getRole());
                    System.out.println("Items in cart:");
                    for (Item item : customer.getShoppingBag()) {
                        System.out.println(" - " + item.getName());
                    }
                }
            } else {
                System.out.println("No customers with carts found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Stäng databasanslutning om det behövs
            try {
                Connection connection = DBManager.getConnection();
                if (connection != null && !connection.isClosed()) {
                    connection.close(); // Om du använder en singleton, kanske du inte vill stänga här.
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

