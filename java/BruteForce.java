public class BruteForce {
    static void bruteForceDecrypt(String encryptedText) {
        System.out.println("=== Brute Force Results ===");
        for (int shift = 1; shift <= 33; shift++) { //максимум для русского языка
            String decrypted = CaesarCipher.processText(encryptedText, shift, false);
            System.out.println("Key " + shift + ": " + decrypted.substring(0,Math.min(100, decrypted.length())) + "...");
            if (decrypted.length() > 100) System.out.println("... (truncated)"); //truncated - обрезанный
            System.out.println("------------------------");
        }
    }
}
