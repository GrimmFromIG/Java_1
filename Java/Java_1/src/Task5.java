import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Calendar {
    private String year;
    private List<Day> days;

    public Calendar(String year) {
        this.year = year;
        this.days = new ArrayList<>();
        System.out.println("Calendar for year " + year + " has been created.");
    }

    public class Day {
        private String date;
        private String type;
        private String description;

        public Day(String date, String type, String description) {
            this.date = date;
            this.type = type;
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        @Override
        public String toString() {
            return String.format("Date: %s | Type: %-10s | Description: %s", date, type, description);
        }
    }

    public void addDay(String date, String type, String description) {
        Day newDay = new Day(date, type, description);
        days.add(newDay);
        System.out.println("-> Successfully added: " + newDay.toString());
    }

    public void displayAllDays() {
        System.out.println("\n--- Calendar (" + year + ") ---");
        if (days.isEmpty()) {
            System.out.println("The calendar is empty.");
            return;
        }
        for (Day day : days) {
            System.out.println(day.toString());
        }
        System.out.println("-------------------------");
    }

    public void searchByDate(String searchDate) {
        System.out.println("\n--- Search by date: " + searchDate + " ---");
        boolean found = false;
        for (Day day : days) {
            if (day.getDate().equals(searchDate)) {
                System.out.println("Found: " + day.toString());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No information found for this date.");
        }
    }
}

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the 'Calendar' program!");
        
        Calendar myCalendar = new Calendar("2024");
        
        boolean running = true;
        while (running) {
            System.out.println("\nSelect an action:");
            System.out.println("1 - Add a new day");
            System.out.println("2 - View all records");
            System.out.println("3 - Find a day by date");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    System.out.print("Enter date (e.g., 01.01): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter day type (Holiday/Weekend/Workday): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String desc = scanner.nextLine();
                    myCalendar.addDay(date, type, desc);
                    break;
                case "2":
                    myCalendar.displayAllDays();
                    break;
                case "3":
                    System.out.print("Enter date to search: ");
                    String searchD = scanner.nextLine();
                    myCalendar.searchByDate(searchD);
                    break;
                case "0":
                    running = false;
                    System.out.println("Program terminated.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}