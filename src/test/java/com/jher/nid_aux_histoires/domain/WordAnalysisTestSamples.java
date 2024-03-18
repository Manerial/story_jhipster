package com.jher.nid_aux_histoires.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WordAnalysisTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static WordAnalysis getWordAnalysisSample1() {
        return new WordAnalysis().id(1L).type("type1").name("name1").analysis("analysis1");
    }

    public static WordAnalysis getWordAnalysisSample2() {
        return new WordAnalysis().id(2L).type("type2").name("name2").analysis("analysis2");
    }

    public static WordAnalysis getWordAnalysisRandomSampleGenerator() {
        return new WordAnalysis()
            .id(longCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .analysis(UUID.randomUUID().toString());
    }
}
