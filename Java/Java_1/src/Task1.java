import java.util.Random;

public class Task1 {
    public static void main(String[] args) {
        System.out.println("Виконав: Петрощук Б.С.");
        System.out.println("-------------------------------------------------");

        int rows = 5;
        int cols = 6;
        int[][] A = new int[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = (random.nextInt(25)*2) + 1; 
            }
        }

        System.out.println("Матриця А:");
        printMatrix(A);

        int maxSum = -1;
        int maxRowIndex = -1;

        for (int i = 0; i < rows; i++) {
            boolean onlyOdds = true;
            int currentSum = 0;

            for (int j = 0; j < cols; j++) {
                if (A[i][j] % 2 == 0) {
                    onlyOdds = false;
                    break; 
                }
                currentSum += Math.abs(A[i][j]);
            }

            if (onlyOdds && currentSum > maxSum) {
                maxSum = currentSum;
                maxRowIndex = i;
            }
        }

        System.out.println("Рядок з максимальною сумою модулів елементів (тільки непарні):");
        if (maxRowIndex != -1) {
            for (int j = 0; j < cols; j++) {
                System.out.print(A[maxRowIndex][j] + "\t");
            }
            System.out.println("\nСума модулів цього рядка: " + maxSum);
        } else {
            System.out.println("Таких рядків (де всі елементи непарні) не знайдено.");
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}