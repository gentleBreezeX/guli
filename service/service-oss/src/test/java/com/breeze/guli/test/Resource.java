package com.breeze.guli.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author breeze
 * @date 2019/11/26
 */
public class Resource {

    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();

    public void add() {
        lock.lock();
        try {
            while (num != 0) {
                try {
                    c1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t" + (++num));
            c2.signal();
        }finally {
            lock.unlock();
        }
    }

    public void sub() {
        lock.lock();
        try {
            while (num != 1) {
                try {
                    c2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t" + (--num));
            c1.signal();
        }finally {
            lock.unlock();
        }
    }


}
