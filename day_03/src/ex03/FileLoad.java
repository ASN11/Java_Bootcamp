import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class FileLoad implements Runnable {

    private final String fileUrl;
    private final int fileNumber;

    public FileLoad(String fileUrl, int fileNumber) {
        this.fileUrl = fileUrl;
        this.fileNumber = fileNumber;
    }

    /**
     * Переопределенный метод класса Runnable, вызывается методом startLoad()<br>
     * запускает метод fileLoad (скачивание файла<br>
     * Выводит на печать точку начала и окончания скачивания каждого файла
     */
    @Override
    public void run() {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(File.separator) + 1);
        Thread currentThread = Thread.currentThread();
        System.out.println("Thread-" + currentThread.getId() + " \u001B[32mstart download\u001B[0m file number " + fileNumber + " (" + fileName + ")");
        fileLoad(fileUrl, fileName);
        System.out.println("Thread-" + currentThread.getId() + " \u001B[31mfinish download\u001B[0m file number " + fileNumber + " (" + fileName + ")");
    }

    /**
     * На основе полученного пути fileUrl сохраняет файл с таким же названием в текущей директории fileSavePath
     *
     * @param fileUrl путь к файлу
     */
    public  void fileLoad(String fileUrl, String fileName) {
        String fileSavePath = System.getProperty("user.dir") + File.separator + fileName;

        try {
            URL url = new URL(fileUrl);
            InputStream inputStream = url.openStream();
            FileOutputStream outputStream = new FileOutputStream(fileSavePath);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
