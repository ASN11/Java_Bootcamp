import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        Downloader downloader = new Downloader(args);
        downloader.startLoad();
    }
}