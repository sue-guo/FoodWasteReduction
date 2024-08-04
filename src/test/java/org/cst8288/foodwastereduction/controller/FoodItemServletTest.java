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
import java.util.List;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.controller.FoodItemServlet;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class FoodItemServletTest {

    @InjectMocks
    private FoodItemServlet foodItemServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private FoodItemBusiness foodItemBusiness;

    @Mock
    private FoodItemDTO foodItem;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        foodItemBusiness = mock(FoodItemBusiness.class);
        foodItemServlet = new FoodItemServlet();  // Use constructor injection
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        foodItem = mock(FoodItemDTO.class);
    }

    @After
    public void tearDown() {
        // Reset the mocks after each test to ensure they are not affected by previous tests
        reset(foodItemBusiness, request, response, dispatcher, foodItem);
    }
    
    
    @Test
    public void testDoGet() throws ServletException, IOException {
       
        int userId = 1;
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        when(request.getRequestDispatcher("views/foodItem.jsp")).thenReturn(dispatcher);

        foodItemServlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }



    @Test
    public void testDoPost() throws ServletException, IOException {
       
        int userId = 1;
      
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        when(request.getParameter("name")).thenReturn("Apple");
        when(request.getParameter("description")).thenReturn("Fresh Apple");
        when(request.getParameter("category")).thenReturn(CategoryEnum.Fruit.name());
        when(request.getParameter("brand")).thenReturn("Brand A");
        when(request.getParameter("unit")).thenReturn("Kg");

        foodItemServlet.doPost(request, response);
        
        verify(response).sendRedirect(request.getContextPath() + "/foodItem?userId=" + userId);
    }

 
}