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
            new Product("R001", "Шуруповерт", "DCD771", 50, "DeWalt", 3500.0, "2023-05-10"),
            new Product("R002", "Дриль", "GSB 13 RE", 30, "Bosch", 2100.50, "2023-08-21"),
            new Product("R003", "Шуруповерт", "GSR 120-LI", 45, "Bosch", 3200.0, "2024-01-15"),
            new Product("R004", "Болгарка", "GA5030", 25, "Makita", 1850.0, "2023-11-05"),
            new Product("R005", "Перфоратор", "GBH 2-26", 20, "Bosch", 5400.0, "2024-02-20")
        };

        System.out.println("Вихідні дані (Склад промислових товарів):");
        printFullTable(products);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nОберіть дію для пошуку:");
            System.out.println("1 - Знайти товари заданої фірми виробника");
            System.out.println("2 - Знайти фірми, ціни та кількість для заданого товару");
            System.out.println("0 - Вихід з програми");
            System.out.print("Ваш вибір: ");

            int choice = -1;
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("\nПОМИЛКА: Невірний тип даних. Будь ласка, введіть ціле число.");
                scanner.nextLine(); 
                continue; 
            }

            switch (choice) {
                case 1:
                    System.out.print("Введіть назву фірми виробника (наприклад, Bosch): ");
                    String searchManufacturer = scanner.nextLine();
                    searchByManufacturer(products, searchManufacturer);
                    break;
                case 2:
                    System.out.print("Введіть назву товару (наприклад, Шуруповерт): ");
                    String searchName = scanner.nextLine();
                    searchByProductName(products, searchName);
                    break;
                case 0:
                    running = false;
                    System.out.println("Роботу програми завершено.");
                    break;
                default:
                    System.out.println("\nПОМИЛКА: Невірний вибір. Оберіть 1, 2 або 0.");
            }
        }
        scanner.close();
    }

    private static void printHeader() {
        printSeparator();
        System.out.printf("| %-10s | %-15s | %-10s | %-8s | %-15s | %-8s | %-12s |\n",
                "Реєстр. №", "Назва", "Модель", "Кільк.", "Виробник", "Ціна", "Дата виг.");
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
        System.out.println("\nРезультати пошуку для виробника: " + manufacturer);
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
            System.out.println("Дані за заданим критерієм пошуку відсутні.");
        }
    }

    private static void searchByProductName(Product[] products, String name) {
        System.out.println("\nРезультати пошуку для товару: " + name);
        System.out.println("---------------------------------------------------------");
        System.out.printf("| %-15s | %-8s | %-8s |\n", "Виробник", "Ціна", "Кількість");
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
            System.out.println("Дані за заданим критерієм пошуку відсутні.");
        }
    }
}

