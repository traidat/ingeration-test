package com.example.applicationintegrationtest.unit;

import com.example.applicationintegrationtest.controller.Portfolio;
import com.example.applicationintegrationtest.model.Stock;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;


@ActiveProfiles("test")
public class PortfolioTesterWithoutMockito {

    @Autowired
    Portfolio portfolio;
    List<Stock> stocks;

    @BeforeAll
    public static void setUp() {
        System.out.println("Start testing class Portfolio!!!");
    }

    @BeforeEach
    public void before_each() {
        System.out.println();
        System.out.println("::::::: Start a new test :::::::");
        //Create a portfolio object which is to be tested

        stocks = new ArrayList<>();
        Stock googleStock = new Stock("1", "Google", 10, 30);
        Stock microsoftStock = new Stock("2", "Microsoft", 100, 40);
        stocks.add(googleStock);
        stocks.add(microsoftStock);
        portfolio = new Portfolio();
        portfolio.setStocks(stocks);
    }

    @AfterEach
    public void after_each() {
        System.out.println("::::::: End this test :::::::");
    }

    @AfterAll
    public static void after_all() {
        System.out.println("End testing!!!");
    }

    @Test
    public void test_market_value_normal() {
        double marketValue = portfolio.getMarketValue();
        Assertions.assertEquals(4300.0, marketValue);
        System.out.println("test_market_value_normal");
    }

//    @Test
//    public void test_market_value_failed() {
//        double marketValue = portfolio.getMarketValue();
//        Assertions.assertEquals(4200.0, marketValue);
//        System.out.println("test_market_value_failed");
//    }

    @Test
    public void test_get_stock_byId_success() {
        Stock stock = portfolio.getStockById("1");
        Assertions.assertEquals("Google", stock.getTicker());
        System.out.println("test_get_stock_byId_success");
    }

    @Test
    public void test_market_value_failed_with_wrong_code() {
        double marketValue = portfolio.getMarketValueWrong();
        Assertions.assertEquals(4300.0, marketValue);
        System.out.println("test_market_value_failed");
    }

    @Test
    public void test_get_stock_byId_null() {
        Stock stock = portfolio.getStockById("3");
        Assertions.assertNull(stock);
        System.out.println("test_get_stock_byId_null");
    }
}