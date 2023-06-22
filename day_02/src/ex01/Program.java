import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Program {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Program input_file1 input_file2");
            return;
        }

        File file1 = new File(args[0]);
        File file2 = new File(args[1]);

        Map<String, Integer> dict = Reader.createDictionary(file1, file2);

        int[] vector1 = Reader.createVector(file1, dict);
        int[] vector2 = Reader.createVector(file2, dict);

        double similarity = Reader.cosineSimilarity(vector1, vector2);

        System.out.printf("Similarity = %.2f%n", similarity);
    }
}

