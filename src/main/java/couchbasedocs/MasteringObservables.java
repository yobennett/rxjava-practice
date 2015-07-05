package couchbasedocs;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.BlockingObservable;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

	public static void testOnError() {

		Observable.just(1, 2, 3)
				.doOnNext(new Action1<Integer>() {
					@Override
					public void call(Integer integer) {
						if (integer.equals(2)) {
							throw new RuntimeException("2 sucks");
						}
					}
				})
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

	public static void take5() {

		Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
				.take(5)
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
						System.out.println("got: " + integer);
					}
				});

	}

	public static void subscribeAction() {

		Observable.just(1, 2, 3)
				.subscribe(new Action1<Integer>() {
					@Override
					public void call(Integer integer) {
						System.out.println("got " + integer);
					}
				});

	}

	public static void asyncToSyncWithLatch() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(5);
		Observable.interval(1, TimeUnit.SECONDS)
				.subscribe(new Action1<Long>() {
					@Override
					public void call(Long aLong) {
						latch.countDown();
						System.out.println("got " + aLong);
					}
				});
		latch.await();

	}

	public static void blockingObservable() {

		BlockingObservable<Long> observable = Observable
				.interval(1, TimeUnit.SECONDS)
				.toBlocking();

		observable.forEach(new Action1<Long>() {
			@Override
			public void call(Long aLong) {
				System.out.println("got " + aLong);
			}
		});

	}

	public static void blockingObservableInsteadOfLatch() {

		Observable.interval(1, TimeUnit.SECONDS)
				.take(5)
				.toBlocking()
				.forEach(new Action1<Long>() {
					@Override
					public void call(Long aLong) {
						System.out.println("got " + aLong);
					}
				});

	}

	public static List<Integer> emittedValuesToList() {

		return Observable
				.just(1, 2, 3, 4, 5, 6, 7)
				.toList()
				.toBlocking()
				.single();
	}

	public static void blockingObservableWithCreate() {
		Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {

				try {
					if (!subscriber.isUnsubscribed()) {
						for (int i = 0; i < 5; i++) {
							subscriber.onNext(i);
						}
						subscriber.onCompleted();
					}
				} catch (Exception e) {
					subscriber.onError(e);
				}

			}
		}).subscribe(new Action1<Integer>() {
			@Override
			public void call(Integer integer) {
				System.out.println("got " + integer);
			}
		});

	}

	public static void blockingObservableWithCreateLambda() {

		Observable.create(subscriber -> {

			try {
				if (!subscriber.isUnsubscribed()) {
					for (int i = 0; i < 5; i++) {
						subscriber.onNext(i);
					}
					subscriber.onCompleted();
				}
			} catch (Exception e) {
				subscriber.onError(e);
			}

		}).subscribe(System.out::println);

	}

	public static void fizzBuzz() {

		Observable
				.interval(10, TimeUnit.MILLISECONDS)
				.take(20)
				.map(i -> {
					if (i % 3 == 0) {
						return "Fizz";
					} else if (i % 5 == 0) {
						return "Buzz";
					} else {
						return Long.toString(i);
					}
				})
				.toBlocking()
				.forEach(System.out::println);

	}

	public static void scan() {

		Observable
				.just(1, 2, 3)
				.scan((sum, value) -> sum + value)
				.subscribe(System.out::println);

	}


	public static void main(String[] args) throws Exception {
		scan();
	}

}
