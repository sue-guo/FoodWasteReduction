/* File: PaymentStrategyTest.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This is a utility class Logger class for logging messages to a file.
 *
 */
package org.cst8288.foodwastereduction.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for various implementations of the PaymentStrategy interface.
 * 
 * The tests verify that each payment strategy (PayPal, Credit Card, Check, and Apple Pay) 
 * processes payment details correctly and returns the expected JSON output. 
 * 
 *
 * @author Hongxiu Guo
 */
public class PaymentStrategyTest {
    
    @Mock
    private HttpServletRequest request;

    private PaymentStrategy paypalPaymentStrategy;
    private PaymentStrategy creditCardPaymentStrategy;
    private PaymentStrategy checkPaymentStrategy;
    private PaymentStrategy applePayPaymentStrategy;
    /**
     * Initializes mocks and sets up the payment strategy objects before each test.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        paypalPaymentStrategy = new PaypalPaymentStrategy();
        creditCardPaymentStrategy = new CreditCardPaymentStrategy();
        checkPaymentStrategy = new CheckPaymentStrategy();
        applePayPaymentStrategy = new ApplePayPaymentStrategy();
    }
    /**
     * Tear down the test environment.
     */
    @After
    public void tearDown() {
        paypalPaymentStrategy = null;
        creditCardPaymentStrategy = null;
        checkPaymentStrategy = null;
        applePayPaymentStrategy = null;
    }
    /**
     * Tests the PayPal payment strategy to ensure it processes payment details correctly.
     * 
     * @throws Exception if any error occurs during the payment processing
     */
    @Test
    public void testPaypalPaymentStrategy() throws Exception {
        // Set up mock request parameters for PayPal payment
        when(request.getParameter("email")).thenReturn("user@example.com");
        when(request.getParameter("password")).thenReturn("password123");

        // Process the payment using the PayPal payment strategy
        String paymentDetails = paypalPaymentStrategy.processPayment(request);

        // Verify that the processed payment details match the expected result
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode expectedPaymentDetails = mapper.createObjectNode();
        expectedPaymentDetails.put("type", "PAYPAL");
        expectedPaymentDetails.put("email", "user@example.com");
        expectedPaymentDetails.put("password", "password123");
        String expectedResult = expectedPaymentDetails.toString();

        assertEquals(expectedResult, paymentDetails);
    }
    /**
     * Tests the Credit Card payment strategy to ensure it processes payment details correctly.
     * 
     * @throws Exception if any error occurs during the payment processing
     */
    @Test
    public void testCreditCardPaymentStrategy() throws Exception {
        // Set up mock request parameters for Credit Card payment
        when(request.getParameter("cardNumber")).thenReturn("4111111111111111");
        when(request.getParameter("cardHolder")).thenReturn("John Doe");
        when(request.getParameter("expiryDate")).thenReturn("12/2025");
        when(request.getParameter("cvv")).thenReturn("123");

        // Process the payment using the Credit Card payment strategy
        String paymentDetails = creditCardPaymentStrategy.processPayment(request);

        //  Verify that the processed payment details match the expected result
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode expectedPaymentDetails = mapper.createObjectNode();
        expectedPaymentDetails.put("type", "CREDIT_CARD");
        expectedPaymentDetails.put("cardNumber", "4111111111111111");
        expectedPaymentDetails.put("cardHolder", "John Doe");
        expectedPaymentDetails.put("expiryDate", "12/2025");
        expectedPaymentDetails.put("cvv", "123");
        String expectedResult = expectedPaymentDetails.toString();

        assertEquals(expectedResult, paymentDetails);
    }
    /**
     * Tests the Check payment strategy to ensure it processes payment details correctly.
     * 
     * @throws Exception if any error occurs during the payment processing
     */
    @Test
    public void testCheckPaymentStrategy() throws Exception {
        // Set up mock request parameters for Check payment
        when(request.getParameter("checkNumber")).thenReturn("123456");
        when(request.getParameter("bankName")).thenReturn("Bank of Test");
        when(request.getParameter("accountNumber")).thenReturn("123456789");
        when(request.getParameter("routingNumber")).thenReturn("987654321");
        when(request.getParameter("payee")).thenReturn("John Doe");
        when(request.getParameter("date")).thenReturn("2024-08-03");
        when(request.getParameter("memo")).thenReturn("Payment for invoice");

        // Process the payment using the Check payment strategy
        String paymentDetails = checkPaymentStrategy.processPayment(request);

        //Verify that the processed payment details match the expected result
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode expectedPaymentDetails = mapper.createObjectNode();
        expectedPaymentDetails.put("type", "CHECK");
        expectedPaymentDetails.put("checkNumber", "123456");
        expectedPaymentDetails.put("bankName", "Bank of Test");
        expectedPaymentDetails.put("accountNumber", "123456789");
        expectedPaymentDetails.put("routingNumber", "987654321");
        expectedPaymentDetails.put("payee", "John Doe");
        expectedPaymentDetails.put("date", "2024-08-03");
        expectedPaymentDetails.put("memo", "Payment for invoice");
        String expectedResult = expectedPaymentDetails.toString();

        assertEquals(expectedResult, paymentDetails);
    }
    /**
     * Tests the Apple Pay payment strategy to ensure it processes payment details correctly.
     * 
     * @throws Exception if any error occurs during the payment processing
     */
    @Test
    public void testApplePayPaymentStrategy() throws Exception {
        //Set up mock request parameters for Apple Pay payment
        when(request.getParameter("deviceToken")).thenReturn("deviceToken123");

        //Process the payment using the Apple Pay payment strategy
        String paymentDetails = applePayPaymentStrategy.processPayment(request);

        // Verify that the processed payment details match the expected result
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode expectedPaymentDetails = mapper.createObjectNode();
        expectedPaymentDetails.put("type", "APPLE_PAY");
        expectedPaymentDetails.put("deviceToken", "deviceToken123");
        String expectedResult = expectedPaymentDetails.toString();

        assertEquals(expectedResult, paymentDetails);
    }
    
    
    /**
     * Tests the PaymentContext class to ensure it correctly uses the appropriate PaymentStrategy
     * based on the payment type specified.
     * 
     * @throws Exception if any error occurs during the payment processing
     */
    @Test
    public void testPaymentContext() throws Exception {
        // Create PaymentContext instances with different PaymentStrategy implementations
        PaymentContext paypalContext = new PaymentContext(paypalPaymentStrategy);
        PaymentContext creditCardContext = new PaymentContext(creditCardPaymentStrategy);
        PaymentContext checkContext = new PaymentContext(checkPaymentStrategy);
        PaymentContext applePayContext = new PaymentContext(applePayPaymentStrategy);

        // Set up mock request parameters for paypal
        when(request.getParameter("email")).thenReturn("user@example.com");
        when(request.getParameter("password")).thenReturn("password123");
        // execute Payment  method for paypal
        String paypalPaymentDetails = paypalContext.executePayment(request);
        
        // Set up mock request parameters for redit Card    
        when(request.getParameter("cardNumber")).thenReturn("4111111111111111");
        when(request.getParameter("cardHolder")).thenReturn("John Doe");
        when(request.getParameter("expiryDate")).thenReturn("12/2025");
        when(request.getParameter("cvv")).thenReturn("123");
        // execute Payment  method for redit Card
        String creditCardPaymentDetails = creditCardContext.executePayment(request);
        
        // Set up mock request parameters for check
        when(request.getParameter("checkNumber")).thenReturn("123456");
        when(request.getParameter("bankName")).thenReturn("Bank of Test");
        when(request.getParameter("accountNumber")).thenReturn("123456789");
        when(request.getParameter("routingNumber")).thenReturn("987654321");
        when(request.getParameter("payee")).thenReturn("John Doe");
        when(request.getParameter("date")).thenReturn("2024-08-03");
        when(request.getParameter("memo")).thenReturn("Payment for invoice");
         // execute Payment  method for check
        String checkPaymentDetails = checkContext.executePayment(request);

        // Set up mock request parameters for applePay
        when(request.getParameter("deviceToken")).thenReturn("deviceToken123");
        // execute Payment  method for applePay
        String applePayPaymentDetails = applePayContext.executePayment(request);

        // Verify that the processed payment details match the expected results
        ObjectMapper mapper = new ObjectMapper();
        // Verify that the processed payment details match the expected results for Paypal
        ObjectNode expectedPaypalDetails = mapper.createObjectNode();
        expectedPaypalDetails.put("type", "PAYPAL");
        expectedPaypalDetails.put("email", "user@example.com");
        expectedPaypalDetails.put("password", "password123");
        assertEquals(expectedPaypalDetails.toString(), paypalPaymentDetails);

        // Verify that the processed payment details match the expected results for creditCard
        ObjectNode expectedCreditCardDetails = mapper.createObjectNode();
        expectedCreditCardDetails.put("type", "CREDIT_CARD");
        expectedCreditCardDetails.put("cardNumber", "4111111111111111");
        expectedCreditCardDetails.put("cardHolder", "John Doe");
        expectedCreditCardDetails.put("expiryDate", "12/2025");
        expectedCreditCardDetails.put("cvv", "123");
        assertEquals(expectedCreditCardDetails.toString(), creditCardPaymentDetails);

        // Verify that the  processed payment details match the expected results for Check
        ObjectNode expectedCheckDetails = mapper.createObjectNode();
        expectedCheckDetails.put("type", "CHECK");
        expectedCheckDetails.put("checkNumber", "123456");
        expectedCheckDetails.put("bankName", "Bank of Test");
        expectedCheckDetails.put("accountNumber", "123456789");
        expectedCheckDetails.put("routingNumber", "987654321");
        expectedCheckDetails.put("payee", "John Doe");
        expectedCheckDetails.put("date", "2024-08-03");
        expectedCheckDetails.put("memo", "Payment for invoice");
        assertEquals(expectedCheckDetails.toString(), checkPaymentDetails);

        // Verify that the  processed payment details match the expected results for ApplePay
        ObjectNode expectedApplePayDetails = mapper.createObjectNode();
        expectedApplePayDetails.put("type", "APPLE_PAY");
        expectedApplePayDetails.put("deviceToken", "deviceToken123");
        assertEquals(expectedApplePayDetails.toString(), applePayPaymentDetails);
    }
}
