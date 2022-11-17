package com.example.applicationintegrationtest.service;

import com.example.applicationintegrationtest.dao.StockDao;

public class StockServiceImp implements StockService {
    private StockDao stockDao;

    public StockServiceImp(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public String createStock(String name) {
        boolean result = stockDao.createStock(name);
        if (result) {
            // Send an email verify ...
            // Show a success message to end user ...
            return "SUCCESS";
        }
        // Send an error message to end user ...
        return "FAILED";
    }
}
