package converge.models;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CartItem extends Product {

    public CartItem() {

    }


    public CartItem(String pid, String category, String title, String details, String price, String picture,
                    int quantity) {
        super(pid, category, title, details, price, picture);

        this.quantity = quantity;
    }

    private long quantity = 1;

    private double cartItemTotalPrice = 0;

    public double getCartItemTotalPrice() {
        return this.getQuantity() * Double.parseDouble(this.getPrice());
    }


    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }


    public void removeItemFromList(String pid) {

    }

    @Override
    public String toString() {
        return "{\"pid\":\"" + this.getPid() + "\",\"category\":\"" + this.getCategory() + "\",\"title\":\"" +
               this.getTitle() + "\",\"details\":\"" + this.getDetails() + "\",\"price\":\"" + this.getPrice() +
               "\",\"picture\":\"" + this.getPicture() + "\",\"quantity\":\"" + String.valueOf(this.getQuantity()) +
               "\"}";
    }


}
