import java.util.*;

public class StatisticalAnalyzer {
    //эталонные частоты букв в английском и русском языках (от самых частых к редким)
    private static final String ENG_FREQ = "ETAOINSHRDLCUMWFGYPBVKJXQZ";
    private static final String RUS_FREQ = "ОЕАИНТСРВЛКМДПУЯЫЬГЗБЧЙХЖШЮЦЁЩФЭ";


    public static int findBestKey(String encryptedText) {
        //сначала определяем язык текста по преобладанию букв
        String language = detectLanguage(encryptedText);
        String freqOrder = language.equals("EN") ? ENG_FREQ : RUS_FREQ;

        //считаем частоту каждой буквы в зашифрованном тексте
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : encryptedText.toCharArray()) {
            if (Character.isLetter(c)) {
                char upper = Character.toUpperCase(c);
                freqMap.put(upper, freqMap.getOrDefault(upper, 0) + 1);
            }
        }

        //сортируем буквы по убыванию частоты
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(freqMap.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        if (list.isEmpty()) return 0;  //нет букв — возвращаем 0

        //самая частая буква в тексте
        char mostFrequent = list.get(0).getKey();
        //самая частая буква в языке (E для английского, О для русского)
        char expectedMostFrequent = freqOrder.charAt(0);

        //вычисляем сдвиг: позиция частой буквы в тексте минус позиция ожидаемой частой буквы
        int shift = (getAlphabetIndex(mostFrequent, language) - getAlphabetIndex(expectedMostFrequent, language) + getAlphabetLength(language)) % getAlphabetLength(language);
        return shift;
    }

    //определяем язык текста: считаем количество английских и русских букв, где больше — тот и язык
    private static String detectLanguage(String text) {
        int engCount = 0, rusCount = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) engCount++;
                else if (c >= 'А' && c <= 'я') rusCount++;
            }
        }
        return engCount >= rusCount ? "EN" : "RU";
    }

    //возвращает индекс буквы в алфавите соответствующего языка
    private static int getAlphabetIndex(char c, String language) {
        String alphabet = language.equals("EN") ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" : "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        return alphabet.indexOf(c);
    }

    private static int getAlphabetLength(String language) {
        return language.equals("EN") ? 26 : 33;
    }
}