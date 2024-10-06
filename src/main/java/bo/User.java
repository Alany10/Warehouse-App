package bo;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class User {

    private final int id;
    private String username;
    private String password;
    private final ArrayList<Item> shoppingBag;
    private Role role;

    protected User(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.shoppingBag = new ArrayList<>();
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public ArrayList<Item> getShoppingBag() {
        return new ArrayList<>(shoppingBag);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean addItem(Item item) {
        shoppingBag.add(item);
        return true;
    }

    public boolean removeItem(Item item) {
        if (shoppingBag.contains(item)) {
            shoppingBag.remove(item);
            return true;
        }
        return false;
    }

    public boolean fulfillOrder() {
        shoppingBag.clear();
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(shoppingBag, user.shoppingBag) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role);
    }

    @Override
    public String toString() {
        return "<b>" + username + "</b>" +  ", " + role.toString().toLowerCase();
    }
}
