/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.model.SurplusStatusEnum;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.notification.EmailConfig;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.notification.FoodItemService;
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
import org.cst8288.foodwastereduction.model.UserType;
import org.cst8288.foodwastereduction.notification.FoodItemServiceImpl;
import org.cst8288.foodwastereduction.notification.ObserverCharitableOrganization;

/**
 *
 * @author ryany
 */
@WebServlet(name = "NotificationServlet", urlPatterns = {"/NotificationServlet"})
public class NotificationServlet extends HttpServlet {
    
    private UserDao userDAO;
    private InventoryDAO inventoryDAO;
    private SubscriptionDAO subscriptionDAO;
    private NotificationService notificationService;
    private NotificationMessageService messageService;
    private SubscriptionService subscriptionService;
    private FoodItemService foodItemService;

    public NotificationServlet() {
        initializeServices();
    }
    private void initializeServices() {
        if (userDAO == null) userDAO = new UserDaoImpl();
        if (inventoryDAO == null) inventoryDAO = new InventoryDAOImpl();
        if (subscriptionDAO == null) subscriptionDAO = new SubscriptionDAOImpl();
        if (notificationService == null) {
            NotificationDAO notificationDAO = new NotificationDAOImpl();
            EmailConfig emailConfig = EmailConfig.getTestConfig();
            notificationService = new NotificationServiceImpl(notificationDAO, true, emailConfig);
        }
        if (messageService == null) messageService = new NotificationMessageServiceImpl(new FoodItemDAOImpl(), userDAO);
        if (subscriptionService == null) subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, userDAO);
        if (foodItemService == null) foodItemService = new FoodItemServiceImpl();
    }
        
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        userDAO = new UserDaoImpl();
//        inventoryDAO = new InventoryDAOImpl();
//        subscriptionDAO = new SubscriptionDAOImpl();
//        NotificationDAO notificationDAO = new NotificationDAOImpl();
//        EmailConfig emailConfig = EmailConfig.getTestConfig();
//        ServletContext servletContext = getServletContext(); 
//        notificationService = new NotificationServiceImpl(notificationDAO, true, emailConfig, servletContext);
//        messageService = new NotificationMessageServiceImpl(new FoodItemDAOImpl(), userDAO);
//        subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, userDAO);
//        foodItemService = new FoodItemServiceImpl(); 
//    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//      throws ServletException, IOException {
//        System.out.println("Entering doPost method");
//
//        String statusString = request.getParameter("status").toUpperCase();
//        String inventoryIdParam = request.getParameter("inventoryId");
//        System.out.println("Received inventoryId: " + inventoryIdParam + ", status: " + statusString);
//        List<String> notifiedUsers = new ArrayList<>();
//        
//        int inventoryId;
//        try {
//            inventoryId = Integer.parseInt(inventoryIdParam);
//        } catch (NumberFormatException e) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("Invalid inventory ID format");
//            return;
//        }                
//
//        try {
//            InventoryDTO inventory = inventoryDAO.getInventoryById(inventoryId);
//            if (inventory != null) {
//                SurplusStatusEnum newStatus = SurplusStatusEnum.valueOf(statusString);
//                
//                SubjectInventory subject = new SubjectInventory(inventory);
//                List<User> subscribers = subscriptionService.getUserByRetailerId(inventory.getRetailerId());
//                for (User subscriber : subscribers) {
//                    Observer observer = new ObserverConsumer(notificationService, messageService, subscriptionService, foodItemService, subscriber, notifiedUsers);
//                    subject.registerObserver(observer);
//                }
//                subject.setSurplusStatus(newStatus);
//
//                // Update the database
//                inventory.setSurplusStatus(newStatus);
//                inventoryDAO.updateInventory(inventory);
//
//                response.setContentType("text/plain;charset=UTF-8");
//                response.getWriter().write("Inventory updated and notifications sent.");
//            } else {
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().write("Inventory not found.");
//            }
//        } catch (Exception e) {
//            System.err.println("Error in doPost: " + e.getMessage());
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("Error updating inventory: " + e.getMessage());
//        }
//    }
    
    /**
     * Make Existed NotificationServlet can be called by InventoryStatusServlet
     * @param inventoryId
     * @param newStatus
     * @return 
     * @throws Exception 
     */
    public List<String> processNotification(int inventoryId, SurplusStatusEnum newStatus) throws Exception {
        List<String> notifiedUsers = new ArrayList<>();
        
        if (foodItemService == null) {
            throw new ServletException("FoodItemService is not initialized");
        }
        
        if (subscriptionService == null) {
                throw new ServletException("SubscriptionService is not initialized");
        }
		
        InventoryDTO inventory = inventoryDAO.getInventoryById(inventoryId);
        if (inventory != null) {
            SubjectInventory subject = new SubjectInventory(inventory);
            List<User> subscribers = subscriptionService.getUserByRetailerId(inventory.getRetailerId());           
//            System.out.println("Found " + subscribers.size() + " subscribers for retailer: " + inventory.getRetailerId());
            
            for (User subscriber : subscribers) {
                Observer observer;
                if (subscriber.getUserType() == UserType.CONSUMER && newStatus == SurplusStatusEnum.Discount) {
                    observer = new ObserverConsumer(notificationService, messageService, subscriptionService, foodItemService, subscriber, notifiedUsers);
                    subject.registerObserver(observer);
                } else if (subscriber.getUserType() == UserType.CHARITABLE_ORGANIZATION && newStatus == SurplusStatusEnum.Donation) {
                    observer = new ObserverCharitableOrganization(notificationService, messageService, subscriptionService, foodItemService, subscriber, notifiedUsers);
                    subject.registerObserver(observer);
                }
            }
            subject.setSurplusStatus(newStatus);
            // Update the database completed in InventoryStatusServLet, no need to do here again
//            inventory.setSurplusStatus(newStatus);
//            inventoryDAO.updateInventory(inventory);
        } else {
            throw new Exception("Inventory not found.");
        }
        
        return notifiedUsers;
    }  
}
