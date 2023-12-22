package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
public class Task7 {
    public class CustomCyclicBarrier {
        private int waitingThreads;
        private final int totalThreads;

        public CustomCyclicBarrier(int totalThreads){
            this.waitingThreads = 0;
            this.totalThreads = totalThreads;
        }

        public synchronized void await(int numThread){
            waitingThreads++;
            if (waitingThreads < totalThreads){
                System.out.println("Thread " + numThread + " waiting at the barrier");
                try {
                    wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            } else {
                System.out.println("Thread " + numThread + " broke the barrier");
                waitingThreads = 0;
                notifyAll();
            }
        }
    }

    public class Worker implements Runnable{
        private final CustomCyclicBarrier barrier;

        private final int numThread;

        public Worker(CustomCyclicBarrier barrier, int numThread){
            this.barrier = barrier;
            this.numThread = numThread;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                barrier.await(numThread);
            }
        }
    }

    public void launchWorkers(int numWorkers){
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(numWorkers);
        Thread[] workers = new Thread[numWorkers];
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new Thread(new Worker(barrier, i), "" + i);
            workers[i].start();
        }
        for (int i = 0; i < numWorkers; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Task7 t7 = new Task7();
        t7.launchWorkers(4);
    }
}
