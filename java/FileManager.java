import java.io.*;
import java.nio.file.*;

public class FileManager {

    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    //запись строки в файл (перезаписывает содержимое)
    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(content);
        }
    }

    //проверка существования файла
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}