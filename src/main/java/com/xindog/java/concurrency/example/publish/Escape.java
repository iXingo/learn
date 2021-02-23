package com.xindog.java.concurrency.example.publish;

import com.xindog.java.concurrency.annoations.NotRecommend;
import com.xindog.java.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape {

    private final int thisCanBeEscape = 0;

    public Escape() {
        new InnerClass();
    }

    public static void main(String[] args) {
        new Escape();
    }

    private class InnerClass {

        public InnerClass() {
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }
}
