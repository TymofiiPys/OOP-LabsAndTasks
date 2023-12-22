package org.example;

import java.util.concurrent.Phaser;

public class Task9 {
    public class CustomPhaser {
        private int parties;
        private int waiting;
        private int phase;

        public CustomPhaser(int parties) {
            this.parties = parties;
            this.phase = 0;
            this.waiting = 0;
        }

        public CustomPhaser() {
            this.parties = 0;
            this.phase = 0;
            this.waiting = 0;
        }

        public synchronized int register() {
            parties++;
            return phase;
        }

        public synchronized int arriveAndAwaitAdvance() {
            int currentPhase = phase;
            waiting++;
            if (waiting == parties) {
                phase++;
                waiting = 0;
                notifyAll();
            } else {
                while (currentPhase == phase) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return phase;
        }
    }

    public class MyThread extends Thread {
        CustomPhaser phaser;

        public MyThread(String name, CustomPhaser phaser) {
            super(name);
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                Phaser p = new Phaser();
                int currentPhase = phaser.arriveAndAwaitAdvance();
                System.out.println(getName() + " arrived at phase: " + currentPhase);
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testPhaser() {
        CustomPhaser phaser = new CustomPhaser();
        MyThread t1 = new MyThread("Thread-1", phaser);
        MyThread t2 = new MyThread("Thread-2", phaser);
        MyThread t3 = new MyThread("Thread-3", phaser);
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Task9().testPhaser();
    }
}
