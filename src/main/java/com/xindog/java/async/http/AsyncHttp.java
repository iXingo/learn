package com.xindog.java.async.http;

import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.*;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    12/15/20
 * Time:    2:47 PM
 * Project: learn
 */
@Slf4j
public class AsyncHttp {
    public static void main(String[] args) {
        DefaultAsyncHttpClientConfig.Builder clientBuilder =
                Dsl.config().setConnectTimeout(500).setIoThreadsCount(1).setThreadPoolName("Shawn Wang");
        AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);
        client.prepareGet("https://www.ixingo.com.cn").execute(new AsyncHandler<Object>() {
            @Override
            public State onStatusReceived(HttpResponseStatus httpResponseStatus) {
                log.info("Status:" + httpResponseStatus);
                return null;
            }

            @Override
            public State onHeadersReceived(HttpHeaders httpHeaders) {
                log.info("Status:" + httpHeaders);
                return null;
            }

            @Override
            public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) {
                log.info("Status:" + httpResponseBodyPart);
                return null;
            }

            @Override
            public void onThrowable(Throwable throwable) {
                log.info("Throwable => {}", throwable.getMessage());

            }

            @Override
            public Object onCompleted() {
                log.info("Completed");
                return null;
            }

        });


        client.prepareGet("https://www.xindog.com").execute(new AsyncHandler<Object>() {
            @Override
            public State onStatusReceived(HttpResponseStatus httpResponseStatus) throws Exception {
                log.info("Status:" + httpResponseStatus);
                return null;
            }

            @Override
            public State onHeadersReceived(HttpHeaders httpHeaders) throws Exception {
                log.info("Status:" + httpHeaders);
                return null;
            }

            @Override
            public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                log.info("Status:" + httpResponseBodyPart);
                return null;
            }

            @Override
            public void onThrowable(Throwable throwable) {
                log.info("Throwable => {}", throwable.getMessage());
            }

            @Override
            public Object onCompleted() throws Exception {
                log.info("Finished");
                return null;
            }
        });

    }
}
