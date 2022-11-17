package com.cortesgerardo.chess.util;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TraverseHelper {
    public static IntStream asc(int from, int to) {
        return IntStream.range(from, to);
    }

    public static IntStream desc(int to, int from) {
        return IntStream.range(from, to - 1)
                .map(i -> to - i + from - 1);
    }

    public static Stream<Pair> upwardsForward(Pair from, Pair to) {
        return Stream
                .iterate(
                        Pair.of(from.first() + 1, from.second() + 1),
                        p -> p.first() < to.first(),
                        p -> Pair.of(p.first() + 1, p.second() + 1)
                );
    }

    public static Stream<Pair> upwardsBackwards(Pair from, Pair to) {
        return Stream
                .iterate(
                        Pair.of(from.first() - 1, from.second() - 1),
                        p -> p.first() > to.first() ,
                        p -> Pair.of(p.first() - 1, p.second() - 1)
                );
    }

    public static Stream<Pair> downwardsForward(Pair from, Pair to) {
        return Stream
                .iterate(
                        Pair.of(from.first() - 1, from.second() + 1),
                        p -> p.second() < to.second(),
                        p -> Pair.of(p.first() - 1, p.second() + 1)
                );
    }

    public static Stream<Pair> downwardsBackwards(Pair from, Pair to) {
        return Stream
                .iterate(
                        Pair.of(from.first() + 1, from.second() - 1),
                        p -> p.first() < to.first(),
                        p -> Pair.of(p.first() + 1, p.second() - 1)
                );
    }

}
