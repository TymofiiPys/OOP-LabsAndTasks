package org.example;

public class Task3 {
    private static void outputGroupInfo(ThreadGroup threadGroup){
        System.out.println("Група: " + threadGroup.getName());
        System.out.println("Надгрупа: " + threadGroup.getParent().getName());
        int numThreads = threadGroup.activeCount();
        int numSubgroups = threadGroup.activeGroupCount();
        System.out.println("Активних потоків у групі: " + numThreads);
        System.out.println("Активних підгруп: " + numSubgroups);
        ThreadGroup[] subgroups = new ThreadGroup[numSubgroups];
        Thread[] threads = new Thread[numThreads];
        threadGroup.enumerate(subgroups, false);
        threadGroup.enumerate(threads, false);
        for (Thread thread : threads) {
            if (thread == null)
                continue;
            System.out.println("Ім'я потоку: " + thread.getName());
            System.out.println("Пріоритет: " + thread.getPriority());
        }
        for (ThreadGroup subgroup : subgroups) {
            if (subgroup == null)
                continue;
            outputGroupInfo(subgroup);
        }
    }
    public static void threadGroupInfo(ThreadGroup threadGroup) {
        Thread outputThread = new Thread(() -> {
            while (threadGroup.activeCount() > 0) {
                outputGroupInfo(threadGroup);
                System.out.println("=====================================");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        outputThread.start();
        try {
            outputThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
