package danlew.pt2;

import rx.Observable;

import java.util.Arrays;
import java.util.List;

public class URLsFromTextSearch {

    public static Observable<List<String>> query(String text) {
        List<String> urls = Arrays.asList("foo", "bar", "baz", "quux", "quuux", "quuuux");
        return Observable.just(urls);
    }

    public static Observable<String> titleFromUrl(String url) {
        return Observable.just("title for " + url);
    }

    public static Observable<String> saveTitle(String title) {
        // save title to disk here
        return Observable.just(title);
    }

    public static void main(String[] args) {
        query("blah")
                .flatMap(Observable::from)
                .flatMap(URLsFromTextSearch::titleFromUrl)
                .filter(title -> title != null)
                .take(5)
                .doOnNext(URLsFromTextSearch::saveTitle)
                .subscribe(System.out::println);
    }

}
