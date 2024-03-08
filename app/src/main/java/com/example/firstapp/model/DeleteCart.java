package com.example.firstapp.model;

public class DeleteCart {

    private int id,productId,colorDetailId,sizeDetailId,totalItem,userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
