package danlew.pt1;

import rx.Observable;

public class HelloWorldLambda {

    public static void main(String[] args) {

        Observable.just("Hello, lambda!")
                .subscribe(System.out::println);

    }

}
