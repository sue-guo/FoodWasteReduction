/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.businesslayer;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;
import org.cst8288.foodwastereduction.model.SurplusStatusEnum;
import org.cst8288.foodwastereduction.model.UserType;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.email.EmailConfig;
import org.cst8288.foodwastereduction.model.InventoryDTO;
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
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;
import org.cst8288.foodwastereduction.model.SubscriberDTO;
import org.cst8288.foodwastereduction.model.Subscription;

/**
 *
 * @author ryany
 */
public class notificationDemo {
    public static void main(String[] args) {

        FoodItemDAO foodItemDAO = new FoodItemDAOImpl();
        UserDao userDAO = new UserDaoImpl();
        NotificationDAO notificationDAO = new NotificationDAOImpl();
        SubscriptionDAO subscriptionDAO = new SubscriptionDAOImpl();

        boolean isTestMode = true;
        
        EmailConfig emailConfig = new EmailConfig("realHost", "realPort", "realUser", "realPassword");
        
        NotificationService notificationService = new NotificationServiceImpl(notificationDAO, isTestMode, emailConfig);     
        NotificationMessageService messageService = new NotificationMessageServiceImpl(foodItemDAO, userDAO);
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, userDAO);
        FoodItemService foodItemService = new FoodItemServiceImpl();
        
        User testConsumer = new User(1, "Ryan Xu", "ryan.y.xu@hotmail.com", "password", UserType.CONSUMER, "2977277", "123 Woodroffe st.", "Ottawa");
        
        Calendar calendar = Calendar.getInstance();
        java.util.Date todayUtilDate = calendar.getTime();
        Date currentDate = new Date(todayUtilDate.getTime());
        
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        java.util.Date pastUtilDate = calendar.getTime();
        Date pastDate = new Date(pastUtilDate.getTime());
        
        List<String> notifiedUsers = new ArrayList<>();
//        notifiedUsers = ;
        
        InventoryDTO testInventory = new InventoryDTO(1, 1, 1, "BATCH001", 10, 100.00, 0.2, pastDate, currentDate, true, SurplusStatusEnum.Discount, true);
        
        Observer consumerObserver = new ObserverConsumer(notificationService, messageService, subscriptionService, foodItemService, testConsumer, notifiedUsers);

        SubjectInventory subject = new SubjectInventory(testInventory);
        
        testSubscriptionService(subscriptionService, userDAO, subscriptionDAO);
        
        // Create email service
        subject.registerObserver(consumerObserver);
        
        subject.setSurplusStatus(SurplusStatusEnum.Discount);
        
        
        System.out.println("Test completed. Check your test email account for the notification.");
    }
    
    
    private static void testSubscriptionService(SubscriptionService subscriptionService, UserDao userDAO, SubscriptionDAO subscriptionDAO) {
        // 1.Create test data
        User testRetailer = new User(7, "Test Retailer", "retailer@test.com", "password", UserType.RETAILER, "1234567", "456 Test St.", "TestCity");
        User testConsumer1 = new User(8, "Test Consumer 1", "consumer1@test.com", "password", UserType.CONSUMER, "7654321", "789 Test Ave.", "TestCity");
        User testConsumer2 = new User(9, "Test Consumer 2", "consumer2@test.com", "password", UserType.CONSUMER, "1122334", "321 Test Blvd.", "TestCity");

        // Add users to database
//        userDAO.addUser(testRetailer);
//        userDAO.addUser(testConsumer1);
//        userDAO.addUser(testConsumer2);

        // Create subscription
        Subscription subscription1 = new Subscription(3, testConsumer1.getUserID(), testRetailer.getUserID(), CommunicationPreference.EMAIL, new HashSet<>(Arrays.asList("Vegetable", "Fruit")), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        Subscription subscription2 = new Subscription(4, testConsumer2.getUserID(), testRetailer.getUserID(), CommunicationPreference.EMAIL, new HashSet<>(Arrays.asList("Dairy", "Beef")), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        System.out.println("subscription1: " + subscription1.toString());
        
        // 添加订阅到数据库
//        subscriptionDAO.addSubscription(subscription1);
//        subscriptionDAO.addSubscription(subscription2);
        


        // 2. 测试 getSubscribersByRetailerId 方法
        List<SubscriberDTO> subscribers = subscriptionService.getSubscribersByRetailerId(testRetailer.getUserID());

        // 3. 验证结果
        System.out.println("Number of subscribers found: " + subscribers.size());
        for (SubscriberDTO subscriber : subscribers) {
            System.out.println("User ID: " + subscriber.getUserID());
            System.out.println("Subscriber Name: " + subscriber.getUserName());
            System.out.println("Subscriber Type: " + subscriber.getUserType());
            System.out.println("Communication Preference: " + subscriber.getCommunicationPreference());
            System.out.println("Food Preferences: " + subscriber.getFoodPreferences());
            System.out.println("Created At: " + subscriber.getCreatedAt());
            System.out.println("Last Updated: " + subscriber.getLastUpdated());
            System.out.println("-----------------------------");
        }

    // 4. 清理测试数据
//        subscriptionDAO.deleteSubscription(subscription1.getSubscriptionId());
//        subscriptionDAO.deleteSubscription(subscription2.getSubscriptionId());
//        userDAO.deleteUser(testRetailer.getUserID());
//        userDAO.deleteUser(testConsumer1.getUserID());
//        userDAO.deleteUser(testConsumer2.getUserID());
    }
}
