package com.jher.nid_aux_histoires.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdeaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Idea getIdeaSample1() {
        return new Idea().id(1L).type("type1").value("value1").complement("complement1");
    }

    public static Idea getIdeaSample2() {
        return new Idea().id(2L).type("type2").value("value2").complement("complement2");
    }

    public static Idea getIdeaRandomSampleGenerator() {
        return new Idea()
            .id(longCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .value(UUID.randomUUID().toString())
            .complement(UUID.randomUUID().toString());
    }
}
