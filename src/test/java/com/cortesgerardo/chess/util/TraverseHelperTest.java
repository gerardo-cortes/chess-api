package com.cortesgerardo.chess.util;

import org.junit.jupiter.api.Test;

class TraverseHelperTest {

    @Test
    void asc() {
        TraverseHelper.asc(0, 3).forEach(System.out::println);
    }

    @Test
    void desc() {
        TraverseHelper.desc(3, 0).forEach(System.out::println);
    }

    @Test
    void upwardsForward() {
        TraverseHelper
                .upwardsForward(Pair.of(3,3), Pair.of(7,7))
                .forEach(System.out::println);
    }

    @Test
    void upwardsBackwards() {
        TraverseHelper
                .upwardsBackwards(Pair.of(3, 3), Pair.of(0, 0))
                .forEach(System.out::println);
    }

    @Test
    void downwardsForward() {
        TraverseHelper
                .downwardsForward(Pair.of(3,3), Pair.of(0,6))
                .forEach(System.out::println);
    }

    @Test
    void downwardsBackwards() {
        TraverseHelper
                .downwardsBackwards(Pair.of(3,3),Pair.of(6,0))
                .forEach(System.out::println);
    }
}