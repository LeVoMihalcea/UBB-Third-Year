import java.util.LinkedList;
import java.util.Random;

public class Main {

    public static void main(String[] args)
            throws InterruptedException
    {
        final ProducerConsumer pc = new ProducerConsumer();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.produce();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.consume();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static class ProducerConsumer {

        LinkedList<Integer> list = new LinkedList<>();
        int capacity = 3;
        Random random = new Random();

        public void produce() throws InterruptedException
        {
            int value = 0;
            while (true) {
                synchronized (this)
                {
                    while (list.size() == capacity) {
                        wait();
                    }

                    int number1 = random.nextInt(10);
                    int number2 = random.nextInt(10);

                    list.add(number1 * number2);

                    System.out.println(String.format("Producer: %d %d %d", number1, number2, number1 * number2));

                    notify();

//                    Thread.sleep(100);
                }
            }
        }

        public void consume() throws InterruptedException
        {
            int sum = 0;
            while (true) {
                synchronized (this)
                {
                    while (list.size() == 0) {
                        wait();
                    }

                    int val = list.removeFirst();
                    sum += val;

                    System.out.println("Consumer consumed - " + val + " and current sum is " + sum);

                    notify();

//                    Thread.sleep(100);
                }
            }
        }
    }
} 