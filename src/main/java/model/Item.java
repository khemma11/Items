package model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Item implements Comparable<Item>, Serializable {
    private long id;
    private String title;
    private String text;
    private double price;
    private User user;
    private Category category;
    private Date createdDate;

    public Item(String title, String text, double price, User user, Category category,Date createdDate) {
        this.title = title;
        this.text = text;
        this.price = price;
        this.user = user;
        this.category = category;
        this.createdDate = createdDate;
    }

    @Override
    public int compareTo(Item o) {
        return title.compareTo(o.getTitle());
    }
}
