package converge.models;

import java.util.Map;

public class Product {

    private String pid;
    private String category;
    private String title;
    private String details;
    private String price;
    private String picture;

    public Product() {

    }

    public Product(String pid, String category, String title, String details, String price, String picture) {

        this.pid = pid;
        this.category = category;
        this.title = title;
        this.details = details;
        this.price = price;
        this.picture = picture;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Product [pid=" + pid + ", category=" + category + ", title=" + title + ", details=" + details +
               ", price=" + price + ", picture=" + picture + "]";
    }

}
