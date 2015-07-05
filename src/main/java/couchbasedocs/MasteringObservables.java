package couchbasedocs;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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

	public static void main(String[] args) throws Exception {
		asyncToSyncWithLatch();
	}

}
