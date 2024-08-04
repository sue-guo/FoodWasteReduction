package org.cst8288.foodwastereduction.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;


/**
 *
 * @author Carri and Ryan Xu
 */
public class InventoryStatusServlet extends HttpServlet {
   private  InventoryBusiness inventoryBusiness;
    private NotificationServlet notificationServlet;
//    private NotificationService notificationService;
//    private FoodItemService foodItemService;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        super.init();
       inventoryBusiness = new InventoryBusiness();
//         inventoryDAO = new InventoryDAOImpl();
        notificationServlet = new NotificationServlet();
//        foodItemService = new FoodItemServiceImpl();
//        notificationService = (NotificationService) servletContext.getAttribute("notificationService");
//        
//        if (notificationService == null) {
//            NotificationDAO notificationDAO = new NotificationDAOImpl();
//            boolean isTestMode = true; 
//            EmailConfig emailConfig = EmailConfig.getTestConfig();
//            
//            notificationService = new NotificationServiceImpl(notificationDAO, isTestMode, emailConfig, servletContext);
//            servletContext.setAttribute("notificationService", notificationService);
//        }
    }    
  
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
      try{
          // Get parameters from the request
        String inventoryIdParam = request.getParameter("inventoryId");
        String statusParam = request.getParameter("status");
        
         // Update the surplus status of the inventory item
            int inventoryId = Integer.parseInt(inventoryIdParam);
            SurplusStatusEnum status = SurplusStatusEnum.valueOf(statusParam);
            InventoryDTO inventory = inventoryBusiness.getInventoryById(inventoryId);
        
        try {
            if (inventory != null) {
               
              // Update the inventory
                inventory.setSurplusStatus(status);
                inventoryDAO.updateInventory(inventory);

              
              
                // Call notificationServlet to deal with notification
                List<String> notifiedUsers = notificationServlet.processNotification(inventoryId, status);
                String userTypeNotified = (status == SurplusStatusEnum.Discount) ? "CONSUMER" : "CHARITABLE_ORGANIZATION";
                sendSuccessResponse(response, notifiedUsers, userTypeNotified);
                // Redirect back to the inventory page
//                response.sendRedirect(request.getContextPath() + "/inventory?userId=" + inventory.getRetailerId());
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Inventory not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating inventory: " + e.getMessage());
        }
  

        
         
         LMSLogger.getInstance().saveLogInformation("Updated inventory status for inventoryId = " + inventoryId, InventoryStatusServlet.class.getName(), LogLevel.INFO);
          
          // Redirect back to the inventory page
         response.sendRedirect(request.getContextPath() + "/inventory?userId=" + inventory.getRetailerId());
       
        } catch (Exception ex) {
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryStatusServlet doGet: " + ex.getMessage(), InventoryStatusServlet.class.getName(), LogLevel.ERROR);
            throw new ServletException(ex);
        }
    }
    
  
  
    private void sendSuccessResponse(HttpServletResponse response, List<String> notifiedUsers, String userTypeNotified) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", true);
        responseData.put("message", "Status updated successfully");
        responseData.put("notifiedUsers", notifiedUsers);
        responseData.put("userTypeNotified", userTypeNotified);
        
        sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
    }
    
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", false);
        responseData.put("message", message);

        sendJsonResponse(response, statusCode, responseData);
    }

    private void sendJsonResponse(HttpServletResponse response, int statusCode, Map<String, Object> data) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(data));
    }     

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
}
