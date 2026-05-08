import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class Task6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of points to generate for each set: ");
        int count = scanner.nextInt();

        Set<Point> set1 = generateRandomPoints(count, 10);
        Set<Point> set2 = generateRandomPoints(count, 10);

        System.out.println("\nSet 1: " + set1);
        System.out.println("Set 2: " + set2);

        Set<Point> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("\nIntersection (common points): " + intersection);

        Set<Point> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("Difference (Set 1 minus Set 2): " + difference);
        
        scanner.close();
    }

    private static Set<Point> generateRandomPoints(int count, int maxCoordinate) {
        Set<Point> points = new HashSet<>();
        Random random = new Random();
        
        while (points.size() < count) {
            int x = random.nextInt(maxCoordinate);
            int y = random.nextInt(maxCoordinate);
            points.add(new Point(x, y));
        }
        return points;
    }
}