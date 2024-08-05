
package org.cst8288.foodwastereduction.retailer;

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
import java.util.Arrays;
import java.util.List;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.controller.FoodItemServlet;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.junit.After;

/**
 * Unit tests for the FoodItemServlet class.
 * @author  WANG JIAYUN
 */
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
    
    @Mock
    private List<FoodItemDTO> foodItemList;

    /**
     * Sets up the test environment before each test.
     * Initializes mocks and injects them into the servlet.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        foodItemBusiness = mock(FoodItemBusiness.class);
        foodItemServlet = new FoodItemServlet(foodItemBusiness);  // Use constructor injection
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
       
        // Create a list with specific FoodItemDTO objects
        FoodItemDTO item1 = new FoodItemDTO();
        FoodItemDTO item2 = new FoodItemDTO();
        foodItemList = Arrays.asList(item1, item2);
        
       // Mock the request parameter "userId" to return the string value of userId
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        // Mock the business layer method to return the predefined list of food items for the given userId
        when(foodItemBusiness.getFoodItemsByRetailerID(userId)).thenReturn(foodItemList);
        // Mock the request dispatcher to forward the request to the "views/foodItem.jsp" page
        when(request.getRequestDispatcher("views/foodItem.jsp")).thenReturn(dispatcher);
        // Call the doGet method of the servlet with the mocked request and response objects
        foodItemServlet.doGet(request, response);
        
       // Verify that the request is set with the correct attribute and forwarded to the correct JSP page
        verify(request).setAttribute("foodItems", foodItemList);
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

        // Verifying the interactions with FoodItemBusiness
         verify(foodItemBusiness).addFoodItem(any(FoodItemDTO.class));
       
        // Verifying the interactions and behavior
        verify(response).sendRedirect(request.getContextPath() + "/foodItem?userId=" + userId);
    }
}