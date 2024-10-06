package bo;

import db.ItemDB;
import db.UserDB;

import java.util.ArrayList;
import java.util.List;

public class UserHandler {

    public static boolean Login(String username, String password, Role role) {
        User user = UserDB.searchByUsername(username);

        if (user == null){
            return false;
        }

        System.out.println(user);
        return user.getPassword().equals(password) && user.getRole().equals(role);
    }

    public static ArrayList<Item> getMyItems(String username) {
        User user = UserDB.searchByUsername(username);

        if (user == null){
            return new ArrayList<>();
        }

        ArrayList<Item> myItems = ItemDB.searchItemByUsername(user.getId());

        return myItems;
    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> items = ItemDB.listAllItems();

        return items;
    }

    public static boolean addItem(int userId, int itemId) {
        User user = UserDB.searchById(userId);
        Item item = ItemDB.searchById(itemId);

        // Kontrollera om användaren eller varan inte finns
        if (user == null || item == null) {
            return false;
        }

        // Kontrollera om varan finns i lager
        if (item.getBalance() < 1){
            return false;
        }

        // Kontrollera om användaren redan har varan
        if (user.getShoppingBag().contains(item)){
            return false;
        }

        // Om databasen lyckades lägga till varan i relationstabellen
        if (UserDB.addItem(userId, itemId)){
            user.addItem(item);
            item.setBalance(item.getBalance() - 1);
            ItemDB.updateBalance(itemId, item.getBalance());
            return true;
        }

        return false;
    }

    public static boolean removeItem(int userId, int itemId) {
        User user = UserDB.searchById(userId);
        Item item = ItemDB.searchById(itemId);

        // Kontrollera om användaren eller varan inte finns
        if (user == null || item == null) {
            return false;
        }

        if (UserDB.removeItem(userId, itemId)){
            user.removeItem(item);
            item.setBalance(item.getBalance() + 1);
            ItemDB.updateBalance(itemId, item.getBalance());
            return true;
        }

        return false;
    }

    public static boolean addNewItem(String name, String description, int price, int quantity, Category category) {
        Item item = new Item(Integer.MAX_VALUE, name, description, price, quantity, category);
        List<Item> items = getAllItems();

        if (items.contains(item)){
            return false;
        }

        if (ItemDB.addNewItem(name, description, price, quantity, category)){
            return true;
        }

        return false;
    }

    public static boolean editItem(int id, String name, String description, int price, int quantity, Category category) {
        for (Item item : getAllItems()) {
            if (item.getId() == id) {
                ItemDB.editItem(new Item(id, name, description, price, quantity, category));
                return true;
            }
        }

        return false;
    }

    public static User getUserByUsername(String username) {
        return UserDB.searchByUsername(username);
    }

    public static Item getItemById(int itemId) {
        for (Item item : getAllItems()) {
            if (item.getId() == itemId) {
                return item;
            }
        }

        return null;
    }

    public static List<User> getAllCustomersWithCarts() {
        List <User> customers = UserDB.getAllCustomers();
        List<User> customersWithCharts = new ArrayList<>();

        for (User user: customers){
            if (!user.getShoppingBag().isEmpty()){
                customersWithCharts.add(user);
            }
        }

        return customersWithCharts;
    }

    public static boolean packOrder(int userId) {
        User user = UserDB.searchById(userId);

        if (user == null) {
            return false;
        }

        if (UserDB.packOrder(userId)){
            user.fulfillOrder();
            return true;
        }

        return false;
    }
}
