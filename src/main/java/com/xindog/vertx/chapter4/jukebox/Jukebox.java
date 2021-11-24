package com.xindog.vertx.chapter4.jukebox;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class Jukebox extends AbstractVerticle {


    private final Queue<String> playlist = new ArrayDeque<>();

    // --------------------------------------------------------------------------------- //
    private final Set<HttpServerResponse> streamers = new HashSet<>();
    private State currentMode = State.PAUSED;
    private AsyncFile currentFile;

    // --------------------------------------------------------------------------------- //
    private long positionInFile;

    // --------------------------------------------------------------------------------- //

    @Override
    public void start() {
        log.info("Start");

        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("jukebox.list", this::list);
        eventBus.consumer("jukebox.schedule", this::schedule);
        eventBus.consumer("jukebox.play", this::play);
        eventBus.consumer("jukebox.pause", this::pause);

        vertx.createHttpServer()
                .requestHandler(this::httpHandler)
                .listen(8081);

        vertx.setPeriodic(100, this::streamAudioChunk);
    }

    private void list(Message<?> request) {
        vertx.fileSystem().readDir("tracks", ".*mp3$", ar -> {
            if (ar.succeeded()) {
                List<String> files = ar.result()
                        .stream()
                        .map(File::new)
                        .map(File::getName)
                        .collect(Collectors.toList());
                JsonObject json = new JsonObject().put("files", new JsonArray(files));
                request.reply(json);
            } else {
                log.error("readDir failed", ar.cause());
                request.fail(500, ar.cause().getMessage());
            }
        });
    }

    private void play(Message<?> request) {
        log.info("Play");
        currentMode = State.PLAYING;
    }

    // --------------------------------------------------------------------------------- //

    private void pause(Message<?> request) {
        log.info("Pause");
        currentMode = State.PAUSED;
    }

    // --------------------------------------------------------------------------------- //

    private void schedule(Message<JsonObject> request) {
        String file = request.body().getString("file");
        log.info("Scheduling {}", file);
        if (playlist.isEmpty() && currentMode == State.PAUSED) {
            currentMode = State.PLAYING;
        }
        playlist.offer(file);
    }

    private void httpHandler(HttpServerRequest request) {
        log.info("{} '{}' {}", request.method(), request.path(), request.remoteAddress());
        if ("/".equals(request.path())) {
            openAudioStream(request);
            return;
        }
        if (request.path().startsWith("/download/")) {
            String sanitizedPath = request.path().substring(10).replaceAll("/", "");
            download(sanitizedPath, request);
            return;
        }
        request.response().setStatusCode(404).end();
    }

    // --------------------------------------------------------------------------------- //

    private void openAudioStream(HttpServerRequest request) {
        log.info("New streamer");
        HttpServerResponse response = request.response()
                .putHeader("Content-Type", "audio/mpeg")
                .setChunked(true);
        streamers.add(response);
        response.endHandler(v -> {
            streamers.remove(response);
            log.info("A streamer left");
        });
    }

    private void download(String path, HttpServerRequest request) {
        String file = "tracks/" + path;
        if (!vertx.fileSystem().existsBlocking(file)) {
            request.response().setStatusCode(404).end();
            return;
        }
        OpenOptions opts = new OpenOptions().setRead(true);
        vertx.fileSystem().open(file, opts, ar -> {
            if (ar.succeeded()) {
                downloadFile(ar.result(), request);
            } else {
                log.error("Read failed", ar.cause());
                request.response().setStatusCode(500).end();
            }
        });
    }

    private void downloadFile(AsyncFile file, HttpServerRequest request) {
        HttpServerResponse response = request.response();
        response.setStatusCode(200)
                .putHeader("Content-Type", "audio/mpeg")
                .setChunked(true);

        file.handler(buffer -> {
            response.write(buffer);
            if (response.writeQueueFull()) {
                file.pause();
                response.drainHandler(v -> file.resume());
            }
        });

        file.endHandler(v -> response.end());
    }

    // --------------------------------------------------------------------------------- //

    private void downloadFilePipe(AsyncFile file, HttpServerRequest request) {
        HttpServerResponse response = request.response();
        response.setStatusCode(200)
                .putHeader("Content-Type", "audio/mpeg")
                .setChunked(true);
        file.pipeTo(response);
    }

    private void streamAudioChunk(long id) {
        if (currentMode == State.PAUSED) {
            return;
        }
        if (currentFile == null && playlist.isEmpty()) {
            currentMode = State.PAUSED;
            return;
        }
        if (currentFile == null) {
            openNextFile();
        }
        currentFile.read(Buffer.buffer(4096), 0, positionInFile, 4096, ar -> {
            if (ar.succeeded()) {
                processReadBuffer(ar.result());
            } else {
                log.error("Read failed", ar.cause());
                closeCurrentFile();
            }
        });
    }

    private void openNextFile() {
        log.info("Opening {}", playlist.peek());
        OpenOptions opts = new OpenOptions().setRead(true);
        currentFile = vertx.fileSystem()
                .openBlocking("tracks/" + playlist.poll(), opts);
        positionInFile = 0;
    }

    // --------------------------------------------------------------------------------- //

    private void closeCurrentFile() {
        log.info("Closing file");
        positionInFile = 0;
        currentFile.close();
        currentFile = null;
    }

    private void processReadBuffer(Buffer buffer) {
        log.info("Read {} bytes from pos {}", buffer.length(), positionInFile);
        positionInFile += buffer.length();
        if (buffer.length() == 0) {
            closeCurrentFile();
            return;
        }
        for (HttpServerResponse streamer : streamers) {
            if (!streamer.writeQueueFull()) {
                streamer.write(buffer.copy());
            }
        }
    }

    // --------------------------------------------------------------------------------- //

    private enum State {PLAYING, PAUSED}
}
