package rxdocs;

import rx.Observable;
import rx.Subscriber;

public class ObservableCreate {

	public static void main(String[] args) {

		Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						for (int i = 1; i < 5; i++) {
							observer.onNext(i);
						}
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		}).subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("Sequence complete.");
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("ERROR: " + throwable.getMessage());
			}

			@Override
			public void onNext(Integer integer) {
				System.out.println("NEXT: " + integer);
			}
		});

	}

}
