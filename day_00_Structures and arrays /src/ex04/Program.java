import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Program {

    private static final short[][] charCounts = new short[256][3];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String res = in.nextLine();
        in.close();

        for (int i = 0; i < res.length(); i++) {
            char c = res.charAt(i);
            charCounts[c][0] = (short) c;
            charCounts[c][1]++;
        }

        Sort();
        OutputGraph();
    }

    /**
     * Определяем компаратор для сортировки по второму значению,<br>
     * Сортируем массив charCounts по второму значению, <br>
     * считаем количество # для каждого символа
     */
    private static void Sort() {
        Comparator<short[]> comparator = (o2, o1) -> Integer.compare(o1[1], o2[1]);
        Arrays.sort(charCounts, comparator);

        for (int i = 0; i < 10; i++) {
            if (charCounts[i][1] > 0) {
                charCounts[i][2] = (short) ((float)charCounts[i][1] / charCounts[0][1] * 10);
            }
        }
    }

    private static void OutputGraph() {
        for (int i = 10; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (charCounts[j][2] == i && charCounts[j][1] > 0) {
                    System.out.printf("%4d", charCounts[j][1]);

                } else if (charCounts[j][2] > i) {
                    System.out.printf("%4s", "#");
                }
            }
            System.out.print("\n");
        }

        for (int i = 0; i < 10; i++) {
            if (charCounts[i][1] > 0) {
                System.out.printf("%4c", charCounts[i][0]);
            }
        }
    }
}
