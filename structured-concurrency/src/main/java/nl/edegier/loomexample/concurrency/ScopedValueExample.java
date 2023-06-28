package nl.edegier.loomexample.concurrency;

import jdk.incubator.concurrent.ScopedValue;

public class ScopedValueExample {
    public final static ScopedValue<String> VALUE = ScopedValue.newInstance(); //Immutable

    public void testScopedValue(){
        ScopedValue.where(VALUE, "testvalue").run( //only available in this scope
                () -> System.out.println("In lambda: "+VALUE.get())
        );
        System.out.println("Out of lambda"+VALUE.get()); //throws exception
    }

    public static void main(String[] args) {
        var test = new ScopedValueExample();
        test.testScopedValue();
    }
}
