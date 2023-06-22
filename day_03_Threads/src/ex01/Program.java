public class Program {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Program --count=<amount>");
            return;
        }
        int count = Integer.parseInt(args[0].substring(8));

        ChickenOrEgg chickenOrEgg = new ChickenOrEgg();

        Thread eggThread = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                chickenOrEgg.run(true);
            }
        }, "Egg");

        Thread henThread = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                chickenOrEgg.run(false);
            }
        }, "Hen");

        henThread.start();
        eggThread.start();
    }
}
