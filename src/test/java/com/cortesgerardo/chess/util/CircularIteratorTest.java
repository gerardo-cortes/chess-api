package com.cortesgerardo.chess.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CircularIteratorTest {

    private final List<Integer> list = Arrays.asList(1, 2, 3);

    @Test
    public void testCircularNext() {
        final CircularIterator<Integer> circular = CircularIterator.of(list);
        assertEquals(1, circular.next());
        assertEquals(2, circular.next());
        assertEquals(3, circular.next());
        assertEquals(1, circular.next());
    }

    @Test
    public void testCircularPrevious() {
        final CircularIterator<Integer> circular = CircularIterator.of(list);
        assertEquals(3, circular.previous());
        assertEquals(2, circular.previous());
        assertEquals(1, circular.previous());
        assertEquals(3, circular.previous());
    }

}