public class ChickenOrEgg implements Runnable {
    private final int count;

    public ChickenOrEgg(int count) {
        this.count = count;
    }

    /**
     * Переопределенный метод интерфейса Runnable,
     * запускается при выполнении метода Thread.start()<br>
     * Thread.sleep используем для задержки печати, чтобы вывод из разных потоков успел перемешаться
     */
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        for (int i = 0; i < count; i++) {
            System.out.println(currentThread.getName());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
