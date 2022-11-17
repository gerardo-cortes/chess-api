package com.cortesgerardo.chess.util;

import java.util.List;
import java.util.ListIterator;

public class CircularIterator<E> implements ListIterator<E> {

    private final List<E> delegate;

    private ListIterator<E> listIterator;

    public static <E> CircularIterator<E> of(final List<E> list) {
        return new CircularIterator<>(list);
    }

    public static <E> CircularIterator<E> of(final List<E> list, int index) {
        return new CircularIterator<>(list, index);
    }

    public CircularIterator(final List<E> delegate) {
        this.delegate = delegate;
        this.listIterator = delegate.listIterator();
    }

    public CircularIterator(final List<E> delegate, final int index){
        this.delegate = delegate;
        this.listIterator = delegate.listIterator(index);
    }

    @Override
    public boolean hasNext() {
        return !delegate.isEmpty();
    }

    @Override
    public E next() {
        if (!listIterator.hasNext()) {
            listIterator = delegate.listIterator();
        }
        return listIterator.next();
    }

    @Override
    public boolean hasPrevious() {
        return !delegate.isEmpty();
    }

    @Override
    public E previous() {
        if (!listIterator.hasPrevious()) {
            listIterator = delegate.listIterator(delegate.size() - 1);
            listIterator.next();
        }
        return listIterator.previous();
    }

    @Override
    public int nextIndex() {
        final int nextIndex = listIterator.nextIndex();
        return nextIndex == delegate.size() ? 0 : nextIndex;
    }

    @Override
    public int previousIndex() {
        final int previousIndex = listIterator.previousIndex();
        return previousIndex == -1 ? delegate.size() - 1 : previousIndex;
    }

    @Override
    public void remove() {
        listIterator.remove();
    }

    @Override
    public void set(final E t) {
        listIterator.set(t);
    }

    @Override
    public void add(final E t) {
        listIterator.add(t);
    }

}

