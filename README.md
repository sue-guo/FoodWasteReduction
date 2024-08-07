# CST8288 Final Project - Food Waste Reduction Platform
## High Level Design

### Version History
| Version | Author       | Description                                       | Date       |
|---------|--------------|---------------------------------------------------|------------|
| 1.0     | Hongxiu Guo  | Basic architecture                                | 2024-07-18 |
| 2.0     | Hongxiu Guo  | Add login/logout and signup functions             | 2024-07-24 |
| 2.1     | Jiayun Wang  | Add retailer related functions                    | 2024-07-29 |
| 2.2     | Ryan Xu      | Add notification function                         | 2024-07-29 |
| 2.3     | Yi Yao       | Add consumer and charitable organization related functions | 2024-07-31 |
| 2.4     | Ryan Xu      | Add subscribe function                            | 2024-08-01 |
| 2.5     | Hongxiu Guo  | Add bonus function - payment                      | 2024-08-02 |

--------------------------------

### Instruction
The Food Waste Reduction Platform aims to tackle the worldwide problem of food waste by offering a holistic solution that links food retailers, consumers, and charitable organizations. This platform plays a crucial role in promoting sustainability, alleviating hunger, and fortifying food ecosystems. It fosters cooperation among stakeholders throughout the food supply chain, encouraging unified efforts to confront one of today's most pressing challenges.

This document outlines the high-level design of the platform, covering the application architecture, business architecture, data architecture, detailed design, security architecture, deployment architecture, testing model, and more.

--------------------------------

### Targeted Audience 
**Retailers:** Retailers aim to manage their surplus food efficiently. They can reduce waste by donating it for public goodwill and potentially benefit from selling it at discounted prices.

**Consumers:** Consumers are looking for notifications from the platform about discounted food availability. By purchasing discounted food, they contribute to reducing food waste.

**Charitable Organizations:** Charitable organizations seek access to surplus food to reduce waste and distribute it to those in need.

**Developers:** Developers are responsible for ensuring the platform operates smoothly and efficiently.

**Project Managers:** Project managers ensure that the platform meets the needs of all stakeholders and maintains a positive impact on reducing food waste and enhancing community welfare.

----------------------

### Scope
#### In Scope
- User Registration and Authentication: Implementation of secure registration and login processes for consumers, retailers, and charitable organizations.
- Inventory Management for Retailers: Features allowing retailers to add, update, and manage their food inventory, including setting expiration dates and identifying surplus items.
- Surplus Food Listing and Management: Functionality for retailers to list surplus food items for donation or sale at a discounted price. Mechanisms for charitable organizations to claim surplus food for distribution.
- Consumer Purchases of Discounted Food: Enabling consumers to browse, select, and "purchase" discounted food items listed by retailers.
- Surplus Food Alerts Subscription: Allowing users to subscribe to alerts for surplus food based on their preferences and location.
- Simulated Payment: Enable consumers to use their preferred payment methods, including check, credit card, PayPal, and Apple Pay, to make simulated payments for their orders.
- Database Design and Management: Development of a relational database schema to support user data, inventory management, transactions, and suscriptions.

#### Out of Scope
- Real Money Transactions: The platform will simulate transactions for study purposes and will not handle real money or financial transactions.
- Third-Party Logistics Integration: The platform will not integrate with third-party logistics providers for the delivery of food items. Logistics arrangements are to be handled externally by the parties involved.
- Comprehensive User Profile Customization: While users can set basic preferences for surplus food alerts, extensive customization of user profiles and interfaces is out of scope.
- Internationalization and Localization: The initial version of the platform will be developed for a specific region or language and will not include multi-language support or localization features.

By defining what is in and out of scope, this document aims to clarify the project boundaries, ensuring that stakeholders have a clear understanding of the expected functionalities and limitations of the Food Waste Reduction Platform.

-------------------------------

### Application Architecture
This Food Wate Platform uses the 3-Tier Architecture model, a common methodology in designing a scalable application.

**Presentation Layer**
- Web-based interface for users to interact with the platform.
- Separate views for Retailers, Consumers, and Charitable Organizations.
- User interface elements for user registration, login/logout, browsing food items, and subscribing to surplus food alerts.
- Views for displaying food list interfaces for retailers, claiming food interfaces for charitable organizations, and purchasing interfaces for consumers.

**Business Layer**
- Contains the core business logic and functionalities of the application.
- Handles processing of user requests and interacts between different components.
- Implements various design patterns for scalability, maintainability, and extensibility.
- Contains: User registration and authentication services. Inventory management services for retailers, including adding new items, updating quantities, and identifying surplus food items. Claiming food services for charitable organizations. Purchasing services for consumers. Surplus food alert services for notifying subscribed users about surplus food items.

**Database Layer**
- Manages persistent storage and retrieval of data.
- Implements CRUD operations for all entities.
- Ensures data integrity and security.

<img width="407" alt="image" src="https://github.com/user-attachments/assets/e9b90c69-3e75-4f3f-ada8-6585a55d50bd">

----------------------------------

### Business Architecture
#### Use Case 1: User Registration
<img width="354" alt="image" src="https://github.com/user-attachments/assets/e4788caa-b197-4cfb-a28a-c6ee61591cd9"><br>
Enable users to create accounts and login/logout.

#### Use Case 2: Retailers
<img width="278" alt="image" src="https://github.com/user-attachments/assets/ae052a70-9b5e-4592-9a0c-c5ba4e2c4996"><br>
Enable retailers to: 
-	Manage inventory, including adding new items, updating quantities, and setting expiration dates for each item.
-	Identify and flag surplus food items that are nearing expiration.
-	List surplus food items on the platform for donation or sale at a reduced price.

#### Use Case 3: Charitable organizations
<img width="283" alt="image" src="https://github.com/user-attachments/assets/e442eaeb-4d58-496d-a4d6-0f6567c05be8"><br>
Enable charitable organizations to:
-	Claim the food items listed by retailers available for donation.
-	Update the inventory after a successful claim of surplus food items.
-	View the claimed orders list.

#### Use Case 4: Consumers
<img width="286" alt="image" src="https://github.com/user-attachments/assets/5c111d1e-31b8-48e4-ad35-17759f5fce66"><br>
Enable consumers to:
-	Purchase the surplus food items listed by the retailers at a given reduced price.
-	Update the inventory after a successful purchase of surplus food items.
-	View the purchased orders list.

#### Use Case 5: Surplus Food Alert
<img width="352" alt="image" src="https://github.com/user-attachments/assets/e3ff97f9-0648-4f67-8b61-a89f3f96cbc4"><br>
Enable users to:
-	Subscribe to receive surplus food alerts based on their location, communication method (email or phone) and food preferences.
-	Receive automatic notifications via email or sms when their preferred retailers list surplus food items on the platform.

#### Use Case 6: Bonus Function - Make Payment
<img width="271" alt="image" src="https://github.com/user-attachments/assets/197e30ff-e57d-4928-bf3d-a4d29db9bb11"><br>
Allow consumers to choose their preferred method of payment and pay for their order.

-------------------------

### Detailed Design 
**The UML Class Diagram**<br>
![image](https://github.com/user-attachments/assets/d50c9453-6f6c-45df-8a4e-ab959523c418)

----------------------

### Data Architecture
**The Entity-Relationship Diagram (ERD)**<br>
![image](https://github.com/user-attachments/assets/e093b9c6-d144-4983-b0ee-1a5b76160885)

----------------------

### Security Architecture
**Authentication and Authorization**
   - Authentication: Ensure that only registered users can access the platform and perform operations.
   - Authorization: Control user access to different functionalities based on their roles (retailers, consumers, charitable organizations).

**Data Protection**
   - Encryption: Encrypt sensitive data stored in the database.
   - Data Masking: Mask sensitive data fields (e.g., passwords) to ensure they are not exposed in logs or unauthorized views.
   - Data Integrity: Ensure data integrity and prevent tampering.

**Access Control**
   - Ensure users have the minimum level of access necessary to perform their tasks.
   - Implement secure session management practices to manage sessions appropriately.

**Database Security**
   - SQL Injection Prevention: Use prepared statements and queries to prevent SQL injection attacks.
   - Database Hardening: Apply practices such as regularly updating and patching database software, restricting database access to authorized users, and monitoring and auditing database activity to harden the database.

**Application Security**
   - Input Validation: Validate user inputs to prevent common vulnerabilities.
   - Error Handling: Implement proper error handling to avoid exposing sensitive information through error messages.
   - Security Patches: Regularly update and patch the application to address security vulnerabilities.

**Compliance**
   - Ensure the platform complies with relevant laws and regulations.
  
-----------------------------

### Deployment Architecture
The deployment architecture involves several stages, from code development on a user's computer to deployment on an Apache Tomcat server, with potential further deployment or usage on a workstation or cloud services like AWS or Azure.

**1. User Computer to Local Repository**<br>
Developer's workstation with necessary development tools (IDE, Git, etc.). Developers write code and commit changes to a local Git repository.

**2. Local Repository to GitHub**<br>
GitHub's cloud-based service for version control using Git. Developers push changes from their local repository to a remote repository on GitHub, triggering potential actions like code reviews or automated tests.

**3. Create Workflow on GitHub**<br>
GitHub Actions for automation. A workflow is defined in the `.github/workflows` directory of the GitHub repository. This workflow specifies actions that run tests, build the project, and deploy the application.

**4. Build on Apache Tomcat Server**<br>
A server running Apache Tomcat, which can be a virtual machine or a physical server, located on-premises or in the cloud. The GitHub Actions workflow includes steps to build the project (e.g., using Maven or Gradle for Java projects) and deploy the built artifacts (e.g., WAR files) to the Tomcat server. This might involve SSH to transfer files and commands to restart the Tomcat service.

-------------------------------

### Testing Model
We will use JUnit to test our Java program since it provides a simple and effective way to write and run automated tests.

The classes in our program that have been tested are: 
- Login class
- Signup class
- FoodItem class
- Inventory class
- Subscription service class
- Payment class

-----------------------------

### Acronyms/Abbreviation
**AWS:** Amazon Web Services<br>
**CLI:** Command Line Interface<br>
**CRUD:** Create, Read, Update, Delete<br>
**DAO:** Data Access Object<br>
**ERD:** Entity-Relationship Diagram<br>
**IDE:** Integrated Development Environment<br>
**JSP:** JavaServer Pages<br>
**SMTP:** Simple Mail Transfer Protocol<br>
**SRP:** Single Responsibility Principle<br>
**SQL:** Structured Query Language<br>
**SSH:** Secure Shell

---------------------------------

### References
SRP code example: https://www.javabrahman.com/programming-principles/single-responsibility-principle-with-example-in-java/

Sending Email in Java: https://javatpoint.com/example-of-sending-email-using-java-mail-api

Auto-generated key: https://stackoverflow.com/questions/14844864/jdbc-preparedstatement-always-returns-1-as-auto-generated-key

Java Mail Example - Send Mail in Java using SMTP:  https://www.digitalocean.com/community/tutorials/javamail-example-send-mail-in-java-smtp

JSP  Tutorial: https://www.javabrahman.com/programming-principles/single-responsibility-principle-with-example-in-java/
