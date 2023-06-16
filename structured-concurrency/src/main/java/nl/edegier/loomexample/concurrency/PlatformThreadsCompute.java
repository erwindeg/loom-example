package nl.edegier.loomexample.concurrency;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.LongStream;

public class PlatformThreadsCompute {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long firstNum = 1;
        long lastNum = 1000000;
        var compute = new PlatformThreadsCompute();
        compute.sumPlatformThreads(firstNum,lastNum);
        compute.sumVirtualThreads(firstNum,lastNum);
    }

    void sumPlatformThreads(long firstNum, long lastNum) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        List<Long> aList = LongStream.rangeClosed(firstNum, lastNum).boxed().toList();

        ThreadFactory factory = Thread.ofPlatform().factory();

        try (ExecutorService e = Executors.newThreadPerTaskExecutor(factory);) {
            long sum = e.submit(() -> aList.parallelStream().reduce(0L, Long::sum)).get();
        }
        System.out.println("Platform threads processing time: " + (System.currentTimeMillis() - start)+" ms");
    }

    void sumVirtualThreads(long firstNum, long lastNum) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<Long> aList = LongStream.rangeClosed(firstNum, lastNum).boxed().toList();

        ThreadFactory factory = Thread.ofVirtual().factory();

        try (ExecutorService e = Executors.newThreadPerTaskExecutor(factory);) {
            long sum = e.submit(() -> aList.parallelStream().reduce(0L, Long::sum)).get();
        }
        System.out.println("Virtual threads processing time: " + (System.currentTimeMillis() - start)+" ms");
    }
}
