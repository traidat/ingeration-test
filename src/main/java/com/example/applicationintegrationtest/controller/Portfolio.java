package com.example.applicationintegrationtest.controller;

import com.example.applicationintegrationtest.model.Stock;
import com.example.applicationintegrationtest.service.StockService;

import java.util.List;

public class Portfolio {
    private List<Stock> stocks;

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public double getMarketValue() {
        double marketValue = 0.0;
        System.out.println(stocks);

        if (stocks.isEmpty()) {
        } else
            for (Stock stock : stocks) {
                marketValue = marketValue + stock.getPrice() * stock.getQuantity();
            }
        return marketValue;
    }

    public double getMarketValueWrong() {
        double marketValue = 0.0;
        System.out.println(stocks);

        if (stocks.isEmpty()) {
        } else
            for (Stock stock : stocks) {
                marketValue = marketValue + stock.getPrice() * stock.getQuantity();
            }
        return marketValue + 100;
    }

    public Stock getStockById(String id) {
        Stock res = null;
        if (!stocks.isEmpty()) {
            for (Stock stock : stocks) {
                if (stock != null)
                    if (stock.getStockId().equals(id)) {
                        res = stock;
                        break;
                    }
            }
        }
        return res;
    }
}