/**
 * Student Name: Ryley Carlson
 * CSC320 Module 8 Portfolio Assignment - Option #2
 * Program: Home Inventory Management System
 * Description: This program defines a Home class to represent individual homes with attributes such as
 * square footage, address, city, state, zip code, model name, and sale status.
 * The HomeInventory class manages a collection of Home objects, allowing users to add, remove, list, and update home information.
 * The program also includes functionality to print the inventory to a file and handles user interaction through the console.
 * All methods include try-catch blocks to ensure robust error handling and provide feedback on success or failure of operations.
 */

import java.util.*;
import java.io.*;

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

/**
 * Part 2: Main HomeInventory Class
 */
public class HomeInventory {
    private static ArrayList<Home> inventory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // 1. Call Home class with parameterized constructor
            System.out.println("--- Initializing Home ---");
            Home initialHome = new Home(2500, "851 Coconut St", "Boston", "MA", 02108, "The Boston", "Is for sale");
            inventory.add(initialHome);

            // 2. Call list method (Loop through array and print)
            listInventory();

            // 3. Call remove home method
            System.out.println("\n--- Removing Home ---");
            System.out.println(removeHome(0)); // Returns and prints success/failure

            // 4. Add a new home
            System.out.println("\n--- Adding New Home ---");
            Home newHome = new Home(3200, "1365 Beaver Ave", "Darlington Township", "PA", 16115, "The Darlington", "Is under contract");
            System.out.println(addHome(newHome)); // Returns and prints success/failure

            // 5. Call list method and print new home info
            listInventory();

            // 6. Update the home (change sale status)
            System.out.println("\n--- Updating Status ---");
            if (!inventory.isEmpty()) {
                System.out.println(inventory.get(0).updateSaleStatus("sold"));
            }

            // 7. Call custom printing method for specific home information
            System.out.println("\n--- Printing Specific Home Information ---");
            if (!inventory.isEmpty()) {
                printHomeDetails(inventory.get(0));
            }

            // 8. File Print Logic
            handleFileOutput();

            // Logic to keep program open or close
            promptToStayOpen();

        } catch (Exception e) {
            // Requirement: Include try..catch and print to console
            System.out.println("A main application error has occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // --- Required Methods with Try..Catch and Return Messages ---

    /**
     * Prints details for a specific home object.
     * This fulfills the request to have a specific printing method in the main class.
     */
    public static void printHomeDetails(Home h) {
        try {
            if (h != null) {
                System.out.println("DATA: " + h.getHomeListing());
            } else {
                System.out.println("FAILURE: Home object is null.");
            }
        } catch (Exception e) {
            System.out.println("Error printing home details: " + e.getMessage());
        }
    }

    public static String addHome(Home h) {
        try {
            inventory.add(h);
            return "SUCCESS: The Home has been added to the inventory.";
        } catch (Exception e) {
            return "FAILURE: Sorry, Your attempt to add a home failed. " + e.getMessage();
        }
    }

    public static String removeHome(int index) {
        try {
            inventory.remove(index);
            return "SUCCESS: The Home at index " + index + " has been removed/variables cleared successfully!";
        } catch (Exception e) {
            return "FAILURE: Sorry, Your attempt to remove a home has failed. " + e.getMessage();
        }
    }

    public static void listInventory() {
        System.out.println("\nCURRENT HOME LISTING:");
        try {
            if (inventory.isEmpty()) {
                System.out.println("Inventory is empty.");
            } else {
                for (Home h : inventory) {
                    // Reusing the printHomeDetails method here for consistency
                    printHomeDetails(h);
                }
            }
        } catch (Exception e) {
            System.out.println("Error listing inventory: " + e.getMessage());
        }
    }

    // Method to handle file output logic
    private static void handleFileOutput() {
        System.out.print("\nDo you want to print the information to a file? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();

        if (response.equals("Y")) {
            printToFile("C:\\Temp\\Home.txt");
        } else {
            System.out.println("File output skipped. Program will continue without printing to file.");
        }
    }

    public static void printToFile(String path) {
        try {
            File dir = new File("C:\\Temp");
            if (!dir.exists()) dir.mkdirs();

            try (FileWriter writer = new FileWriter(path)) {
                writer.write("HOME INVENTORY REPORT\n=====================\n");
                for (Home h : inventory) {
                    writer.write(h.getHomeListing() + "\n");
                }
                System.out.println("SUCCESS: File has been printed to " + path);
            }
        } catch (IOException e) {
            System.out.println("FAILURE: Error writing to file. " + e.getMessage());
        }
    }

    private static void promptToStayOpen() {
        System.out.print("\nWould you like to keep the program open? (Y/N): ");
        String stay = scanner.nextLine().trim().toUpperCase();
        if (stay.equals("Y")) {
            System.out.println("System remains open for further commands (Simulation).");
        } else {
            System.out.println("Closing application...");
        }
    }
}
