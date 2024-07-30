/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.utility;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A custom implementation of the ServletContextListener interface.
 * This listener is used to perform tasks during the initialization and destruction
 * of the servlet context.
 * 
 * @author Hongxiu Guo
 */
public class MyAppContextListener implements ServletContextListener {
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        // Initialization code here
//    }

    /**
     * This method is called when the servlet context is about to be destroyed.
     * It performs cleanup tasks to prevent memory leaks, such as shutting down 
     * the AbandonedConnectionCleanupThread and deregistering JDBC drivers.
     * 
     * @param sce the ServletContextEvent containing the ServletContext 
     * that is being destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            // Deregister JDBC drivers to prevent memory leaks
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Optionally, deregister JDBC drivers
        // This is useful when running in a container like Tomcat
        java.sql.DriverManager.drivers().forEach(driver -> {
            try {
                java.sql.DriverManager.deregisterDriver(driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
