package com.example.applicationintegrationtest.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Stock {
    private String stockId;
    private String name;
    private int quantity;

    private  int price;

    public Stock(String stockId, String name, int quantity, int price) {
        this.stockId = stockId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTicker() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

