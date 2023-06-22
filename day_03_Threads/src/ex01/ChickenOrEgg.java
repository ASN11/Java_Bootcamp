public class ChickenOrEgg {
    private static boolean isEggTurn_ = false;


    /**
     * synchronized метод, который проверяет, кто его вызвал, после чего печатает имя потока, в котором он был вызван.<br>
     * Печать происходит по очереди за счет постоянного изменения значения isEggTurn_ на противоположное
     *
     * @param isEggTurn Параметр, проверяющий, курица или яйцо вызывают данный метод
     */
    public synchronized void run(boolean isEggTurn) {
        while (isEggTurn_ == isEggTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isEggTurn_ = isEggTurn;
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName());
        notifyAll();
    }
}
