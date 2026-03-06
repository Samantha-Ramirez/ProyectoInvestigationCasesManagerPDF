package com.ucv.investigationcasesmanager.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * PDyF: Patrón Iterator - agregador concreto que contiene la colección de entidades y crea el
 * iterador apropiado.
 */
public class EntityListAggregate<T> extends EntityAggregate<T> {
    private final List<T> items;

    public EntityListAggregate(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    public EntityIterator<T> createIterator() {
        return new EntityListIterator<>(items);
    }

    public int getCount() {
        return items.size();
    }

    public T get(int index) {
        return items.get(index);
    }
}
