package converge.models;

import java.util.Date;
import java.util.List;

public class Order {
    private List<CartItem> cartList;

    private Date dateOrdered;
    private double totalOrderCost;

    public List<CartItem> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartItem> cartList) {
        this.cartList = cartList;
    }

    public Date getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public double getTotalOrderCost() {
        double totalAmount = 0;
        List<CartItem> tempCartList = this.getCartList();
        for (int i = 0; i < tempCartList.size(); i++) {
            totalAmount += Double.parseDouble(tempCartList.get(i).getPrice()) * tempCartList.get(i).getQuantity();
        }

        return totalAmount;
    }

    private String orderedBy;
    private String address;
    private String email;
    private String city;
    private String status = "Placed";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    private String phone;
    private String pincode;

}
