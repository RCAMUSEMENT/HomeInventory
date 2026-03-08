import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Student Name: Ryley Carlson
 * Part 2: Main HomeInventory Class
 * Refactored: Try-with-resources Scanner and Multi-catch Exception Handling
 */
public class HomeInventory {
    private static final ArrayList<Home> inventory = new ArrayList<>();

    public static void main(String[] args) {
        // 1. Try-with-resources: Automatically closes the scanner and System.in
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Initializing Home ---");
            Home initialHome = new Home(2500, "851 Coconut St", "Boston", "MA", 2108, "The Boston", "available");
            inventory.add(initialHome);

            listInventory();

            System.out.println("\n--- Removing Home ---");
            System.out.println(removeHome(0)); 

            System.out.println("\n--- Adding New Home (Enter Details) ---");
            Home userHome = promptForHome(scanner);
            System.out.println(addHome(userHome)); 

            listInventory();

            System.out.println("\n--- Updating Status ---");
            if (!inventory.isEmpty()) {
                String newStatus = promptForStatus(scanner);
                System.out.println(inventory.get(0).updateSaleStatus(newStatus));
            }

            listInventory();
            handleFileOutput(scanner);

        } catch (Exception e) {
            System.out.println("A main application error has occurred: " + e.getMessage());
        }
    }

    private static Home promptForHome(Scanner scanner) {
        try {
            System.out.print("Enter square feet: ");
            int sqft = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter address: ");
            String addr = scanner.nextLine();
            System.out.print("Enter city: ");
            String city = scanner.nextLine();
            System.out.print("Enter state: ");
            String state = scanner.nextLine();
            System.out.print("Enter zip code: ");
            int zip = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter model name: ");
            String model = scanner.nextLine();
            
            String status = promptForStatus(scanner);
            
            return new Home(sqft, addr, city, state, zip, model, status);
        } catch (NumberFormatException e) {
            System.out.println("Numeric input error. Creating a placeholder home.");
            return new Home(0, "Invalid", "Invalid", "XX", 0, "ErrorModel", "available");
        }
    }

    private static String promptForStatus(Scanner scanner) {
        String input;
        while (true) {
            System.out.print("Enter sale status (sold, available, or under contract): ");
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("sold") || input.equals("available") || input.equals("under contract")) {
                return input;
            }
            System.out.println("INVALID STATUS. Please choose from the three options.");
        }
    }

    private static String addHome(Home h) {
        try {
            inventory.add(h);
            return "SUCCESS: The Home has been added to the inventory.";
        }
        // 2. Multi-catch: Handling specific exceptions related to list operations
        catch (NullPointerException | IllegalArgumentException | ClassCastException e) {
            return "FAILURE: Data integrity issue. Could not add home: " + e.getMessage();
        } catch (Exception e) {
            return "FAILURE: An unexpected error occurred: " + e.getMessage();
        }
    }

    public static String removeHome(int index) {
        try {
            inventory.remove(index);
            return "SUCCESS: The Home at index " + index + " has been removed.";
        } catch (IndexOutOfBoundsException e) {
            return "FAILURE: No home exists at that index.";
        } catch (Exception e) {
            return "FAILURE: Error removing home: " + e.getMessage();
        }
    }

    public static void listInventory() {
        System.out.println("\nCURRENT HOME LISTING:");
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Home h : inventory) {
                System.out.println(h.getHomeListing());
            }
        }
    }

    private static void handleFileOutput(Scanner scanner) {
        System.out.print("\nDo you want to print the information to a file? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();

        if (response.equals("Y")) {
            printToFile("C:\\Temp\\Home.txt");
        } else {
            System.out.println("A file will not be printed.");
        }
    }

    public static void printToFile(String path) {
        File dir = new File("C:\\Temp");
        if (!dir.exists()) dir.mkdirs();

        try (FileWriter writer = new FileWriter(path)) {
            writer.write("HOME INVENTORY REPORT\n=====================\n");
            for (Home h : inventory) {
                writer.write(h.getHomeListing() + "\n");
            }
            System.out.println("SUCCESS: File printed to " + path);
        } catch (IOException e) {
            System.out.println("FAILURE: Error writing to file. " + e.getMessage());
        }
    }
}

