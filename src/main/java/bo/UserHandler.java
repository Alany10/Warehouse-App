package bo;

import db.ItemDB;
import db.UserDB;

import java.util.ArrayList;

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

        ArrayList<Item> myItems = ItemDB.searchItemByUsername(username, user.getId());

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

        if (item.getBalance() < 1){
            return false;
        }


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

    public static User getUserByUsername(String username) {
        return UserDB.searchByUsername(username);
    }
}
