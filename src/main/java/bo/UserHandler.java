package bo;

import db.ItemDB;
import db.UserDB;

import java.util.ArrayList;

public class UserHandler {

    public static boolean Login(String username, String password) {
        User user = UserDB.searchByUsername(username);

        if (user == null){
            return false;
        }

        return user.getPassword().equals(password);
    }

    public static ArrayList<Item> getMyItems(String username) {
        ArrayList<Item> myItems = ItemDB.searchItemByUsername(username);

        return myItems;
    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> items = ItemDB.listAllItems();

        return items;
    }

    public static boolean addItem(int userId, int itemId) {
        if (UserDB.addItem(userId, itemId)){
            User user = UserDB.searchById(userId);
            Item item = ItemDB.searchById(itemId);
            user.addItem(item);
            return true;
        }

        return false;
    }

    public static boolean removeItem(int userId, int itemId) {
        if (UserDB.removeItem(userId, itemId)){
            User user = UserDB.searchById(userId);
            Item item = ItemDB.searchById(itemId);
            user.removeItem(item);
            return true;
        }

        return false;
    }

    public static User getUserByUsername(String username) {
        return UserDB.searchByUsername(username);
    }
}
