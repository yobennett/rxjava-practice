package danlew.pt1;

import rx.Observable;
import rx.functions.Action1;

public class HelloWorldSimple2 {

    public static void main(String[] args) {

        Observable.just("Hello, world!")
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println(s);
                }
        });

    }

}
