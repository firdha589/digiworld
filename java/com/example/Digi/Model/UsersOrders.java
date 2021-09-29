package com.example.Digi.Model;

public class UsersOrders {

    private String buyers_name,buyers_shipping_address,buyers_phone_number,buyers_city,discount,quantity,time,date,totalprice,pname,pid,order_state,image;

    public UsersOrders() {

    }

    public UsersOrders(String buyers_name, String buyers_shipping_address, String buyers_phone_number, String buyers_city, String discount, String quantity, String time, String date, String totalprice, String pname, String pid, String order_state,String image) {
        this.buyers_name = buyers_name;
        this.buyers_shipping_address = buyers_shipping_address;
        this.buyers_phone_number = buyers_phone_number;
        this.buyers_city = buyers_city;
        this.discount = discount;
        this.quantity = quantity;
        this.time = time;
        this.date = date;
        this.totalprice = totalprice;
        this.pname = pname;
        this.pid = pid;
        this.order_state = order_state;
        this.image=image;
    }

    public String getBuyers_name() {
        return buyers_name;
    }

    public void setBuyers_name(String buyers_name) {
        this.buyers_name = buyers_name;
    }

    public String getBuyers_shipping_address() {
        return buyers_shipping_address;
    }

    public void setBuyers_shipping_address(String buyers_shipping_address) {
        this.buyers_shipping_address = buyers_shipping_address;
    }

    public String getBuyers_phone_number() {
        return buyers_phone_number;
    }

    public void setBuyers_phone_number(String buyers_phone_number) {
        this.buyers_phone_number = buyers_phone_number;
    }

    public String getBuyers_city() {
        return buyers_city;
    }

    public void setBuyers_city(String buyers_city) {
        this.buyers_city = buyers_city;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
