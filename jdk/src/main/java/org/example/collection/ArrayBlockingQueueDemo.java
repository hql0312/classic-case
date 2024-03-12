package org.example.collection;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(10);

        for (int i = 0; i < 10; i++) {
            arrayBlockingQueue.put(String.valueOf(i));
        }

        Thread consumer = new Thread(() -> {
            String value = "";
            try {
                // 等待10秒，降低消费频率
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while ((value = arrayBlockingQueue.poll()) != null) {
                System.out.println(value);
            }
        });

        consumer.start();
        long now = System.currentTimeMillis();
        System.out.println("put 第11个数据,当前时间:" + now);
        arrayBlockingQueue.put("aaaa");
        System.out.println("put 第11个数据,花费:" + (System.currentTimeMillis() - now));
    }
}
