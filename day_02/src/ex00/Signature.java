import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Signature {
    private final Map<String, String> signatures;
    private String res;


    public Signature(){
        signatures = new HashMap<>();
        readSignature();
    }

    /**
     * Считываем файл signatures.txt и сохраняем в signatures пары (тип файла \ его сигнатура).<br>
     * Вызывается в конструкторе при инициализации класса
     *
     * @throws RuntimeException если файл signatures.txt не удалось открыть
     */
    public void readSignature() {
        try {
            Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("signatures.txt")));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",\\s*"); // разделяем строку на тип файла и его код
                signatures.put(parts[1], parts[0]);
            }

            scanner.close();
        } catch (NullPointerException e) {
            System.out.println("File signatures.txt not found");
            throw new RuntimeException();
        }
    }

    /**
     * Считываем модержимое файла и сохраняем первые 20 символов в 16ричной системе в строку res
     *
     * @param path полный путь до анализируемого файла
     * @throws IOException если файл не удалось открыть
     */
    public void analyzeFile(String path) {
        res = "";
        try {
            byte[] signature = Files.readAllBytes(Paths.get(path));
            int counter = 0;
            for (byte b : signature) {
                if (counter == 20) {
                    break;
                }
                if (res.equals("")) {
                    res = String.format("%02X", b);
                } else {
                    res = String.format("%s %02X", res, b);
                }
                counter++;
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Сравниваем сигнатуру анализируемого файла со списком сигнатур signatures.<br>
     * Если находим совпадение, записываем тип файла в result.txt.<br>
     * Если нет - выводим "UNDEFINED" в терминал
     *
     * @throws IOException если ест проблемы с созданием или считыванием файла
     */
    public void searchType() {
        boolean flag = false;

        for (Map.Entry<String, String> entry : signatures.entrySet()) {
            String key = entry.getKey();

            if (res.substring(0, key.length()).equals(key)) {
                String value = entry.getValue();

                try {
                    String outputPath = System.getProperty("user.dir") + File.separator + "result.txt";
                    FileWriter writer = new FileWriter(outputPath, true);
                    writer.write(value + "\n");
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
                System.out.println("PROCESSED");
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("UNDEFINED");
        }
    }
}
