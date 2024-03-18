package com.jher.nid_aux_histoires.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BonusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bonus getBonusSample1() {
        return new Bonus().id(1L).name("name1").extension("extension1");
    }

    public static Bonus getBonusSample2() {
        return new Bonus().id(2L).name("name2").extension("extension2");
    }

    public static Bonus getBonusRandomSampleGenerator() {
        return new Bonus().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).extension(UUID.randomUUID().toString());
    }
}
