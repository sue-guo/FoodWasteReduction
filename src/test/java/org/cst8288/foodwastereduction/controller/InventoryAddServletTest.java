package org.cst8288.foodwastereduction.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;

@RunWith(MockitoJUnitRunner.class)
public class InventoryAddServletTest {

    @InjectMocks
    private InventoryAddServlet inventoryAddServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private InventoryBusiness inventoryBusiness;

    @Mock
    private FoodItemBusiness foodItemBusiness;

    @Mock
    private LMSLogger logger;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        inventoryBusiness = mock(InventoryBusiness.class);
        foodItemBusiness = mock(FoodItemBusiness.class);
        inventoryAddServlet = new InventoryAddServlet();  // Use constructor injection
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        logger = mock(LMSLogger.class);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        int userId = 1;
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        when(request.getRequestDispatcher("views/addInventory.jsp")).thenReturn(dispatcher);

        inventoryAddServlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        int userId = 1;
        int foodItemId = 1;
        String batchNumber = "batch123";
        int quantity = 10;
        double regularPrice = 5.0;
        double discountRate = 0.1;
        Date expirationDate = Date.valueOf("2024-12-31");
        Date receiveDate = Date.valueOf("2024-07-01");

        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        when(request.getParameter("foodItemId")).thenReturn(String.valueOf(foodItemId));
        when(request.getParameter("batchNumber")).thenReturn(batchNumber);
        when(request.getParameter("quantity")).thenReturn(String.valueOf(quantity));
        when(request.getParameter("regularPrice")).thenReturn(String.valueOf(regularPrice));
        when(request.getParameter("discountRate")).thenReturn(String.valueOf(discountRate));
        when(request.getParameter("expirationDate")).thenReturn(expirationDate.toString());
        when(request.getParameter("receiveDate")).thenReturn(receiveDate.toString());

        inventoryAddServlet.doPost(request, response);
        verify(response).sendRedirect(request.getContextPath() + "/inventory?userId=" + userId);
    }
}