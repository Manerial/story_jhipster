package com.jher.nid_aux_histoires.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SceneTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Scene getSceneSample1() {
        return new Scene().id(1L).name("name1").number(1);
    }

    public static Scene getSceneSample2() {
        return new Scene().id(2L).name("name2").number(2);
    }

    public static Scene getSceneRandomSampleGenerator() {
        return new Scene().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).number(intCount.incrementAndGet());
    }
}
