import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reader {

    public Reader() {}

    /**
     * Метод создает на основе file1 и file2 словарь уникальных слов, а также создает файл "dictionary.txt",
     * в который записывает этот словарь
     *
     * @param file1 Файл для сравнения 1
     * @param file2 Файл для сравнения 2
     * @return Map<String, Integer> - словарь уникальных слов из обоих файлов String, каждое из который встречается в
     * тексте Integer раз
     * @throws IOException Обработка ошибок ввода вывода при чтении и записи файлов
     */
    public static Map<String, Integer> createDictionary(File file1, File file2) throws IOException {
        Map<String, Integer> dict = new HashMap<>();

        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        writeDictionary(dict, reader1);
        writeDictionary(dict, reader2);

        reader1.close();
        reader2.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"));
        for (String word : dict.keySet()) {
            writer.write(word + "\n");
        }
        writer.close();

        return dict;
    }

    private static void writeDictionary(Map<String, Integer> dict, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\W+");
            for (String word : words) {
                dict.put(word, dict.getOrDefault(word, 0) + 1);
            }
        }
    }

    /**
     * Создаем вектор с длиной, равной длине словаря dict. В i-й позиции вектора отразим частоту встречаемости i-го
     * слова в словаре в первом и последнем текстах:
     *
     * @param file Файл, для которого создаем вектор
     * @param dict Словарь уникальных слов
     * @return int[] vector массив, в котором каждая цифра соотвестсвует количеству встреченных слов словаря dict
     * из файла file
     * @throws IOException Обработка ошибок ввода вывода при чтении и записи файлов
     */
    public static int[] createVector(File file, Map<String, Integer> dict) throws IOException {
        int[] vector = new int[dict.size()];

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\W+");
            for (String word : words) {
                if (dict.containsKey(word)) {
                    int index = new ArrayList<>(dict.keySet()).indexOf(word);
                    vector[index]++;
                }
            }
        }

        reader.close();

        return vector;
    }

    /**
     * Метод вычисляет результат сравнения двух массивов vector1 и vector2 и возвращает итоговый ответ
     *
     * @param vector1 Массив с количеством втреченных слов из первого файла
     * @param vector2 Массив с количеством втреченных слов из второго файла
     * @return результат их сравнения сходства (косинусная мера)
     */
    public static double cosineSimilarity(int[] vector1, int[] vector2) {
        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += vector1[i] * vector1[i];
            norm2 += vector2[i] * vector2[i];
        }

        double denominator = Math.sqrt(norm1) * Math.sqrt(norm2);

        if (denominator == 0) {
            return 0;
        } else {
            return dotProduct / denominator;
        }
    }
}
