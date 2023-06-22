import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FileUtil {
    private Path currentFolder;

    public FileUtil(Path currentFolder) {
        this.currentFolder = currentFolder;
    }

    /**
     * Основной метод класса, запускает консольное меню управления программой<br>
     * На старте выводит абсолютный путь до текущего каталога
     *
     * @throws IOException Input \ Output error
     */
    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print(currentFolder.toAbsolutePath() + "\n-> ");
            String[] command = scanner.nextLine().trim().split(" ");
            switch (command[0]) {
                case "ls":
                    listDirectoryContents();
                    break;
                case "cd":
                    if (command.length > 1) {
                        changeDirectory(command[1]);
                    } else {
                        changeDirectory("");
                    }
                    break;
                case "mv":
                    if (command.length == 3) {
                        moveFile(command[1], command[2]);
                    }
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

    /**
     * Обрабатывает команду ls, открывает каталог currentFolder, в котором считывает все файлы в потоке stream<br>
     * Выводит на экран имя файла и его размер в КБ или МБ
     *
     * @throws IOException Input \ Output error
     * @throws UncheckedIOException AccessDeniedException
     */
    private void listDirectoryContents() throws IOException {
        try {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentFolder)) {
                for (Path file : stream) {
                    long size = Files.walk(file)
                            .filter(p -> p.toFile().isFile() || p.toFile().isDirectory())
                            .mapToLong(p -> p.toFile().length())
                            .sum() / 1024;
                    String name = file.getFileName().toString();
                    if (size >= 1024) {
                        System.out.println(name + " " + size / 1024 + " MB");
                    } else {
                        System.out.println(name + " " + size + " KB");
                    }
                }
            }
//        }
        } catch (UncheckedIOException ignored) { }
    }

    /**
     * Обрабатывает команду cd, меняет текущую директорию
     *
     * @param folderName новый путь относительно текущей директории
     */
    private void changeDirectory(String folderName) {
        Path folder;
        if ("".equals(folderName)) {
            folder = currentFolder.getRoot();
        } else {
            folder = currentFolder.resolve(folderName);
        }

        if (Files.isDirectory(folder)) {
            currentFolder = folder.normalize();
        } else {
            System.out.println("Folder not found.");
        }
    }

    /**
     * Обработка команды mv
     *
     * @param from имя изменяемого файла
     * @param to путь перемещения или имя для переименования
     */
    private void moveFile(String from, String to) {
        Path source = currentFolder.resolve(from);
        Path destination = currentFolder.resolve(to);
        Path target = destination.resolve(source.getFileName());
        if (!Files.exists(destination)) {
            try {
                Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File renamed.");
            } catch (IOException e) {
                System.out.println("Error renaming file.");
            }
        } else if (Files.exists(source)) {
            try {
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File moved.");
            } catch (IOException e) {
                System.out.println("Error moving file.");
            }
        } else {
            System.out.println("File not found.");
        }
    }
}
