package org.cst8288.foodwastereduction.controller;

import static org.mockito.Mockito.*;
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
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.junit.After;

/**
 * Unit tests for the FoodItemServlet class.
 * @author  WANG JIAYUN
 */
import org.junit.Ignore;
@Ignore("This test is not ready yet")
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

    /**
     * Sets up the test environment before each test.
     * Initializes mocks and injects them into the servlet.
     */
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

    /**
     * Cleans up the test environment after each test.
     * Resets the mocks to ensure they are not affected by previous tests.
     */
    @After
    public void tearDown() {
        reset(foodItemBusiness, request, response, dispatcher, foodItem);
    }

    /**
     * Tests the doGet method of FoodItemServlet.
     * Verifies that the request is forwarded to the correct JSP page.
     * 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testDoGet() throws ServletException, IOException {
        int userId = 1;
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        when(request.getRequestDispatcher("views/foodItem.jsp")).thenReturn(dispatcher);

        foodItemServlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the doPost method of FoodItemServlet.
     * Verifies that the food item is added and the user is redirected correctly.
     * 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testDoPost() throws ServletException, IOException {
        int userId = 1;

        // Mocking request parameters
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        when(request.getParameter("name")).thenReturn("Apple");
        when(request.getParameter("description")).thenReturn("Fresh Apple");
        when(request.getParameter("category")).thenReturn(CategoryEnum.Fruit.name());
        when(request.getParameter("brand")).thenReturn("Brand A");
        when(request.getParameter("unit")).thenReturn("Kg");

        // Calling the doPost method
        foodItemServlet.doPost(request, response);

        // Verifying the interactions and behavior
        verify(response).sendRedirect(request.getContextPath() + "/foodItem?userId=" + userId);
    }
}