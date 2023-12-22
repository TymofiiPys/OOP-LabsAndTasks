package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task10 {
    public class ThreadPool {
        private final BlockingQueue<Runnable> taskQueue;
        private boolean isStopped;
        private PoolWorker[] workers;

        public ThreadPool(int numThreads) {
            taskQueue = new LinkedBlockingQueue<>();
            isStopped = false;
            workers = new PoolWorker[numThreads];
            for (int i = 0; i < numThreads; i++) {
                workers[i] = new PoolWorker();
                workers[i].start();
            }
        }

        public void addTask(Runnable task) {
            synchronized (taskQueue) {
                taskQueue.add(task);
                taskQueue.notify();
            }
        }

        private class PoolWorker extends Thread {
            @Override
            public void run() {
                while (!isStopped()) {
                    Runnable task;
                    try {
                        task = taskQueue.take();
                        task.run();
                    } catch (InterruptedException ignored) {

                    }
                }
            }

            public synchronized void doStop() {
                isStopped = true;
                interrupt();
            }

            public synchronized boolean isStopped() {
                return isStopped;
            }
        }

        public synchronized void stop() {
            isStopped = true;
            for (PoolWorker worker : workers) {
                worker.doStop();
            }
        }

        public synchronized void waitUntilAllTasksFinished() {
            while (!taskQueue.isEmpty()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class FunnyTask implements Runnable {
        private final int numTask;

        public FunnyTask(int numTask) {
            this.numTask = numTask;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
            System.out.println("Hello! I am task №" + numTask);
        }

    }

    public void launchTasks(int numTasks, int numThreads) {
        ThreadPool threadPool = new ThreadPool(numThreads);
        for (int i = 0; i < 10; i++) {
            threadPool.addTask(new FunnyTask(i));
        }
        threadPool.waitUntilAllTasksFinished();
        threadPool.stop();
    }

    public static void main(String[] args) {
        Task10 task10 = new Task10();
        task10.launchTasks(20, 4);
    }
}
