/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.businesslayer;

import java.time.LocalDate;
import org.cst8288.foodwastereduction.constants.SurplusStatus;
import org.cst8288.foodwastereduction.constants.UserType;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDAOImpl;
import org.cst8288.foodwastereduction.email.EmailConfig;
import org.cst8288.foodwastereduction.model.Inventory;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.notification.FoodItemService;
import org.cst8288.foodwastereduction.notification.FoodItemServiceImpl;
import org.cst8288.foodwastereduction.notification.NotificationMessageService;
import org.cst8288.foodwastereduction.notification.NotificationMessageServiceImpl;
import org.cst8288.foodwastereduction.notification.NotificationService;
import org.cst8288.foodwastereduction.notification.NotificationServiceImpl;
import org.cst8288.foodwastereduction.notification.Observer;
import org.cst8288.foodwastereduction.notification.ObserverConsumer;
import org.cst8288.foodwastereduction.notification.SubjectInventory;
import org.cst8288.foodwastereduction.notification.SubscriptionService;
import org.cst8288.foodwastereduction.notification.SubscriptionServiceImpl;

/**
 *
 * @author ryany
 */
public class notificationDemo {
    public static void main(String[] args) {

        FoodItemDAO foodItemDAO = new FoodItemDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        NotificationDAO notificationDAO = new NotificationDAOImpl();
        SubscriptionDAO subscriptionDAO = new SubscriptionDAOImpl();

        boolean isTestMode = true;
        
        EmailConfig emailConfig = new EmailConfig("realHost", "realPort", "realUser", "realPassword");
        
        NotificationService notificationService = new NotificationServiceImpl(notificationDAO, isTestMode, emailConfig);     
        NotificationMessageService messageService = new NotificationMessageServiceImpl(foodItemDAO, userDAO);
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionDAO);
        FoodItemService foodItemService = new FoodItemServiceImpl(foodItemDAO);
        
        User testConsumer = new User(1, "Ryan Xu", "ryan.y.xu@hotmail.com", "password", UserType.CONSUMER, "2977277", "123 Woodroffe st.", "Ottawa");
        
        Inventory testInventory = new Inventory(1, 1, 1, "BATCH001", 10, 100.00, 0.2, LocalDate.now().plusDays(-5), LocalDate.now(), true, SurplusStatus.DISCOUNT, true);
        
        Observer consumerObserver = new ObserverConsumer(notificationService, messageService, subscriptionService, foodItemService, testConsumer);

        SubjectInventory subject = new SubjectInventory(testInventory);
        
        // Create email service
        subject.registerObserver(consumerObserver);
        
        subject.setSurplusStatus(SurplusStatus.DISCOUNT);
        
        
        System.out.println("Test completed. Check your test email account for the notification.");
    }
}
