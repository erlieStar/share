package com.javashitang.blog.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lilimin
 * @since 2021-10-16
 */
@RestController
@RequestMapping("share")
public class ShareController {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    @RequestMapping("test")
    public String test() {
        return "success";
    }

    @RequestMapping("loop")
    public String loop() {
        System.out.println("start");
        while (true) {}
    }

    @RequestMapping("deadlock")
    public String deadlock() {
        new Thread(() -> {
            synchronized (lock1) {
                try{
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {}
                synchronized (lock2) {
                    System.out.println("thread1 over");
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (lock2) {
                try{
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {}
                synchronized (lock1) {
                    System.out.println("thread2 over");
                }
            }
        }).start();
        return "success";
    }


    @SneakyThrows
    @RequestMapping("errorWait")
    public String errorWait() {
        System.out.println("errorWait");
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            threadPool.submit(() -> {
                if (index != 4) {
                    latch.countDown();
                }
            });
        }
        latch.await();
        return "success";
    }
}

