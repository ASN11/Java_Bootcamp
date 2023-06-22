import java.util.Random;

public class Math implements Runnable {
    private int arraySize;
    private int threadsCount;
    private int[] randomValue;
    private int[] sumOfThreads;
    private int standardSumArray;
    private int threadSumArray;
    int threadSectionSize;
    int remainingElements;

    public Math(String[] args) {
        analyze(args);
        generateRandomArr();
    }

    /**
     * Создает массив randomValue с размером arraySize и заполяет его рандомными значениями от -1000 до 1000.<br>
     * Выводит на печать сумму этих значений. Вызывается в конструкторе
     */
    private void generateRandomArr() {
        randomValue = new int[arraySize];
        Random random = new Random();

        for (int i = 0; i < randomValue.length; i++) {
            randomValue[i] = random.nextInt(2001) - 1000;
            standardSumArray += randomValue[i];
        }

        System.out.println("Sum: " + standardSumArray);
    }

    /**
     * Заполняет поля arraySize и threadsCount на основе данных, введенных пользователем. Вызывается в конструкторе
     *
     * @param args - параметры запуска программы, указанные пользователем
     */
    private void analyze(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--arraySize=")) {
                arraySize = Integer.parseInt(arg.substring(12));
                if (arraySize > 2000000) {
                    throw new RuntimeException("arraySize > 2000000");
                }
            } else if (arg.startsWith("--threadsCount=")) {
                threadsCount = Integer.parseInt(arg.substring(15));
                if (threadsCount > arraySize) {
                    throw new RuntimeException("threadsCount > arraySize");
                }
            }
        }
    }

    /**
     * Переопределенный метод класса Runnable, вызывается методом start()<br>
     * Считает сумму части массива randomValue, ограниченную индексами begin и end
     * и заносит результат в массив sumOfThreads по индексу номера массива<br>
     * Выводит на печать результат вычисления для каждого потока в отдельности
     */
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        int threadNumber = Integer.parseInt(currentThread.getName());
        int begin = threadSectionSize * (threadNumber);
        int end = begin + threadSectionSize;
        if ((threadNumber + 1) == threadsCount) {
            end += remainingElements;
        }

        for (int i = begin; i < end; i++) {
            sumOfThreads[threadNumber] += randomValue[i];
        }

        System.out.println("Thread " + (threadNumber + 1) + ": from " + begin + " to " + (end - 1) + " sum is " +  sumOfThreads[threadNumber]);
    }

    /**
     * Вычисляет размер сегмента threadSectionSize, который надо посчитать для каждого потока
     * и размер сегмента для последнего потока remainingElements<br>
     * Создает массив потоков потоков с размером threadsCount и отправляет их в метод run()<br>
     * Считаетобщую сумму значений массива sumOfThreads, который был заполнен отдельными потоками в методе run()<br>
     * Выводит на печать общую сумму значений.
     *
     * @throws InterruptedException Thread.join()
     */
    public void start() throws InterruptedException {
        threadSectionSize = arraySize / threadsCount;
        sumOfThreads = new int[threadsCount];
        remainingElements = arraySize % threadsCount;
        Thread[] sumThread = new Thread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            sumThread[i] = new Thread(this, Integer.toString(i));
            sumThread[i].start();
        }

        for (int i = 0; i < threadsCount; i++) {
            sumThread[i].join();
        }

        for (int i = 0; i < threadsCount; i++) {
            threadSumArray += sumOfThreads[i];
        }
        System.out.println("Sum by threads: " + threadSumArray);
    }
}
