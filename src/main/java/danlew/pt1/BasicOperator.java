package danlew.pt1;

import rx.Observable;

public class BasicOperator {

    public static void main(String[] args) {
        Observable.just("Hello, world!")
                .map(s -> s + " -Bennett")
                .subscribe(System.out::println);
    }

}
