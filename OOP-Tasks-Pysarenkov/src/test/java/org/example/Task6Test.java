package org.example;

import junit.framework.TestCase;
import org.example.Task6.*;

import java.util.Random;

public class Task6Test extends TestCase {
    Random rand = new Random();

    private class MyThread extends Thread {
        MSQueue<Integer> queue;

        public MyThread(String name, MSQueue<Integer> queue) {
            super(name);
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                int option = rand.nextInt(2);
                switch (option) {
                    case 0:
                        int num = rand.nextInt(100);
                        queue.enqueue(num);
                        System.out.println(getName() + " enqueued " + num);
                        break;
                    case 1:
                        queue.dequeue();
                        System.out.println(getName() + " dequeued");
                        break;
                }
                try {
                    sleep(rand.nextInt(5) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testQueue() {
        MSQueue<Integer> queue = new MSQueue<>();
        Thread t1 = new MyThread("Thread-1", queue);
        Thread t2 = new MyThread("Thread-2", queue);
        Thread t3 = new MyThread("Thread-3", queue);
        Thread t4 = new MyThread("Thread-4", queue);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}