/* File: LoginTest.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.08
 * Modified: 
 * Description: This class define Unit tests for the LoginServlet class.
 *
 */
package org.cst8288.foodwastereduction.loginSignup;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.cst8288.foodwastereduction.businesslayer.UserBusiness;
import org.cst8288.foodwastereduction.controller.LoginServlet;
import org.cst8288.foodwastereduction.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the LoginServlet class.
 * 
 * This test class uses JUnit and Mockito to test the behavior of the LoginServlet class,
 * ensuring it correctly handles user login attempts.
 *
 * @author Hongxiu Guo
 */
public class LoginTest {
    
    /**
     * Instance of the servlet to be tested
     */
    @Mock
    private LoginServlet loginServlet;   
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
     * Mocked UserBusiness dependency
     */
    @Mock
    private UserBusiness userBusiness;
    /**
     * Mocked User
     */
    @Mock
    private User user;
    
    /**
     * Sets up the test environment before each test method is run.
     * Initializes the mocks and the servlet instance with the mocked UserBusiness.
     */    
    @Before
    public void setUp() {
        // Initialize the mocks
        MockitoAnnotations.initMocks(this);
        
        // Create an instance of LoginServlet with the mock UserBusiness
        loginServlet = new LoginServlet(userBusiness);
        
        // Ensure getSession(false) returns the mock HttpSession
        when(request.getSession(false)).thenReturn(session);   

    }
     /**
     * Cleans up the test environment after each test method is run.
     * Resets the mocks to ensure they are not affected by previous tests.
     */   
    @After
    public void tearDown() {
        // Reset the mocks after each test to ensure they are not affected by previous tests
        reset(userBusiness, request, response, session, dispatcher, user);
    }
    
    /**
     * Tests the doPost method of LoginServlet when the user is not found.
     * Verifies that the appropriate error message is set and the request is forwarded to login.jsp.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDoPost_UserNotFound() throws Exception {
        // Arrange: Set up the request parameters and mock behaviors
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        when(userBusiness.getUserByEmail("test@example.com")).thenReturn(null);
        when(userBusiness.authenticateUser("test@example.com", "wrongpassword")).thenReturn(false);
        when(request.getRequestDispatcher("views/login.jsp")).thenReturn(dispatcher);

        // Act: Call the doPost method of the servlet
        loginServlet.doPost(request, response);

        // Assert: Verify the expected behaviors
        verify(request).setAttribute("errorMessage", "User does not exist or password is wrong. Please try again.");
        verify(request).setAttribute("email", "test@example.com");
        verify(request).setAttribute("password", "wrongpassword");
        verify(dispatcher).forward(request, response);
    }
    
    /**
     * Tests the doPost method of LoginServlet when the user is found and authenticated.
     * Verifies that the user is set in the session and the request is forwarded to home.jsp.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDoPost_UserFound() throws Exception {
        // Arrange: Set up the request parameters and mock behaviors
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("correctpassword");
        when(userBusiness.getUserByEmail("test@example.com")).thenReturn(user);
        when(userBusiness.authenticateUser("test@example.com", "correctpassword")).thenReturn(true);
        // Ensure that getSession() returns a non-null mock session
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("views/home.jsp")).thenReturn(dispatcher);

        // Act: Call the doPost method of the servlet
        loginServlet.doPost(request, response);

        // Assert: Verify the expected behaviors
        verify(session).setAttribute("user", user);
        verify(request).setAttribute("user", user);
        verify(dispatcher).forward(request, response);
    }
}
