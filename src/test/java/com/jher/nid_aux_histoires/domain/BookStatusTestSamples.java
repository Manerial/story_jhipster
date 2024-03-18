package com.jher.nid_aux_histoires.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BookStatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BookStatus getBookStatusSample1() {
        return new BookStatus().id(1L);
    }

    public static BookStatus getBookStatusSample2() {
        return new BookStatus().id(2L);
    }

    public static BookStatus getBookStatusRandomSampleGenerator() {
        return new BookStatus().id(longCount.incrementAndGet());
    }
}
