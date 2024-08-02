-- Create the database
DROP DATABASE IF EXISTS FWRP;
CREATE DATABASE FWRP;
USE FWRP;

-- Create database user for this group assignment
-- CREATE USER 'CST8288Group'@'localhost' IDENTIFIED BY '12345678';
-- GRANT ALL PRIVILEGES ON FWRP.* TO 'CST8288Group'@'localhost';
-- FLUSH PRIVILEGES;

-- Create User Table
-- Use City as Location for User Subscription
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    UserType ENUM('Retailer', 'Consumer', 'CHARITABLE_ORGANIZATION') NOT NULL,
    PhoneNumber VARCHAR(20),
    Address VARCHAR(100),
    City VARCHAR(50),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE FoodItems (
    FoodItemID INT AUTO_INCREMENT PRIMARY KEY,
    RetailerID INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(255),
    Category ENUM('Dairy', 'Egg', 'Beef', 'Pork', 'Poultry', 'Fruit', 'Vegetable', 'Seafood', 'Grain', 'Nut', 'Legume', 'Bakery', 'Beverage', 'Snack', 'Condiment', 'Other') NOT NULL,
    Brand VARCHAR(100),
    Unit VARCHAR(20),
    FOREIGN KEY (RetailerID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Inventory (
    InventoryID INT AUTO_INCREMENT PRIMARY KEY,
    RetailerID INT NOT NULL,
    FoodItemID INT NOT NULL,
    BatchNumber VARCHAR(50),
    Quantity INT NOT NULL,
    RegularPrice DECIMAL(10, 2),
    DiscountRate DECIMAL(5, 2),
    ExpirationDate DATE NOT NULL,
    ReceiveDate DATE NOT NULL,
    IsSurplus BOOLEAN DEFAULT FALSE,
    SurplusStatus ENUM('None', 'Donation', 'Discount') DEFAULT 'None',
    LastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    IsActive BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (RetailerID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (FoodItemID) REFERENCES FoodItems(FoodItemID) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE KEY (RetailerID, FoodItemID, BatchNumber)
);

CREATE TABLE Transactions (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,
    InventoryID INT NOT NULL,
    UserID INT NOT NULL,
    Quantity INT NOT NULL,
    TransactionType ENUM('Purchase', 'Donation') NOT NULL,
    TransactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (InventoryID) REFERENCES Inventory(InventoryID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Subscriptions (
    SubscriptionID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    RetailerID INT NOT NULL,
    CommunicationPreference ENUM('Email', 'Phone') NOT NULL,
    FoodPreferences SET('Dairy', 'Egg', 'Beef', 'Pork', 'Poultry', 'Fruit', 'Vegetable', 'Seafood', 'Grain', 'Nut', 'Legume', 'Bakery', 'Beverage', 'Snack', 'Condiment', 'Other') NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (RetailerID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Notifications (
    NotificationID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    InventoryID INT NOT NULL,
    NotificationType ENUM('SurplusAlert', 'Other') NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (InventoryID) REFERENCES Inventory(InventoryID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    TransactionID INT NOT NULL,
    PaymentType ENUM('CHECK', 'CREDIT_CARD', 'APPLE_PAY', 'PAYPAL') NOT NULL,
    Amount DECIMAL(10, 2),
    Credential JSON,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (TransactionID) REFERENCES Transactions(TransactionID) ON UPDATE CASCADE ON DELETE CASCADE
);
-- ---------Test data----
-- Test data for Users
-- INSERT INTO Users (Name, Email, PasswordHash, UserType, PhoneNumber, Address, City) VALUES
-- ('Costo', 'john.doe@example.com', 'hashed_password_1', 'Retailer', '555-1234', '123 Elm St', 'Ottawa'),
-- ('Jane Smith', 'jane.smith@example.com', 'hashed_password_2', 'Consumer', '555-5678', '456 Oak St', 'Ottawa'),
-- ('Charity Org', 'contact@charity.org', 'hashed_password_3', 'CharitableOrganization', '555-8765', '789 Pine St', 'Kingston'),
-- ('Walmart', 'alice.johnson@example.com', 'hashed_password_4', 'Retailer', '555-4321', '321 Maple St', 'Kingston');
-- 
-- -- Test data for FoodItems of the first Retailer
-- INSERT INTO FoodItems (RetailerID, Name, Description, Category, Brand, Unit) VALUES
-- (1, 'Milk', '1L of whole milk', 'Dairy', 'BrandA', 'Litre'),
-- (1, 'Cheddar Cheese', '200g of cheddar cheese', 'Dairy', 'BrandB', 'Gram'),
-- (1, 'Eggs', '12 large eggs', 'Egg', 'BrandC', 'Dozen'),
-- (1, 'Ground Beef', '500g of ground beef', 'Beef', 'BrandD', 'Gram'),
-- (1, 'Pork Chops', '4 pork chops', 'Pork', 'BrandE', 'Piece'),
-- (1, 'Chicken Breast', '2 chicken breasts', 'Poultry', 'BrandF', 'Piece'),
-- (1, 'Apples', '1kg of apples', 'Fruit', 'BrandG', 'Kilogram'),
-- (1, 'Carrots', '500g of carrots', 'Vegetable', 'BrandH', 'Gram');
-- 
-- -- Test data for FoodItems of the second Retailer
-- INSERT INTO FoodItems (RetailerID, Name, Description, Category, Brand, Unit) VALUES
-- (4, 'Whole Wheat Bread', '500g loaf of whole wheat bread', 'Bakery', 'BrandI', 'Gram'),
-- (4, 'Orange Juice', '1L of orange juice', 'Beverage', 'BrandJ', 'Litre'),
-- (4, 'Potato Chips', '200g bag of potato chips', 'Snack', 'BrandK', 'Gram'),
-- (4, 'Olive Oil', '500ml of olive oil', 'Condiment', 'BrandL', 'Millilitre'),
-- (4, 'Frozen Pizza', '1 frozen pizza', 'Other', 'BrandM', 'Piece'),
-- (4, 'Peanut Butter', '250g of peanut butter', 'Nut', 'BrandN', 'Gram'),
-- (4, 'Pasta', '500g of pasta', 'Grain', 'BrandO', 'Gram'),
-- (4, 'Salmon', '200g of fresh salmon', 'Seafood', 'BrandP', 'Gram');
-- 
-- -- Test data for Inventory of the second Retailer
-- INSERT INTO Inventory (RetailerID, FoodItemID, BatchNumber, Quantity, RegularPrice, DiscountRate, ExpirationDate, ReceiveDate, IsSurplus, SurplusStatus) VALUES
-- (1, 1, 'BATCH001', 100, 1.99, 0.10, '2024-09-30', '2024-07-01', FALSE, 'None'),
-- (1, 2, 'BATCH002', 50, 4.99, 0.15, '2024-10-15', '2024-07-02', FALSE, 'None'),
-- (1, 3, 'BATCH003', 200, 2.99, 0.20, '2024-08-20', '2024-07-03', FALSE, 'None'),
-- (1, 4, 'BATCH004', 150, 7.99, 0.05, '2024-09-05', '2024-07-04', FALSE, 'None'),
-- (1, 5, 'BATCH005', 100, 6.49, 0.10, '2024-09-25', '2024-07-05', FALSE, 'None');
-- 
-- -- Test data for Inventory of the second Retailer
-- INSERT INTO Inventory (RetailerID, FoodItemID, BatchNumber, Quantity, RegularPrice, DiscountRate, ExpirationDate, ReceiveDate, IsSurplus, SurplusStatus) VALUES
-- (4, 9, 'BATCH006', 75, 3.99, 0.10, '2024-11-30', '2024-07-06', FALSE, 'None'),
-- (4, 10, 'BATCH007', 100, 2.49, 0.05, '2024-10-20', '2024-07-07', FALSE, 'None'),
-- (4, 11, 'BATCH008', 200, 1.99, 0.15, '2024-08-30', '2024-07-08', FALSE, 'None'),
-- (4, 12, 'BATCH009', 50, 5.49, 0.20, '2024-11-15', '2024-07-09', FALSE, 'None'),
-- (4, 13, 'BATCH010', 120, 8.99, 0.10, '2024-12-05', '2024-07-10', FALSE, 'None');
-- 
-- -- Test data for Subscriptions
-- INSERT INTO Subscriptions (UserID, RetailerID, CommunicationPreference, FoodPreferences) VALUES
-- (2, 1, 'Email', 'Dairy,Fruit,Vegetable');
-- 
-- INSERT INTO Subscriptions (UserID, RetailerID, CommunicationPreference, FoodPreferences) VALUES
-- (3, 4, 'Email', 'Grain,Dairy,Beverage');