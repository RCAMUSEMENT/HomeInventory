/**
 * Student Name: Ryley Carlson
 * CSC320 Module 8 Portfolio Assignment - Option #2
 * Program: Home Inventory Management System
 * Description: This program defines a Home class to represent individual homes with attributes such as
 * square footage, address, city, state, zip code, model name, and sale status.
 * The HomeInventory class manages a collection of Home objects, allowing users to add, remove, list, and update home information.
 *  The program also includes functionality to print the inventory to a file and handles user interaction through the console.
 *  All methods include try-catch blocks to ensure robust error handling and provide feedback on success or failure of operations.
 */
/**
 * Part 1: Home Class with Required Attributes and Methods
 */

class Home {
    // Required Private Attributes
    private int square_feet;
    private String address;
    private String city;
    private String state;
    private int zip_code;
    private String Model_name;
    private String sale_status;

    // Parameterized Constructor with Try..Catch
    public Home(int square_feet, String address, String city, String state, int zip_code, String Model_name, String sale_status) {
        try {
            this.square_feet = square_feet;
            this.address = address;
            this.city = city;
            this.state = state;
            this.zip_code = zip_code;
            this.Model_name = Model_name;
            this.sale_status = sale_status;
        } catch (Exception e) {
            System.out.println("Error in Constructor: " + e.getMessage());
        }
    }

    // Method to update home attributes - Returns Success/Failure
    public String updateHomeAttributes(int sqft, String addr, String city, String state, int zip, String model, String status) {
        try {
            this.square_feet = sqft;
            this.address = addr;
            this.city = city;
            this.state = state;
            this.zip_code = zip;
            this.Model_name = model;
            this.sale_status = status;
            return "SUCCESS: The Home's attributes have been updated.";
        } catch (Exception e) {
            return "FAILURE: Sorry, Could not update the attributes. " + e.getMessage();
        }
    }

    // Specific method to update status - Returns Success/Failure
    public String updateSaleStatus(String newStatus) {
        try {
            this.sale_status = newStatus;
            return "SUCCESS: Sale status updated to " + newStatus;
        } catch (Exception e) {
            return "FAILURE: Status update failed. " + e.getMessage();
        }
    }

    // Helper method to return home listing information as a formatted string
    public String getHomeListing() {
        try {
            return String.format("Model: %s | Status: %s | Address: %s, %s, %s %d | Size: %d sqft",
                    Model_name, sale_status, address, city, state, zip_code, square_feet);
        } catch (Exception e) {
            return "Error in retrieving the listing: " + e.getMessage();
        }
    }
}
