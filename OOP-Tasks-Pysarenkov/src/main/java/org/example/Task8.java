package org.example;

public class Task8 {
    public class CustomReentrantLock {
        private boolean isLocked = false;
        private Thread lockedBy = null;
        private int lockCount = 0;

        public synchronized void lock() throws InterruptedException {
            Thread callingThread = Thread.currentThread();
            while (isLocked && lockedBy != callingThread) {
                wait();
            }
            isLocked = true;
            lockedBy = callingThread;
            lockCount++;
        }

        public synchronized void unlock() {
            if (Thread.currentThread() == this.lockedBy) {
                lockCount--;

                if (lockCount == 0) {
                    isLocked = false;
                    notify();
                }
            }
        }
    }

    public class MyThread extends Thread {
        CustomReentrantLock lock;

        public MyThread(String name, CustomReentrantLock lock) {
            super(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                try {
                    lock.lock();
                    System.out.println(getName() + ": outer lock acquired");
                    inner();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(getName() + ": outer lock released");
                }
            }
        }

        public void inner() {
            try {
                lock.lock();
                System.out.println(getName() + ": inner lock acquired");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(getName() + ": inner lock released");
            }
        }
    }

    public void testLock() {
        CustomReentrantLock lock = new CustomReentrantLock();
        Thread t1 = new MyThread("Thread-1", lock);
        Thread t2 = new MyThread("Thread-2", lock);
        Thread t3 = new MyThread("Thread-3", lock);
        Thread t4 = new MyThread("Thread-3", lock);
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

    public static void main(String[] args) {
        new Task8().testLock();
    }
}
