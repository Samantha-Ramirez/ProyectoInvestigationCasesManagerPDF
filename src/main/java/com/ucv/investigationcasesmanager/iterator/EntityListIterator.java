package com.ucv.investigationcasesmanager.iterator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * PDyF: Iterator concreto que recorre una lista de entidades del sistema. Permite a las vistas
 * poblar combos (UC13) sin conocer la estructura interna de la colección (Array, List, etc.).
 */
public class EntityListIterator<T> implements EntityIterator<T> {
    private final List<T> items;
    private int index = 0;

    public EntityListIterator(List<T> items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return index < items.size();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos en el iterador.");
        }
        return items.get(index++);
    }
}
