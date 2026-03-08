import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Part 2: HomeInventory Class with Required Methods and User Interaction
 */

public class HomeInventory {
    private static final ArrayList<Home> inventory = new ArrayList<>();

    public static void main(String[] args) {
        // TRY..CATCH BLOCK FOR MAIN APPLICATION LOGIC
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Initializing Home ---");
            Home initialHome = new Home(2500, "851 Coconut St", "Boston", "MA", 2108, "The Boston", "available");
            inventory.add(initialHome);

            boolean keepGoing = true;
            while (keepGoing) {
                System.out.println("\n--- HOME INVENTORY MENU ---");
                System.out.println("1. Add a new home");
                System.out.println("2. Remove a home");
                System.out.println("3. View current listing");
                System.out.println("4. Update status");
                System.out.println("5. Exit program");
                System.out.print("Select an option: ");
                
                String choice = scanner.nextLine();

                // SWITCH CASE FOR USER CHOICE
                switch (choice) {
                    case "1" -> {
                        System.out.println("\n--- Adding New Home (Enter Details) ---");
                        Home userHome = promptForHome(scanner);
                        System.out.println(addHome(userHome));
                    }
                    case "2" -> {
                        System.out.println("\n--- Removing Home ---");
                        if (inventory.isEmpty()) {
                            System.out.println("Inventory is empty. There is nothing to remove.");
                        } else {
                            System.out.print("Enter index to remove: ");
                            try {
                                int idx = Integer.parseInt(scanner.nextLine());
                                // CONFIRMATION FAILSAFE
                                System.out.print("Are you sure you really want to remove home [" + idx + "]? (Y/N): ");
                                if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
                                    System.out.println(removeHome(idx));
                                } else {
                                    System.out.println("Removal cancelled.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }
                    }
                    case "3" -> listInventory();
                    case "4" -> {
                        System.out.println("\n--- Updating Status ---");
                        updateStatusWorkflow(scanner);
                    }
                    case "5" -> keepGoing = false;
                    default -> System.out.println("Invalid choice.");
                }

                if (keepGoing) {
                    System.out.print("\nWould you like to add, remove, or view another home? (Y/N): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("N")) {
                        keepGoing = false;
                    }
                }
            }

            handleFileOutput(scanner);

        } catch (Exception e) {
            System.out.println("A main application error has occurred: " + e.getMessage());
        }
    }

    private static Home promptForHome(Scanner scanner) {
        int sqft = -1;
        while (sqft < 0) {
            try {
                System.out.print("Enter square feet: ");
                sqft = Integer.parseInt(scanner.nextLine());
                if (sqft < 0) System.out.println("Square feet cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for square feet.");
            }
        }

        System.out.print("Enter address: ");
        String addr = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        String state = "";
        while (state.length() != 2) {
            System.out.print("Enter state (2 character abbreviation): ");
            state = scanner.nextLine().trim().toUpperCase();
            if (state.length() != 2) System.out.println("INVALID STATE. Please use the 2-letter format (e.g., MA).");
        }

        int zip = -1;
        while (zip < 0) {
            try {
                System.out.print("Enter zip code: ");
                zip = Integer.parseInt(scanner.nextLine());
                if (zip < 0) System.out.println("Zip code cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for zip code.");
            }
        }

        System.out.print("Enter model name: ");
        String model = scanner.nextLine();
        String status = promptForStatus(scanner);

        return new Home(sqft, addr, city, state, zip, model, status);
    }

    private static String promptForStatus(Scanner scanner) {
        while (true) {
            System.out.print("Enter sale status (sold, available, or under contract): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("sold") || input.equals("available") || input.equals("under contract")) {
                return input;
            }
            System.out.println("INVALID STATUS. Please choose from the three options.");
        }
    }

    private static void updateStatusWorkflow(Scanner scanner) {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.print("Enter home index to update: ");
            try {
                int idx = Integer.parseInt(scanner.nextLine());
                String newStatus = promptForStatus(scanner);
                System.out.println(inventory.get(idx).updateSaleStatus(newStatus));
            } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
                System.out.println("Error updating status: " + e.getMessage());
            }
        }
    }

    private static String addHome(Home h) {
        try {
            inventory.add(h);
            return "SUCCESS: The Home has been added to the inventory.";
        } catch (NullPointerException | IllegalArgumentException | ClassCastException e) {
            return "FAILURE: Sorry, Your attempt to add a home failed. " + e.getMessage();
        }
    }

    public static String removeHome(int index) {
        try {
            inventory.remove(index);
            return "SUCCESS: The Home at index " + index + " has been removed/variables cleared successfully!";
        } catch (IndexOutOfBoundsException | UnsupportedOperationException e) {
            return "FAILURE: Sorry, Your attempt to remove a home has failed. " + e.getMessage();
        }
    }

    public static void listInventory() {
        System.out.println("\nCURRENT HOME LISTING:");
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        try {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println("[" + i + "] " + inventory.get(i).getHomeListing());
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Error listing inventory: " + e.getMessage());
        }
    }

    private static void handleFileOutput(Scanner scanner) {
        System.out.print("\nDo you want to print the information to a file? (Y/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
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
            System.out.println("SUCCESS: File has been printed to " + path);
        } catch (IOException e) {
            System.out.println("FAILURE: Error writing to file. " + e.getMessage());
        }
    }
}

