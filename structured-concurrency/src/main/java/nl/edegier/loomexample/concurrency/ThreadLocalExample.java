package nl.edegier.loomexample.concurrency;

import jdk.incubator.concurrent.ScopedValue;

public class ThreadLocalExample {

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> context = new ThreadLocal<>();
        var pt1= Thread.ofVirtual().name("pt-1").start(getRunnable(context,"pt-1"));
        var pt2 = Thread.ofVirtual().name("pt-2").start(getRunnable(context,"pt-2"));
        pt1.join();
        pt2.join();

        var vt1= Thread.ofVirtual().name("vt-1").start(getRunnable(context,"vt-1"));
        var vt2 = Thread.ofVirtual().name("vt-2").start(getRunnable(context,"vt-2"));
        vt1.join();
        vt2.join();


    }

    private static Runnable getRunnable(ThreadLocal<String> context, String value) {
        return () -> {
            context.set(value);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+":"+context.get());
        };
    }

}
