package com.gpcoder.mockito;

import com.example.applicationintegrationtest.dao.StockDao;
import com.example.applicationintegrationtest.service.StockService;
import com.example.applicationintegrationtest.service.StockServiceImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

// @RunWith attaches a runner with the test class to initialize the mock data
@RunWith(MockitoJUnitRunner.class)
public class StockServiceImpTest {

    // Create a mock object
    @Mock
    private StockDao stockDao;

    private StockService stockService;

    @Before
    public void setUp() {
        stockService = new StockServiceImp(stockDao);
    }

    @Test
    public void createStock_WhenNameExisted_ReturnFailed() {
        // Define return value for method createUser()
        Mockito.when(stockDao.createStock("existedStock")).thenReturn(false);

        // Use mock in test
        Assert.assertEquals("FAILED", stockService.createStock("existedStock"));
    }

    @Test
    public void createStock_WhenNameExisted_ReturnSuccess() {
        // Define return value for method createUser()
        Mockito.when(stockDao.createStock("not_existedStock")).thenReturn(true);

        // Use mock in test
        Assert.assertEquals("SUCCESS", stockService.createStock("not_existedStock"));
    }
}