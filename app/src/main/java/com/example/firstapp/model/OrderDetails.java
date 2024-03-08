package com.example.firstapp.model;

public class OrderDetails {

    private int id,userId,productId,colorDetailId,sizeDetailId,quantity,totalPrice,orderHeaderId,hsnCodeId;

    private String productName,productImage, gstNo,gstRate;

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getGstRate() {
        return gstRate;
    }

    public void setGstRate(String gstRate) {
        this.gstRate = gstRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getColorDetailId() {
        return colorDetailId;
    }

    public void setColorDetailId(int colorDetailId) {
        this.colorDetailId = colorDetailId;
    }

    public int getSizeDetailId() {
        return sizeDetailId;
    }

    public void setSizeDetailId(int sizeDetailId) {
        this.sizeDetailId = sizeDetailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(int orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }



    public int getHsnCodeId() {
        return hsnCodeId;
    }

    public void setHsnCodeId(int hsnCodeId) {
        this.hsnCodeId = hsnCodeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
