import java.util.Scanner;

public class Program {

    private static String progressData = "";
    private static int weekNumber;
    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 9;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int analyzePhase = 0;

        while (true) {
            String input = in.nextLine();

            if (analyzePhase == 0) {
                WeekAnalyze(input);
                analyzePhase++;

            } else if (analyzePhase == 1) {
                ScoreAnalyze(input);
                analyzePhase--;
            }
        }
    }

    private static void ScoreAnalyze(String input) {
        if (input.length() != 9) {
            ErrorIndicator();
        }

        int minResult = MAX_SCORE;

        for (int j = 0; j < input.length(); j += 2) {
            int result = (int) input.charAt(j) - 48;
            if (result < MIN_SCORE || result > MAX_SCORE) {
                ErrorIndicator();
            }
            minResult = Math.min(minResult, result);
        }
        StringBuilder graph = new StringBuilder();
        for (int i = 0; i < minResult; i++) {
            graph.append("=");
        }
        graph.append(">");

        progressData = progressData.concat( "Week " + weekNumber + " " + graph + '\n');
    }

    private static void WeekAnalyze(String input) {
        boolean err = false;
        if (input.startsWith("Week ")) {
            String numString = input.substring(5);
            if (numString.matches("^[1-9]$|^[1][0-8]$")) {
                weekNumber = Integer.parseInt(numString);
                err = true;
            }
        }

        if (input.equals("42")) {
            System.out.print(progressData);
            System.exit(0);
        }

        if (!err) {
            ErrorIndicator();
        }
    }

    private static void ErrorIndicator() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }
}

/*
Week 1
4 5 2 4 2
Week 2
7 7 7 7 6
Week 3
4 3 4 9 8
Week 4
9 9 4 6 7
42
 */