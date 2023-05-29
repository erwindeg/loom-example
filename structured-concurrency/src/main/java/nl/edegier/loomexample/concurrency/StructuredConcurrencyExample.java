package nl.edegier.loomexample.concurrency;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StructuredConcurrencyExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        var example = new StructuredConcurrencyExample();
        example.completeBoth();
        example.completeOne();
    }

    private void completeBoth() throws InterruptedException {
        long start = System.currentTimeMillis();
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) { //implements AutoCloseable
            Future<String> one = scope.fork(this::callService1);
            Future<String> two = scope.fork(this::callService2);
            scope.join();

            System.out.println(one.resultNow() + " " + two.resultNow());
            System.out.println(System.currentTimeMillis() - start);
        }
    }

    private void completeOne() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) { //implements AutoCloseable
            scope.fork(this::callService1);
            scope.fork(this::callService2);
            scope.join();

            String result = scope.result();
            System.out.println(result);
            System.out.println(System.currentTimeMillis() - start);
        }
    }

    private String callService1() throws InterruptedException {
        Thread.sleep(1000);
        return "hello";
    }

    private String callService2() throws InterruptedException {
        Thread.sleep(2000);
        return "world";
    }
}
