/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;
import org.cst8288.foodwastereduction.model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 * JUnit test for ObserverCharitableOrganization
 * @author Ryan Xu
 * Created on 2024-08-02
 */
public class ObserverCharitableOrganizationTest {
    
    /**
     * Mocked instance of {@link NotificationService}.
     * Used to simulate sending notifications to charitable organizations.
     */
    @Mock
    private NotificationService notificationService;

    /**
     * Mocked instance of {@link NotificationMessageService}.
     * Used to simulate creating notification messages.
     */    
    @Mock
    private NotificationMessageService messageService;

    /**
     * Mocked instance of {@link SubscriptionService}.
     * Used to simulate checking subscription status and interest in food categories.
     */
    @Mock
    private SubscriptionService subscriptionService;

    /**
     * Mocked instance of {@link FoodItemService}.
     * Used to simulate fetching food category information.
     */
    @Mock
    private FoodItemService foodItemService;

    /**
     * Mocked instance of {@link User}.
     * Represents the charitable organization that receives notifications.
     */
    @Mock
    private User charitableOrganization;

    /**
     * Instance of {@link ObserverCharitableOrganization} with injected mocks.
     * This is the system under test.
     */
    @InjectMocks
    private ObserverCharitableOrganization observer;

    /**
     * List of users who have been notified.
     */
    private List<String> notifiedUsers;
    
    /**
     * Default constructor for the test class.
     */
    public ObserverCharitableOrganizationTest() {
    }
    
     /**
     * Method that runs once before any of the test methods in the class.
     * Sets up the test class-level fixtures.
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     * Method that runs once after all the test methods in the class have run.
     * Cleans up the test class-level fixtures.
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
    * Sets up the test environment before each individual test method is run.
    * This method is executed before each test method in the class, ensuring 
    * that the test environment is in a consistent state before every test.
    */
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notifiedUsers = new ArrayList<>();
        observer = new ObserverCharitableOrganization(notificationService, 
                                                      messageService, 
                                                      subscriptionService,
                                                      foodItemService,
                                                      charitableOrganization, 
                                                      notifiedUsers);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testUpdate_WithDonationStatus() {
        // Arrange
        InventoryDTO inventory = mock(InventoryDTO.class);
        when(inventory.getSurplusStatus()).thenReturn(SurplusStatusEnum.Donation);
        when(inventory.getFoodItemId()).thenReturn(1);
        when(inventory.getRetailerId()).thenReturn(10);
        when(inventory.getInventoryId()).thenReturn(100);

        when(charitableOrganization.getUserID()).thenReturn(1);
        when(charitableOrganization.getEmail()).thenReturn("charity@example.com");
        when(charitableOrganization.getName()).thenReturn("CharityOrg");

        CategoryEnum foodCategory = CategoryEnum.Fruit;
        when(foodItemService.getFoodCategory(1)).thenReturn(foodCategory);
        when(subscriptionService.isSubscribed(1, 10)).thenReturn(true);
        when(subscriptionService.isInterestedInCategory(1, 10, foodCategory)).thenReturn(true);
        when(messageService.createDonationMessage(inventory)).thenReturn("Donation Message");

        // Act
        observer.update(inventory);

        // Assert
        verify(notificationService).sendEmail(1, 100, "charity@example.com", "Donation Available", "Donation Message");
        assertTrue(notifiedUsers.contains("CharityOrg"));
    }

    @Test
    public void testUpdate_WithNoSuchElementException() {
        // Arrange
        InventoryDTO inventory = mock(InventoryDTO.class);
        when(inventory.getSurplusStatus()).thenReturn(SurplusStatusEnum.Donation);
        when(inventory.getFoodItemId()).thenReturn(1);
        when(inventory.getRetailerId()).thenReturn(10);
        when(inventory.getInventoryId()).thenReturn(100);

        when(charitableOrganization.getUserID()).thenReturn(1);
        when(charitableOrganization.getEmail()).thenReturn("charity@example.com");
        when(charitableOrganization.getName()).thenReturn("CharityOrg");

        when(foodItemService.getFoodCategory(1)).thenThrow(new NoSuchElementException("Category not found"));

        // Act
        observer.update(inventory);

        // Assert
        // Ensure no email was sent
        verify(notificationService, never()).sendEmail(anyInt(), anyInt(), anyString(), anyString(), anyString());
        // Ensure no user was added to the notified list
        assertFalse(notifiedUsers.contains("CharityOrg"));
    }
    
}
