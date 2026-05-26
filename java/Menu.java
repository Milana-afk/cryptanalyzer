import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Caesar Cipher Tool ===");
            System.out.println("1. Encrypt file");
            System.out.println("2. Decrypt file with key");
            System.out.println("3. Brute force decrypt");
            System.out.println("4. Statistical analysis decrypt");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> encryptFile();
                case 2 -> decryptWithKey();
                case 3 -> bruteForce();
                case 4 -> statisticalDecrypt();
                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void encryptFile() {
        System.out.print("Input file path: ");
        String inputPath = scanner.nextLine();
        System.out.print("Output file path: ");
        String outputPath = scanner.nextLine();
        System.out.print("Shift key (positive integer): ");
        int key = getIntInput();

        if (!FileManager.fileExists(inputPath)) {
            System.out.println("Input file does not exist.");
            return;
        }
        if (key <= 0) {
            System.out.println("Key must be positive.");
            return;
        }

        try {
            String text = FileManager.readFile(inputPath);
            String encrypted = CaesarCipher.processText(text, key, true);
            FileManager.writeFile(outputPath, encrypted);
            System.out.println("Encryption successful. Result written to " + outputPath);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void decryptWithKey() {
        System.out.print("Input file path: ");
        String inputPath = scanner.nextLine();
        System.out.print("Output file path: ");
        String outputPath = scanner.nextLine();
        System.out.print("Shift key (positive integer): ");
        int key = getIntInput();

        if (!FileManager.fileExists(inputPath)) {
            System.out.println("Input file does not exist.");
            return;
        }

        try {
            String text = FileManager.readFile(inputPath);
            String decrypted = CaesarCipher.processText(text, key, false);
            FileManager.writeFile(outputPath, decrypted);
            System.out.println("Decryption successful. Result written to " + outputPath);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void bruteForce() {
        System.out.print("Encrypted file path: ");
        String inputPath = scanner.nextLine();
        if (!FileManager.fileExists(inputPath)) {
            System.out.println("File does not exist.");
            return;
        }
        try {
            String text = FileManager.readFile(inputPath);
            BruteForce.bruteForceDecrypt(text);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void statisticalDecrypt() {
        System.out.print("Encrypted file path: ");
        String inputPath = scanner.nextLine();
        System.out.print("Output file path: ");
        String outputPath = scanner.nextLine();

        if (!FileManager.fileExists(inputPath)) {
            System.out.println("Input file does not exist.");
            return;
        }

        try {
            String text = FileManager.readFile(inputPath);
            int bestKey = StatisticalAnalyzer.findBestKey(text);
            System.out.println("Suggested key: " + bestKey);
            String decrypted = CaesarCipher.processText(text, bestKey, false);
            FileManager.writeFile(outputPath, decrypted);
            System.out.println("Statistical decryption completed. Result written to " + outputPath);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }
}