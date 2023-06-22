import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Signature signature = new Signature();
        Scanner in = new Scanner(System.in);
        String str;
        while (true) {
            try {
                str = in.nextLine();
                if (str.equals("42")) {
                    break;
                }
                signature.analyzeFile(str);
                signature.searchType();
            } catch (Exception ignored) {}
        }
    }
}
