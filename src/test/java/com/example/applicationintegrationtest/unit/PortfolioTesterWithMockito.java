package com.example.applicationintegrationtest.unit;

import com.example.applicationintegrationtest.controller.Portfolio;
import com.example.applicationintegrationtest.model.Stock;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class PortfolioTesterWithMockito {

    @Mock
    Portfolio portfolio;

    @BeforeAll
    public static void setUp() {
        System.out.println("Start testing class Portfolio!!!");
    }

    @BeforeEach
    public void before_each() {
        System.out.println("::::::: Start a new test :::::::");

        List<Stock> stocks = new ArrayList<>();
        Stock stock1 = Mockito.mock(Stock.class);
        Stock stock2 = Mockito.mock(Stock.class);
        Stock stock3 = Mockito.mock(Stock.class);

        stocks.add(stock1);
        stocks.add(stock2);
        stocks.add(stock3);

        portfolio = Mockito.mock(Portfolio.class);
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
        when(portfolio.getMarketValue()).thenReturn(5000.0);
        Assertions.assertEquals(5000.0, portfolio.getMarketValue());
        System.out.println("test_market_value_normal");
    }

    @Test
    public void test_market_value_failed() {
        when(portfolio.getMarketValue()).thenReturn(5000.0);
        Assertions.assertEquals(4000.0, portfolio.getMarketValue());
        System.out.println("test_market_value_failed");
    }

    @Test
    public void test_get_stock_byId_success() {
        Stock stock = Mockito.mock(Stock.class);
        when(portfolio.getStockById(stock.getStockId())).thenReturn(stock);
        Assertions.assertSame(stock, portfolio.getStockById(stock.getStockId()));
    }

    @Test
    public void test_get_stock_byId_null() {
        when(portfolio.getStockById("2")).thenReturn(null);
        Assertions.assertNull(portfolio.getStockById("2"));
    }
}