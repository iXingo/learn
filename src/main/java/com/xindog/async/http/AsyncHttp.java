package com.xindog.async.http;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.*;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    12/15/20
 * Time:    2:47 PM
 * Project: learn
 */
public class AsyncHttp {
    public static void main(String[] args) {
        DefaultAsyncHttpClientConfig.Builder clientBuilder =
                Dsl.config().setConnectTimeout(500).setIoThreadsCount(1).setThreadPoolName("Shawn Wang");
        AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);
        BoundRequestBuilder getRequest = client.prepareGet("http://www.ixingo.com.cn");
        getRequest.execute(new AsyncHandler<Object>() {
            @Override
            public State onStatusReceived(HttpResponseStatus httpResponseStatus) throws Exception {

                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
                System.out.println("Status:"+httpResponseStatus);
                return null;
            }

            @Override
            public State onHeadersReceived(HttpHeaders httpHeaders) throws Exception {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
                System.out.println("Status:"+httpHeaders);
                return null;
            }

            @Override
            public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
                System.out.println("Status:"+httpResponseBodyPart);
                return null;
            }

            @Override
            public void onThrowable(Throwable throwable) {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());

            }

            @Override
            public Object onCompleted() throws Exception {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
                System.out.println("Finished");
                return null;
            }
        });
        BoundRequestBuilder getRequest2 = client.prepareGet("http://www.xindog.com");
        getRequest2.execute(new AsyncHandler<Object>() {
            @Override
            public State onStatusReceived(HttpResponseStatus httpResponseStatus) throws Exception {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
                System.out.println("Status:"+httpResponseStatus);
                return null;
            }

            @Override
            public State onHeadersReceived(HttpHeaders httpHeaders) throws Exception {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
                System.out.println("Status:"+httpHeaders);
                return null;
            }

            @Override
            public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
                System.out.println("Status:"+httpResponseBodyPart);
                return null;
            }

            @Override
            public void onThrowable(Throwable throwable) {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());

            }

            @Override
            public Object onCompleted() throws Exception {
                System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
                System.out.println("Finished");
                return null;
            }
        });

    }
}
