package danlew.pt1;

import rx.Observable;

public class BasicOperatorIntegerOutput {

    public static void main(String[] args) {
        Observable.just("Hello, world!")
                .map(s -> " -Bennett")
                .map(String::hashCode)
                .subscribe(System.out::println);
    }

}
