import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Calendar {
    private String year;
    private List<Day> days;

    public Calendar(String year) {
        this.year = year;
        this.days = new ArrayList<>();
        System.out.println("Створено календар на " + year + " рік.");
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
            return String.format("Дата: %s | Тип: %-10s | Опис: %s", date, type, description);
        }
    }

    public void addDay(String date, String type, String description) {
        Day newDay = new Day(date, type, description);
        days.add(newDay);
        System.out.println("-> Успішно додано: " + newDay.toString());
    }

    public void displayAllDays() {
        System.out.println("\n--- Календар (" + year + ") ---");
        if (days.isEmpty()) {
            System.out.println("Календар порожній.");
            return;
        }
        for (Day day : days) {
            System.out.println(day.toString());
        }
        System.out.println("-------------------------");
    }

    public void searchByDate(String searchDate) {
        System.out.println("\n--- Пошук за датою: " + searchDate + " ---");
        boolean found = false;
        for (Day day : days) {
            if (day.getDate().equals(searchDate)) {
                System.out.println("Знайдено: " + day.toString());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Інформації за цією датою не знайдено.");
        }
    }
}

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вітаємо у програмі 'Календар'!");
        
        Calendar myCalendar = new Calendar("2024");
        
        boolean running = true;
        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1 - Додати новий день");
            System.out.println("2 - Переглянути всі записи");
            System.out.println("3 - Знайти день за датою");
            System.out.println("0 - Вийти");
            System.out.print("Ваш вибір: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    System.out.print("Введіть дату (наприклад, 01.01): ");
                    String date = scanner.nextLine();
                    System.out.print("Введіть тип дня (Святковий/Вихідний/Робочий): ");
                    String type = scanner.nextLine();
                    System.out.print("Введіть опис: ");
                    String desc = scanner.nextLine();
                    myCalendar.addDay(date, type, desc);
                    break;
                case "2":
                    myCalendar.displayAllDays();
                    break;
                case "3":
                    System.out.print("Введіть дату для пошуку: ");
                    String searchD = scanner.nextLine();
                    myCalendar.searchByDate(searchD);
                    break;
                case "0":
                    running = false;
                    System.out.println("Роботу завершено.");
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }
}