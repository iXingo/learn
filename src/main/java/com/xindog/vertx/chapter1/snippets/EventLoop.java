package com.xindog.vertx.chapter1.snippets;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;


@Slf4j
public final class EventLoop {

    public static final class Event {
        private final String key;
        private final Object data;

        public Event(String key, Object data) {
            this.key = key;
            this.data = data;
        }
    }

    private final ConcurrentLinkedDeque<Event> events = new ConcurrentLinkedDeque<>();
    private final ConcurrentHashMap<String, Consumer<Object>> handlers = new ConcurrentHashMap<>();

    public EventLoop on(String key, Consumer<Object> handler) {
        handlers.put(key, handler);
        return this;
    }

    public void dispatch(Event event) {
        log.warn("Dispatch an event: {}=> {}", event.key, event.data);
        events.add(event);
    }

    public void run() {
        while (!(events.isEmpty() && Thread.interrupted())) {
            if (!events.isEmpty()) {
                Event event = events.pop();
                if (handlers.containsKey(event.key)) {
                    handlers.get(event.key).accept(event.data);
                } else {
                    log.error("No handler for key " + event.key);
                }
            }
        }
    }

    public void stop() {
        Thread.currentThread().interrupt();
    }

    public static void main(String[] args) {
        EventLoop eventLoop = new EventLoop();

        new Thread(() -> {
            for (int n = 0; n < 6; n++) {
                delay(1000);
                eventLoop.dispatch(new Event("tick", n));
            }
            eventLoop.dispatch(new Event("stop", null));
        }).start();

        new Thread(() -> {
            delay(2500);
            eventLoop.dispatch(new Event("hello", "beautiful world"));
            delay(800);
            eventLoop.dispatch(new Event("hello", "beautiful universe"));
        }).start();

        eventLoop.dispatch(new Event("hello", "world!"));
        eventLoop.dispatch(new Event("foo", "bar"));

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
}
