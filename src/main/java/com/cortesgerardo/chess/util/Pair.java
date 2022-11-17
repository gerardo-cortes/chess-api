package com.cortesgerardo.chess.util;

public record Pair(int first, int second) {
    public static Pair of(int first, int second) {
        return new Pair(first, second);
    }
}
