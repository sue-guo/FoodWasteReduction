/* File: SignupTest.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.08
 * Modified: 
 * Description: This class define Unit tests for the SignupServlet class.
 *
 */
package org.cst8288.foodwastereduction.loginSignup;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.cst8288.foodwastereduction.businesslayer.UserBusiness;
import org.cst8288.foodwastereduction.controller.SignupServlet;
import org.cst8288.foodwastereduction.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the SignupServlet class.
 * 
 * This test class verifies the behavior of the SignupServlet, ensuring that user signup
 * functionality works correctly, including handling cases where the user already exists
 * or where a new user is successfully created.
 * 
 * @author Hongxiu Guo
 */
public class SignupTest {
    /**
     * Instance of the servlet to be tested
     */
    @Mock
    private SignupServlet  signupServlet;   
    /**
     * Mocked HttpServletRequest
     * Simulates the HTTP request sent by the client
     */
    @Mock
    private HttpServletRequest request;
    /**
     * Mocked HttpServletResponse
     * Simulates the HTTP response sent by the servlet.
     */
    @Mock
    private HttpServletResponse response;
    /**
     * Mocked HttpSession
     * Simulates the HTTP session used to store user data.
     */
    @Mock
    private HttpSession session;
    /**
     * Mocked RequestDispatcher
     * Simulates the dispatcher used to forward requests and responses.
     */
    @Mock
    private RequestDispatcher dispatcher;
    /**
     * Mocked UserBusiness dependency
     * Simulates the business logic class used to interact with user data.
     */
    @Mock
    private UserBusiness userBusiness;
    /**
     * Mocked User: Represents a user entity, used in tests to simulate existing users.
     */
    @Mock
    private User user;
    
    /**
     * Runs before each test method is executed.
     * Initializes mocks and creates an instance of SignupServlet with the mocked UserBusiness.
     */    
    @Before
    public void setUp() {
        // Initialize mocks for the servlet and its dependencies
        userBusiness = mock(UserBusiness.class);
        signupServlet = new SignupServlet(userBusiness);  // Use constructor injection
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        user = mock(User.class);
    }
    /**
     * Runs after each test method has been executed.
     * Resets mocks to ensure they are not affected by previous tests.
     */    
    @After
    public void tearDown() {
        // Reset the mocks after each test to ensure they are not affected by previous tests
        reset(userBusiness, request, response, dispatcher, user);
    }
    
    /**
     * Tests the doPost method of SignupServlet when a user already exists.
     * Verifies that the appropriate error message is set and the request is forwarded to signup.jsp.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDoPost_UserAlreadyExists() throws Exception {
        // Set up the request parameters and mock behaviors
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(userBusiness.getUserByEmail("test@example.com")).thenReturn(user);
        when(request.getRequestDispatcher("views/signup.jsp")).thenReturn(dispatcher);

        // Call the doPost method of the servlet
        signupServlet.doPost(request, response);

        // Verify that the error message is set and the request is forwarded to signup.jsp
        verify(request).setAttribute("errorMessage", "User already exists. Please try with a different email.");
        verify(dispatcher).forward(request, response);
    }
    
    /**
     * Tests the doPost method of SignupServlet when a new user is being registered.
     * Verifies that the user is added and the response redirects to the login page.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDoPost_NewUser() throws Exception {
        // Set up the request parameters and mock behaviors
        when(request.getParameter("email")).thenReturn("newuser@example.com");
        when(userBusiness.getUserByEmail("newuser@example.com")).thenReturn(null);

        //  Mock request parameters for addUser method
        when(request.getParameter("name")).thenReturn("New User");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("userType")).thenReturn("RETAILER");
        when(request.getParameter("phoneNumber")).thenReturn("1234567890");
        when(request.getParameter("address")).thenReturn("123 Main St");
        when(request.getParameter("city")).thenReturn("City");

        // Call the doPost method of the servlet
        signupServlet.doPost(request, response);

        //Verify that the user is added and the response redirects to login.jsp
        verify(response).sendRedirect("views/login.jsp");
        verify(userBusiness).addUser(any(User.class));
    }

}
