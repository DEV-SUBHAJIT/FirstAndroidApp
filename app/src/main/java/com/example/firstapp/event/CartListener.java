package com.example.firstapp.event;

import com.example.firstapp.model.Carts;

public interface CartListener {

    void onItemClick(boolean isIncrement, Carts carts);
}
