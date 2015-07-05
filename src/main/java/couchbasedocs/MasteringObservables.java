package couchbasedocs;

import rx.Observable;
import rx.Subscriber;

public class MasteringObservables {

	public static void consumeObservable() {

		Observable.just(1, 2, 3)
				.subscribe(new Subscriber<Integer>() {
					@Override
					public void onCompleted() {
						System.out.println("complete observable");
					}

					@Override
					public void onError(Throwable throwable) {
						System.out.println("error: " + throwable.getMessage());
					}

					@Override
					public void onNext(Integer integer) {
						System.out.println("got: "  + integer);
					}
				});

	}

	public static void main(String[] args) {
		System.out.println("Consume simple observable");
		consumeObservable();
	}

}
