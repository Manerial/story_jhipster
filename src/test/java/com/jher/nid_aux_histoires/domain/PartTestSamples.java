package com.jher.nid_aux_histoires.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PartTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Part getPartSample1() {
        return new Part().id(1L).name("name1").description("description1").number(1);
    }

    public static Part getPartSample2() {
        return new Part().id(2L).name("name2").description("description2").number(2);
    }

    public static Part getPartRandomSampleGenerator() {
        return new Part()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .number(intCount.incrementAndGet());
    }
}
