package org.example;

import junit.framework.TestCase;

import java.util.Random;

public class Task3Test extends TestCase {
    Random rand = new Random();
    public class MyThread extends Thread {
        public MyThread(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++){
                try {
                    Thread.sleep(rand.nextInt(5) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testThreadGroupInfo() {
        ThreadGroup MsgThreads = new ThreadGroup("MsgThreads");
        ThreadGroup JmsThreads = new ThreadGroup(MsgThreads, "JMSThreads");
        ThreadGroup EmailThreads = new ThreadGroup(MsgThreads, "EmailThreads");
        new MyThread(MsgThreads, "msgThread_1").start();
        new MyThread(JmsThreads, "jmsThread_1").start();
        new MyThread(JmsThreads, "jmsThread_2").start();
        new MyThread(EmailThreads, "emailThread_1").start();
        new MyThread(EmailThreads, "emailThread_2").start();
        Task3.threadGroupInfo(MsgThreads);
    }
}