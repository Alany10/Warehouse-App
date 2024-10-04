package bo;

import db.ItemDB;
import db.UserDB;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBagHandler {

    public static List<User> getAllCustomersWithCarts() {
        return UserDB.getAllCustomersWithCarts();
    }
}
