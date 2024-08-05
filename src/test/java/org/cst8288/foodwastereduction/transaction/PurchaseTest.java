package org.cst8288.foodwastereduction.transaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.TransactionBusiness;
import org.cst8288.foodwastereduction.controller.PurchaseServlet;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class PurchaseTest {
    /**
     * Instance of the servlet to be tested
     */
    @Mock
    private PurchaseServlet purchaseServlet;
    /**
     * Mocked HttpServletRequest
     */
    @Mock
    private HttpServletRequest request;
    /**
     * Mocked HttpServletResponse
     */
    @Mock
    private HttpServletResponse response;
    /**
     * Mocked HttpSession
     */
    @Mock
    private HttpSession session;
    /**
     * Mocked RequestDispatcher
     */
    @Mock
    private RequestDispatcher dispatcher;
    /**
     * Mocked InventoryBusiness dependency
     */
    @Mock
    private InventoryBusiness inventoryBusiness;
    /**
     * Mocked FoodItemBusiness dependency
     */
    @Mock
    private FoodItemBusiness foodItemBusiness;
    /**
     * Mocked TransactionBusiness dependency
     */
    @Mock
    private TransactionBusiness transactionBusiness;
    /**
     * Mocked InventoryDTO
     */
    @Mock
    private InventoryDTO inventory;
    /**
     * Mocked FoodItemDTO
     */
    @Mock
    private FoodItemDTO foodItem;
    /**
     * Mocked Transaction
     */
    @Mock
    private Transaction transaction;

    /**
     * Sets up the test environment before each test method is run.
     */
    @Before
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create an instance of PurchaseServlet with the mocked dependencies
        purchaseServlet = new PurchaseServlet();

        foodItemBusiness = mock(FoodItemBusiness.class);
        inventoryBusiness = mock(InventoryBusiness.class);
        transactionBusiness = mock(TransactionBusiness.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        inventory = mock(InventoryDTO.class);
        foodItem = mock(FoodItemDTO.class);
        transaction = mock(Transaction.class);
    }

    /**
     * Cleans up the test environment after each test method is run.
     */
    @After
    public void tearDown() {
        // Reset the mocks after each test
        reset(inventoryBusiness, foodItemBusiness, transactionBusiness, request, response, dispatcher, inventory, foodItem, transaction);
    }

    /**
     * Tests the doGet method of PurchaseServlet when inventory is found.
     * Verifies that the inventory and food item are set as request attributes and forwarded to purchase.jsp.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDoGet_InventoryFound() throws Exception {
        // Arrange
        when(request.getParameter("inventoryId")).thenReturn("1");
        when(inventoryBusiness.getInventoryById(1)).thenReturn(inventory);
        when(request.getRequestDispatcher("/views/purchase.jsp")).thenReturn(dispatcher);

        // Act
        purchaseServlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("inventory", inventory);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the doGet method of PurchaseServlet when inventory is not found.
     * Verifies that a 404 error is sent and an error log is recorded.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDoGet_InventoryNotFound() throws Exception {
        // Arrange
        when(request.getParameter("inventoryId")).thenReturn("1");
        when(inventoryBusiness.getInventoryById(1)).thenReturn(null);

        // Act
        purchaseServlet.doGet(request, response);

        // Assert
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory not found");
        verifyNoMoreInteractions(request, dispatcher);
    }

    /**
     * Tests the doPost method of PurchaseServlet for a successful purchase.
     * Verifies that the inventory is updated and a transaction is created.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDoPost_Success() throws Exception {
        // Arrange
        when(request.getParameter("inventoryId")).thenReturn("1");
        when(request.getParameter("quantity")).thenReturn("5");
        when(inventoryBusiness.getInventoryById(1)).thenReturn(inventory);

        // Act
        purchaseServlet.doPost(request, response);

        // Assert
        verify(inventory).setQuantity(anyInt());
        verify(inventoryBusiness).updateInventory(inventory);
        verify(transactionBusiness).addTransaction(any(Transaction.class));
        verify(response).sendRedirect(anyString());
    }

    /**
     * Tests the doPost method of PurchaseServlet for invalid input.
     * Verifies that a 400 error is sent for invalid inventory ID or quantity format.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDoPost_InvalidInput() throws Exception {
        // Arrange
        when(request.getParameter("inventoryId")).thenReturn("abc");
        when(request.getParameter("quantity")).thenReturn("5");

        // Act
        purchaseServlet.doPost(request, response);

        // Assert
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid inventory ID or quantity");
    }
}