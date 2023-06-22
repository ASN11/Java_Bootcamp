public class Program {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("Usage: java Program --arraySize=<amount> --threadsCount=<amount>");
            return;
        }

        Math math = new Math(args);
        math.start();
    }
}
