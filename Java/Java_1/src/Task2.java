import java.util.Random;

public class Task2 {
    public static void main(String[] args) {
        Random random = new Random();

        int num3 = 100 + random.nextInt(900); 
        int num4 = 1000 + random.nextInt(9000);

        String strNum3 = String.valueOf(num3);
        String strNum4 = String.valueOf(num4);

        System.out.println("--- Обробка тризначного числа ---");
        System.out.println("Згенероване число: " + strNum3);
        System.out.println("Норма числа: " + calculateNorm(strNum3));

        System.out.println("\n--- Обробка чотиризначного числа ---");
        System.out.println("Згенероване число: " + strNum4);
        System.out.println("Норма числа: " + calculateNorm(strNum4));
    }

    public static int calculateNorm(String numberStr) {
        while (numberStr.length() > 1) {
            int currentSum = 0;
            
            for (int i = 0; i < numberStr.length(); i++) {
                char digitChar = numberStr.charAt(i);
                currentSum += Character.getNumericValue(digitChar);
            }
            
            numberStr = String.valueOf(currentSum);
        }
        
        return Integer.parseInt(numberStr);
    }
}