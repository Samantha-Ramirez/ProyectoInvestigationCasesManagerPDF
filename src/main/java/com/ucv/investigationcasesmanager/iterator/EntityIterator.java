package com.ucv.investigationcasesmanager.iterator;

/**
 * PDyF: Iterator - define el contrato para recorrer una colección de elementos sin exponer su
 * representación interna.
 */
public abstract class EntityIterator<T> {
    public abstract T first();

    public abstract T next();

    public abstract boolean isDone();

    public abstract T currentItem();
}
