package org.example.lock;

import java.util.concurrent.CountDownLatch;

/**
 * 该示例主要目的：
 * 1. 验证CountDownLatch的使用
 * 2. 通过示例来了解CountDownLatch的原理
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(4);
        for (int i = 0; i < 4; i++)
            new Thread(() -> {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                startSignal.countDown();
            }).start();

        new Thread(() -> {
            try {
                startSignal.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("the second waits");
        }).start();

        long now = System.currentTimeMillis();
        startSignal.await();
        System.out.println("main start,ts = " + (System.currentTimeMillis() - now) / 1000 + "s");
    }

}
