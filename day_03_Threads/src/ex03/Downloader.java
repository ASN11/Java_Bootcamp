import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {
    private final String FILE_URLS = System.getProperty("user.dir") + File.separator + "files_urls.txt";
    private int threadsCount;
    ArrayList<String> fileUrls = new ArrayList<>();
    private ExecutorService THREAD_POOL;

    public Downloader(String[] args) throws IOException {
        analyze(args);
        readFile();
    }

    /**
     * Считываем информацию об адресах для скачивания из файла по адресу FILE_URLS и сохраняем в массив fileUrls
     *
     * @throws IOException Файл по адресу FILE_URLS не доступен
     */
    private void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_URLS));
        String line;
        while ((line = reader.readLine()) != null) {
            fileUrls.add(line);
        }
    }

    /**
     *  Заполняет поле threadsCount и THREAD_POOL на основе данных, введенных пользователем. Вызывается в конструкторе
     *
     * @param args - параметры запуска программы, указанные пользователем
     */
    private void analyze(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--threadsCount=")) {
                threadsCount = Integer.parseInt(arg.substring(15));
            }
        }
        THREAD_POOL = Executors.newFixedThreadPool(threadsCount);
    }

    public void startLoad() {
        for (int i = 0; i < fileUrls.size(); i++) {
            THREAD_POOL.submit(new FileLoad(fileUrls.get(i), i+1));
        }
        THREAD_POOL.shutdown();
    }

}
