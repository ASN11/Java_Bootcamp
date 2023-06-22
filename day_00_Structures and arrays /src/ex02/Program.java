import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int counter = 0;
        while (true) {
            int number = in.nextInt();
            if (number == 42) {
                System.out.printf("Count of coffee-request – %d", counter);
                break;
            } else {
                number = GetSum(number);
                if(IsPrimeNumber(number)) {
                    counter += 1;
                }
            }
        }
    }

    public static int GetSum(int number) {
        int sum = 0;

        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    public static boolean IsPrimeNumber(int inputNumber) {
        boolean isPrime = true;
        for (int i = 2; i <= Math.sqrt(inputNumber); i++) {
            if (inputNumber % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
}