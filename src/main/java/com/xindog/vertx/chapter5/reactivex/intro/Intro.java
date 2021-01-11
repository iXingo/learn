package com.xindog.vertx.chapter5.reactivex.intro;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Intro {

    public static void main(String[] args) throws InterruptedException {
        Observable.just(1, 2, 3)
                .map(Object::toString)
                .map(s -> "@" + s)
                .subscribe(log::info);

        Observable.<String>error(() -> new RuntimeException("Woops"))
                .map(String::toUpperCase)
                .subscribe(log::info, Throwable::printStackTrace);

        Single<String> s1 = Single.just("foo");
        Single<String> s2 = Single.just("bar");
        Flowable<String> m = Single.merge(s1, s2);
        m.subscribe(log::info);

        Observable
                .just("--", "this", "is", "--", "a", "sequence", "of", "items", "!")
                .doOnSubscribe(d -> log.debug("Subscribed!"))
                .delay(5, TimeUnit.SECONDS)
                .filter(s -> !s.startsWith("--"))
                .doOnNext(x -> log.info("doOnNext: " + x))
                .map(String::toUpperCase)
                .buffer(2)
                .subscribe(
                        pair -> log.info("next: " + pair),
                        Throwable::printStackTrace,
                        () -> log.info("~Done~"));

        Thread.sleep(10_000);
    }
}
