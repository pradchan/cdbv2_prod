package converge.models;

import java.util.List;

public class Cart {
    private List<CartItem> ctItem;
    private double cartTotal = 0;


    public List<CartItem> getCartItems() {
        return this.ctItem;
    }

    public double getCartAmount() {
        double totalAmount = 0;
        List<CartItem> tempCartList = this.getCartItems();
        for (int i = 0; i < tempCartList.size(); i++) {
            totalAmount += tempCartList.get(i).getCartItemTotalPrice();
        }

        return totalAmount;

    }


}
