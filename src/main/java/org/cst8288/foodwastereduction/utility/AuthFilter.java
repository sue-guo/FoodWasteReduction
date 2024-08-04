/* File: AuthFilter.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.08
 * Modified: 
 * Description: A servlet filter that performs authentication checks on incoming requests.
 *
 */
package org.cst8288.foodwastereduction.utility;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * A servlet filter that performs authentication checks on incoming requests.
 * 
 * This filter ensures that users are redirected to the login page if they are not authenticated
 * and are trying to access protected resources. It excludes requests for login, signup, and logout pages
 * from this check.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {
    /**
     * Initializes the filter. This method can be used to set up filter-specific parameters.
     * 
     * @param filterConfig The filter configuration object.
     * @throws ServletException if an error occurs during filter initialization.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
           // Initialization logic for the filter (if needed).
    }
    /**
     * Processes requests and responses. This method performs authentication checks and redirects
     * to the login page if the user is not authenticated and the request is for a protected resource.
     * 
     * @param request The servlet request object.
     * @param response The servlet response object.
     * @param chain The filter chain used to pass the request and response.
     * @throws IOException if an I/O error occurs during the filtering process.
     * @throws ServletException if a servlet-specific error occurs during the filtering process.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
         // Get the current session if it exists; otherwise, return null.
        HttpSession session = httpRequest.getSession(false);
        
        // Define URIs to be excluded from authentication checks
        String loginURI = httpRequest.getContextPath() + "/views/login.jsp";
        String signupURI = httpRequest.getContextPath() + "/views/signup.jsp";
        String logoutURI = httpRequest.getContextPath() + "/logout";
        String loginURI2 = httpRequest.getContextPath() + "/login";
        String signupURI2 = httpRequest.getContextPath() + "/signup";

        // Check if the request is for login, signup, or logout
        boolean isLoginJsp = httpRequest.getRequestURI().equals(loginURI);
        boolean isSignupJsp = httpRequest.getRequestURI().equals(signupURI);
        boolean isLogoutRequest = httpRequest.getRequestURI().equals(logoutURI);
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI2);
        boolean isSignupRequest = httpRequest.getRequestURI().equals(signupURI2);

        // If the request is not for login, signup, or logout, and the session is invalid or the user is not logged in,
        // redirect to the login page.
        if (!isLoginJsp && !isSignupJsp && !isLogoutRequest && !isLoginRequest && !isSignupRequest
                && (session == null || session.getAttribute("user") == null)) {
            
            // Redirect to login page if the user is logged out
            httpResponse.sendRedirect(loginURI);

        } else {
            // User is logged in or request is for excluded pages, continue processing the request.
            chain.doFilter(request, response);
        }

    }
    
    /**
     * Destroys the filter. This method can be used to release any resources held by the filter.
     */
    @Override
    public void destroy() {
         // Cleanup logic for the filter (if needed).
    }
}