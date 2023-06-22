public class Program {

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.out.println("Usage: java Program --count=<amount>");
            return;
        }
        int count = Integer.parseInt(args[0].substring(8));

        Thread henThread = new Thread(new ChickenOrEgg(count), "Hen");
        Thread eggThread = new Thread(new ChickenOrEgg(count), "Egg");

        henThread.start();
        eggThread.start();

        henThread.join();
        eggThread.join();

        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }
}
