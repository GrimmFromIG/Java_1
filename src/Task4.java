import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputFilePath = "";
        boolean isTempFile = false;
        File tempFile = null;

        System.out.println("Choose operation mode:");
        System.out.println("1 - Enter path to an existing file");
        System.out.println("2 - Generate a temporary file (5 lines of text)");
        System.out.print("Your choice: ");
        String choice = scanner.nextLine();

        if (choice.equals("2")) {
            try {
                tempFile = new File("temp_input.txt");
                generateTempFile(tempFile);
                inputFilePath = tempFile.getAbsolutePath();
                isTempFile = true;
                System.out.println("-> Temporary file generated successfully.");
            } catch (IOException e) {
                System.out.println("ERROR: Failed to create temporary file.");
                return;
            }
        } else {
            System.out.print("Enter the path to the INPUT file (e.g., input.txt): ");
            inputFilePath = scanner.nextLine();
        }

        System.out.print("Enter the letter to search for: ");
        String letterInput = scanner.nextLine();

        if (letterInput.isEmpty()) {
            System.out.println("ERROR: No letter entered. Program terminated.");
            if (isTempFile && tempFile != null) tempFile.delete();
            return;
        }
        char targetLetter = letterInput.charAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        String timestamp = LocalDateTime.now().format(formatter);
        String inputFileName = new File(inputFilePath).getName();
        
        String baseName = inputFileName;
        if (baseName.contains(".")) {
            baseName = baseName.substring(0, baseName.lastIndexOf('.'));
        }
        
        String outputFilePath = timestamp + "_Letter-" + targetLetter + "_" + baseName + "_output.txt";

        System.out.println("\n=== FILE PROCESSING STARTED ===");
        System.out.println("Searching for letter: '" + targetLetter + "'");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            System.out.println("Files opened successfully. Starting to read and write...\n");

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                int count = countLetter(line, targetLetter);

                System.out.println("Processed Line " + lineNumber);

                writer.write("Line " + lineNumber + ": " + line);
                writer.newLine();
                writer.write("--- Count of letter '" + targetLetter + "': " + count + " ---");
                writer.newLine();
                writer.newLine();

                lineNumber++;
            }

            System.out.println("\n=== PROCESSING COMPLETED SUCCESSFULLY ===");
            System.out.println("Results have been saved to: " + outputFilePath);

        } catch (FileNotFoundException e) {
            System.out.println("\nERROR: Input file not found! Check the path: " + inputFilePath);
        } catch (IOException e) {
            System.out.println("\nERROR: An error occurred while processing the files: " + e.getMessage());
        } finally {
            scanner.close();
            
            if (isTempFile && tempFile != null) {
                if (tempFile.delete()) {
                    System.out.println("-> Temporary input file deleted successfully.");
                } else {
                    System.out.println("-> WARNING: Failed to delete temporary input file.");
                }
            }
        }
    }

    private static void generateTempFile(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("First line for testing letter search.");
            writer.newLine();
            writer.write("Java is an object-oriented programming language.");
            writer.newLine();
            writer.write("Here we check how the algorithm works.");
            writer.newLine();
            writer.write("The quick brown fox jumps over the lazy dog.");
            writer.newLine();
            writer.write("The final, fifth line of this temporary file.");
            writer.newLine();
        }
    }

    private static int countLetter(String text, char target) {
        int count = 0;
        char lowerTarget = Character.toLowerCase(target);
        char upperTarget = Character.toUpperCase(target);

        for (char c : text.toCharArray()) {
            if (c == lowerTarget || c == upperTarget) {
                count++;
            }
        }
        return count;
    }
}