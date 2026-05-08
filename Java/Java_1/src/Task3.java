import java.util.InputMismatchException;
import java.util.Scanner;

class Product {
    private String regNumber;
    private String name;
    private String model;
    private int quantity;
    private String manufacturer;
    private double price;
    private String date;

    public Product(String regNumber, String name, String model, int quantity, String manufacturer, double price, String date) {
        this.regNumber = regNumber;
        this.name = name;
        this.model = model;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.price = price;
        this.date = date;
    }

    public String getName() { return name; }
    public String getManufacturer() { return manufacturer; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void printRow() {
        System.out.printf("| %-10s | %-15s | %-10s | %-8d | %-15s | %-8.2f | %-12s |\n",
                regNumber, name, model, quantity, manufacturer, price, date);
    }
}

public class Task3 {
    public static void main(String[] args) {
        Product[] products = {
            new Product("R001", "Screwdriver", "DCD771", 50, "DeWalt", 3500.0, "2023-05-10"),
            new Product("R002", "Drill", "GSB 13 RE", 30, "Bosch", 2100.50, "2023-08-21"),
            new Product("R003", "Screwdriver", "GSR 120-LI", 45, "Bosch", 3200.0, "2024-01-15"),
            new Product("R004", "Grinder", "GA5030", 25, "Makita", 1850.0, "2023-11-05"),
            new Product("R005", "Rotary Hammer", "GBH 2-26", 20, "Bosch", 5400.0, "2024-02-20")
        };

        System.out.println("Initial Data (Industrial Goods Warehouse):");
        printFullTable(products);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nSelect an action:");
            System.out.println("1 - Find products by manufacturer");
            System.out.println("2 - Find manufacturer, price, and quantity for a specific product");
            System.out.println("0 - Exit program");
            System.out.print("Your choice: ");

            int choice = -1;
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Invalid data type. Please enter an integer.");
                scanner.nextLine(); 
                continue; 
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter manufacturer name (e.g., Bosch): ");
                    String searchManufacturer = scanner.nextLine();
                    searchByManufacturer(products, searchManufacturer);
                    break;
                case 2:
                    System.out.print("Enter product name (e.g., Screwdriver): ");
                    String searchName = scanner.nextLine();
                    searchByProductName(products, searchName);
                    break;
                case 0:
                    running = false;
                    System.out.println("Program terminated.");
                    break;
                default:
                    System.out.println("\nERROR: Invalid choice. Select 1, 2, or 0.");
            }
        }
        scanner.close();
    }

    private static void printHeader() {
        printSeparator();
        System.out.printf("| %-10s | %-15s | %-10s | %-8s | %-15s | %-8s | %-12s |\n",
                "Reg. No.", "Name", "Model", "Quantity", "Manufacturer", "Price", "Mfg. Date");
        printSeparator();
    }

    private static void printSeparator() {
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    private static void printFullTable(Product[] products) {
        printHeader();
        for (Product p : products) {
            p.printRow();
        }
        printSeparator();
    }

    private static void searchByManufacturer(Product[] products, String manufacturer) {
        System.out.println("\nSearch results for manufacturer: " + manufacturer);
        printHeader();
        boolean found = false;
        for (Product p : products) {
            if (p.getManufacturer().equalsIgnoreCase(manufacturer)) {
                p.printRow();
                found = true;
            }
        }
        printSeparator();
        if (!found) {
            System.out.println("No data found for the given search criteria.");
        }
    }

    private static void searchByProductName(Product[] products, String name) {
        System.out.println("\nSearch results for product: " + name);
        System.out.println("---------------------------------------------------------");
        System.out.printf("| %-15s | %-8s | %-8s |\n", "Manufacturer", "Price", "Quantity");
        System.out.println("---------------------------------------------------------");
        boolean found = false;
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.printf("| %-15s | %-8.2f | %-8d |\n", p.getManufacturer(), p.getPrice(), p.getQuantity());
                found = true;
            }
        }
        System.out.println("---------------------------------------------------------");
        if (!found) {
            System.out.println("No data found for the given search criteria.");
        }
    }
}

