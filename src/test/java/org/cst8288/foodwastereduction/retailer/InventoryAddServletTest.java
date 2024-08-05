
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
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.controller.InventoryAddServlet;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.junit.After;
/**
 * Unit tests for the InventoryAddServlet class.
 * @author  WANG JIAYUN
 */
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
    private FoodItemDTO foodItem;
    
    @Mock
    private List<FoodItemDTO> foodItemList;
    
    @Mock
    private InventoryDTO inventory;
    
    @Mock
    private List<InventoryDTO> inventoryList;
    
    @Mock
    private LMSLogger logger;

    /**
     * Sets up the test environment before each test.
     * Initializes mocks and injects them into the servlet.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        inventoryBusiness = mock(InventoryBusiness.class);
        foodItemBusiness = mock(FoodItemBusiness.class);
        inventoryAddServlet = new InventoryAddServlet(foodItemBusiness, inventoryBusiness);  // Use constructor injection
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        foodItem = mock(FoodItemDTO.class);
        inventory = mock(InventoryDTO.class);
    }

    
    /**
     * Cleans up the test environment after each test.
     * Resets the mocks to ensure they are not affected by previous tests.
     */
    @After
    public void tearDown() {
        reset(foodItemBusiness, inventoryBusiness, request, response, dispatcher, foodItem, inventory);
    }
    
    
    
    /**
     * Tests the doGet method of InventoryAddServlet.
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
        // Mock the request dispatcher to forward the request to the "views/addInventory.jsp" page
        when(request.getRequestDispatcher("views/addInventory.jsp")).thenReturn(dispatcher);
        // Call the doGet method of the servlet with the mocked request and response objects
        inventoryAddServlet.doGet(request, response);
        // Verify that the request is set with the correct attribute and forwarded to the correct JSP page
        verify(request).setAttribute("foodItems", foodItemList);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the doPost method of InventoryAddServlet.
     * Verifies that the inventory is added and the user is redirected correctly.
     * 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

        // Mocking request parameters
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        when(request.getParameter("foodItemId")).thenReturn(String.valueOf(foodItemId));
        when(request.getParameter("batchNumber")).thenReturn(batchNumber);
        when(request.getParameter("quantity")).thenReturn(String.valueOf(quantity));
        when(request.getParameter("regularPrice")).thenReturn(String.valueOf(regularPrice));
        when(request.getParameter("discountRate")).thenReturn(String.valueOf(discountRate));
        when(request.getParameter("expirationDate")).thenReturn(expirationDate.toString());
        when(request.getParameter("receiveDate")).thenReturn(receiveDate.toString());

        // Calling the doPost method
        inventoryAddServlet.doPost(request, response);
        
        // Verifying the interactions with InventoryBusiness
        verify(inventoryBusiness).addInventory(any(InventoryDTO.class));
        
        // Verifying the interactions and behavior
        verify(response).sendRedirect(request.getContextPath() + "/inventory?userId=" + userId);
    }
}