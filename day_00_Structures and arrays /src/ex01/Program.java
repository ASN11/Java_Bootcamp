import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        int iterationCount = 1;
        boolean isPrime = true;

        Scanner in = new Scanner(System.in);
        int inputNumber = in.nextInt();
        in.close();

        if (inputNumber <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }

        for (int i = 2; i <= Math.sqrt(inputNumber); i++, iterationCount++) {
            if (inputNumber % i == 0) {
                isPrime = false;
                break;
            }
        }
        System.out.println(isPrime + " " + iterationCount);
    }
}


