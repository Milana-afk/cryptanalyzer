import java.util.*;

public class CaesarCipher {
    //алфавиты (заглавные буквы)
    private static final String ENG_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String RUS_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

    public static String processText(String text, int shift, boolean encrypt) {
        if (text == null || text.isEmpty()) return text;

        //если расшифровка — то сдвиг становится отрицательным (двигаемся в обратную сторону)
        int actualShift = encrypt ? shift : -shift;
        StringBuilder result = new StringBuilder();

        //обрабатываем каждый символ по отдельности
        for (char c : text.toCharArray()) {
            //работаем только с буквами (остальные символы остаются без изменений)
            if (Character.isLetter(c)) {
                boolean isUpperCase = Character.isUpperCase(c);
                char upperChar = Character.toUpperCase(c);  //приводим к верхнему регистру для поиска в алфавите

                //определяем, к какому алфавиту относится буква (ENG/RUS)
                String alphabet = isEnglishLetter(upperChar) ? ENG_ALPHABET : RUS_ALPHABET;
                int index = alphabet.indexOf(upperChar);

                if (index != -1) {
                    //вычисляем новый индекс с учётом сдвига и циклического перехода
                    int newIndex = (index + actualShift) % alphabet.length();
                    if (newIndex < 0) newIndex += alphabet.length();  //корректировка для отрицательного сдвига

                    char newChar = alphabet.charAt(newIndex);
                    //сохраняем исходный регистр
                    result.append(isUpperCase ? newChar : Character.toLowerCase(newChar));
                } else {
                    result.append(c);  //на всякий случай, если буквы нет в алфавите
                }
            } else {
                result.append(c);  //цифры, знаки препинания, пробелы - не меняются
            }
        }
        return result.toString();
    }

    //проверка принадлежности символа к английскому алфавиту
    private static boolean isEnglishLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
}