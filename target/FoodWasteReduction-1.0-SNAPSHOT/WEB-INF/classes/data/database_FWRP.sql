DROP DATABASE IF EXISTS FWRP;
CREATE DATABASE FWRP;
USE FWRP;

-- Create User Table
-- Use City as Location for User Subscription
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    UserType ENUM('Retailer', 'Consumer', 'CharitableOrganization') NOT NULL,
    PhoneNumber VARCHAR(20),
    Address VARCHAR(100),
	City VARCHAR(50),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create FoodItems
CREATE TABLE FoodItems (
    FoodItemID INT AUTO_INCREMENT PRIMARY KEY,
    RetailerID INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(255),
    Category ENUM('Dairy', 'Egg', 'Beef', 'Pork', 'Poultry', 'Fruit', 'Vegetable', 'Seafood', 'Grain', 'Nut', 'Legume', 'Bakery', 'Beverage', 'Snack', 'Condiment', 'Other') NOT NULL,
    Brand VARCHAR(100),
    Unit VARCHAR(20),
    FOREIGN KEY (RetailerID) REFERENCES Users(UserID)
);

-- Create Inventory
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
    ListedForDonation BOOLEAN DEFAULT FALSE,
    LastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    IsActive BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (RetailerID) REFERENCES Users(UserID),
    FOREIGN KEY (FoodItemID) REFERENCES FoodItems(FoodItemID),
    UNIQUE KEY (RetailerID, FoodItemID, BatchNumber)
);

-- Create Transactions
CREATE TABLE Transactions (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,
    InventoryID INT NOT NULL,
    UserID INT NOT NULL,
    Quantity INT NOT NULL,
    TransactionType ENUM('Purchase', 'Donation') NOT NULL,
    TransactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (InventoryID) REFERENCES Inventory(InventoryID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


-- Create Subscriptions
CREATE TABLE Subscriptions (
    SubscriptionID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    RetailerID INT NOT NULL,
    CommunicationPreference ENUM('Email', 'Phone') NOT NULL,
    FoodPreferences SET('Dairy', 'Egg', 'Beef', 'Pork', 'Poultry', 'Fruit', 'Vegetable', 'Seafood', 'Grain', 'Nut', 'Legume', 'Bakery', 'Beverage', 'Snack', 'Condiment', 'Other') NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (RetailerID) REFERENCES Users(UserID)
);


-- Create Notifications
CREATE TABLE Notifications (
    NotificationID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    InventoryID INT NOT NULL,
    NotificationType ENUM('SurplusAlert', 'Other') NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (InventoryID) REFERENCES Inventory(InventoryID)
);
