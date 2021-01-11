package com.xindog.vertx.chapter1.snippets;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;


@Slf4j
public final class EventLoop {

    private final ConcurrentLinkedDeque<Event<String>> events = new ConcurrentLinkedDeque<>();
    private final ConcurrentHashMap<String, Consumer<String>> handlers = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        EventLoop eventLoop = new EventLoop();

        new Thread(() -> {
            for (int n = 0; n < 6; n++) {
                delay(1000);
                eventLoop.dispatch(new Event<>("tick", String.valueOf(n)));
            }
            eventLoop.dispatch(new Event<>("stop", null));
        }).start();

        new Thread(() -> {
            delay(2500);
            eventLoop.dispatch(new Event<>("hello", "beautiful world"));
            delay(800);
            eventLoop.dispatch(new Event<>("hello", "beautiful universe"));
        }).start();

        eventLoop.dispatch(new Event<>("hello", "world!"));
        eventLoop.dispatch(new Event<>("foo", "bar"));

        eventLoop
                .on("hello", s -> log.info("hello " + s))
                .on("tick", n -> log.info("tick #" + n))
                .on("stop", v -> eventLoop.stop())
                .run();

        System.out.println("Bye!");
    }

    private static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public EventLoop on(String key, Consumer<String> handler) {
        handlers.put(key, handler);
        return this;
    }

    public void dispatch(Event<String> event) {
        log.warn("Dispatch an event: {}=> {}", event.key, event.data);
        events.add(event);
    }

    public void run() {
        while (!(events.isEmpty() && Thread.interrupted())) {
            if (!events.isEmpty()) {
                Event<String> event = events.pop();
                if (handlers.containsKey(event.key)) {
                    handlers.get(event.key).accept(String.valueOf(event.data));
                } else {
                    log.error("No handler for key " + event.key);
                }
            }
        }
    }

    public void stop() {
        Thread.currentThread().interrupt();
    }

    public static final class Event<T> {
        private final String key;
        private final Object data;

        public Event(String key, T data) {
            this.key = key;
            this.data = data;
            log.debug("New Event Created: key: {} => data:{}", key, data);
        }
    }
}
