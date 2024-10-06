package bo;

import java.util.Objects;

public class Item {
    private final int id;
    private String name;
    private String description;
    private int price;
    private static int balance;
    private Category category;

    protected Item (int id, String name, String description, int price, int balance, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        Item.balance = balance;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getBalance() {
        return balance;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setBalance(int balance) {
        if (balance < 0){
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        Item.balance = balance;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return price == item.price && Objects.equals(name, item.name) && Objects.equals(description, item.description) && category == item.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, category);
    }

    @Override
    public String toString() {
        return "<b>" + name + "</b>" + ", " + description + ", " + category.toString().toLowerCase() + ", price: " + price;
    }
}
