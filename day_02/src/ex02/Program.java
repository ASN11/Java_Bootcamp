import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java Program --current-folder=<path>");
            return;
        }
        Path currentFolder = Paths.get(args[0].substring(17));
        FileUtil fileUtil = new FileUtil(currentFolder);
        fileUtil.run();
    }
}

