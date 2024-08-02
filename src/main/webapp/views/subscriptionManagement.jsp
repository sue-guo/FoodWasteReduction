<%-- 
    Document   : subscriptionManagement
    Created on : Jul 31, 2024, 4:04:36 p.m.
    Author     : ryany
--%>

<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Subscriptions</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/home.css">
    <link rel="stylesheet" href="styles/fooditemlist.css">
    <link rel="stylesheet" href="styles/subscribe_1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
       
    </style>
</head>
<body>
    <header>
        <h1>Food Waste Reduction Platform</h1>
        <nav>
            <ul>
                <li><a href="views/home.jsp">Home</a></li>
                <li>
                    <%
                        // Get the session attribute
                        User user = (User) session.getAttribute("user");
                        if (user != null) {
                            out.print("<p>Welcome, " + user.getName() + "! </p>" + 
                            "<a href=\"logout\" class=\"logout\">[ Logout ]</a>");
                        }
                    %>              
                </li>
            </ul>

        </nav>
    </header>
 
    <form id="subscribe-form">
        <h3>Subscription Management for ${user.name}</h3>  
        <input type="hidden" name="userId" value="${sessionScope.user.userID}">

        <div class="form-row">
            <label for="retailerId">Select Retailer:</label>
            <select name="retailerId" id="retailerId">
                <option value="">Choose a retailer</option>
                <c:forEach var="retailer" items="${localRetailers}">
                    <option value="${retailer.userID}">${retailer.name}</option>
                </c:forEach>
            </select>
        </div>

        <div id="subscription-details" style="display: none;">
            <div class="form-row">
                <label>Communication Preference:</label>
                <div class="radio-options">
                    <div class="radio-element">
                        <input type="radio" name="communicationPreference" id="email" value="EMAIL" checked>
                        <label for="email">Email</label>
                    </div>
                    <div class="radio-element">
                        <input type="radio" name="communicationPreference" id="sms" value="SMS">
                        <label for="sms">SMS</label>
                    </div>
                </div>
            </div>

            <div class="form-row">
                <label>Food Preferences:</label>
            </div>
            <div class="checkbox-container">
                <c:forEach var="preference" items="${foodCategories}">
                    <div class="checkbox-item">
                        <input type="checkbox" name="foodPreferences" value="${preference}" id="${preference}">
                        <label for="${preference}">${preference}</label>
                    </div>
                </c:forEach>
            </div>
            <div>
                <button type="button" id="selectAll">Select All</button>
                <button type="button" id="invertSelection">Invert Selection</button>
                <input type="submit" id="submit-btn" value="Update Subscription" disabled>
            </div>
            <div id="message-container"></div>
        </div>
    </form>

    <script>
    $(document).ready(function() {
        let initialFormState = getFormState();
        
        // Select all food preferences
        $('#selectAll').click(function() {
            $('input[name="foodPreferences"]').prop('checked', true);
        });

        // invert Selection
        $('#invertSelection').click(function() {
            $('input[name="foodPreferences"]').each(function() {
                $(this).prop('checked', !$(this).prop('checked'));
            });
        });
        
        // Get the current state of the foodPreferences
        function getFoodPreferencesState() {
            let foodPreferences = [];

            $('input[name="foodPreferences"]:checked').each(function() {
                foodPreferences.push($(this).val());
            });

            // Sort foodPreferences for consistent comparison
            foodPreferences.sort();

            // Hide warning message if food preferences are selected
            if (foodPreferences.length > 0) {
                $('#no-subscription-message').hide();
            }

            return foodPreferences;
        }
        
        // Function to get the current state of the form
        function getFormState() {
            let state = {
                retailerId: $('#retailerId').val(),
                communicationPreference: $('input[name="communicationPreference"]:checked').val(),
                foodPreferences: getFoodPreferencesState()
            };

            return state;
        }
        
        
        // Function to compare two form states
        function isFormStateChanged() {
            let currentState = getFormState();
            return currentState.retailerId !== initialFormState.retailerId ||
                   currentState.communicationPreference !== initialFormState.communicationPreference ||
                   currentState.foodPreferences.toString() !== initialFormState.foodPreferences.toString();
        }
        
        // Event listener for any form changes
        $('#retailerId, input[name="communicationPreference"], input[name="foodPreferences"]').on('change', function() {
            if (isFormStateChanged()) {
                $('#submit-btn').prop('disabled', false);
            } else {
                $('#submit-btn').prop('disabled', true);
            }
        });
        
        
        // Automatically load the first retailer's subscription if available
        var firstRetailerId = $('#retailerId option:eq(1)').val();
        if (firstRetailerId) {
            $('#retailerId').val(firstRetailerId);
            loadSubscription(firstRetailerId);
        }
        
        // Handle retailer selection change
        $('#retailerId').change(function() {
            var retailerId = $(this).val();
            if (retailerId) {
                loadSubscription(retailerId);
            } else {
                $('#subscription-details').hide();
            }
        });

        // Load subscription details based on selected retailer
        function loadSubscription(retailerId) {
            console.log('Sending data to server:', {
                userId: ${sessionScope.user.userID},
                retailerId: retailerId
            });
            $.ajax({
                url: 'subscribe',
                type: 'GET',
                data: {
                   userId: ${sessionScope.user.userID},
                   retailerId: retailerId
                },
                dataType: 'json',
                success: function(response) {
                    // There may be more than one userSubscription in response
                    // find the one with retailerId
                    console.log('Received response:', response);
                    console.log('retailerId:', retailerId);
                    console.log('response.userSubscriptions:', response.userSubscriptions);
                    let subscription;
                    if (response.userSubscriptions) {
                        for (const pref of response.userSubscriptions){
                            if (pref.userID == retailerId){
                                subscription = pref;
                                break;
                            }
                        }
                    }
//                    let subscription = response.userSubscriptions?.find(sub => sub.userID === retailerId);
               
//                    console.log('subscription:', subscription);
//                    console.log('Typeof subscription:', typeof subscription);
//                    console.log('Typeof subscription.foodPreferences:', typeof subscription.foodPreferences);
                   if (subscription) {
                       updateShowSubscriptionDetails(subscription);
                   } else {
                       showNoSubscriptionMessage();
                   }
                },
                error: function(xhr, status, error) {
                    console.error('AJAX error:', status, error);
                    console.error('Response text:', xhr.responseText);
                    showNoSubscriptionMessage();
                }
            }); 
        }

        // Update the form with subscription details
        function updateShowSubscriptionDetails(subscription) {
            $('#subscription-details').show();
            $('#no-subscription-message').remove();
            $('input[name="communicationPreference"][value="' + subscription.communicationPreference + '"]').prop('checked', true);
             // Reset food preferences checkboxes
            $('input[name="foodPreferences"]').prop('checked', false);

            // Check if foodPreferences exists and is an array
            if (Array.isArray(subscription.foodPreferences)) {
                // Loop through all checkboxes and set checked if value matches
                $('input[name="foodPreferences"]').each(function() {
                    const checkboxValue = $(this).val().toUpperCase(); // Get the value of each checkbox and convert to lowercase
                    if (subscription.foodPreferences.includes(checkboxValue)) {
                        $(this).prop('checked', true);
                    }
                });
            }
            // Reset the form state and disable submit button
            initialFormState = getFormState();
            $('#submit-btn').prop('disabled', true);
       }

        // Show a message when no subscription is found
        function showNoSubscriptionMessage() {
            $('#subscription-details').show();
            resetSubscriptionForm();
            const noSubscriptionMessage = 'No existing subscription found. You can subscribe by selecting your preferences below.';
            let $message = $('#no-subscription-message');
            if ($message.length === 0) {
                $('#subscription-details').prepend('<p id="no-subscription-message" class="info-message highlight"> ' + noSubscriptionMessage + '</p>');
            } else {
                $message.text(noSubscriptionMessage).addClass('highlight');
            }
        }

        // Reset the form to default state
        function resetSubscriptionForm() {
           // keep Communication Preference default: EMAIL
           $('input[name="communicationPreference"]').prop('checked', false);
           $('input[name="foodPreferences"]').prop('checked', false);
        }

        // Handle form submission, this will connect to the SubscribeServlet
        $('#subscribe-form').submit(function(e) {
            e.preventDefault();
            
            // Check if communicationPreference is selected
            let communicationPreference = $('input[name="communicationPreference"]:checked').val();
            if (!communicationPreference) {
                alert('Please select a communication preference.');
                return false; // Prevent form submission
            }
            
            // Get selected food preferences
            const selectedFoodPreferences = $('input[name="foodPreferences"]:checked').map(function() {
                return $(this).val();
            }).get();
            
            // Get the retailer name from the selected option
            const retailerId = $('#retailerId').val();
            const retailerName = $('#retailerId option:selected').text(); // Use .text() to get the display text

            // Check if food preferences are empty
            if (selectedFoodPreferences.length === 0) {
                   // Ask user for confirmation
                   const unsubscribeAlert = 'You have selected no food preferences. This will unsubscribe you from the retailer ' + retailerName + '. Are you sure you want to proceed?';
                   if (!confirm(unsubscribeAlert)) {
                        return; // Exit the function if user cancels
                   }
            }
        
            // Append flag to data if needed
            const formData = $(this).serialize() + '&noFoodPreference=' + (selectedFoodPreferences.length === 0);
            console.log("noFoodPreference:", selectedFoodPreferences.length === 0);
            $.ajax({
                url: 'subscribe',
                type: 'POST',
                data: formData,
                success: function(response) {
                    $('#message-container').html('<div class="alert alert-success">Subscription updated successfully.</div>');
                    $('#submit-btn').prop('disabled', true);
                    initialFormState = getFormState(); // Reset the initial state after a successful submission
                },
                error: function(xhr) {
                    $('#message-container').html('<div class="alert alert-danger">Error updating subscription: ' + xhr.responseText + '</div>');
                }
           });
       });
    });
    </script>
</body>
</html>
