package com.ucv.investigationcasesmanager.iterator;

import java.util.List;

/**
 * PDyF: Iterator concreto que recorre una lista de entidades del sistema. Permite a las vistas
 * poblar combos (UC13) sin conocer la estructura interna de la colección (Array, List, etc.).
 */
public class EntityListIterator<T> extends EntityIterator<T> {
    private final List<T> items;
    private int current = 0;

    public EntityListIterator(List<T> items) {
        this.items = items;
    }

    // Regresa el primer elemento y posiciona el cursor al inicio
    @Override
    public T first() {
        current = 0;
        return items.isEmpty() ? null : items.get(0);
    }

    // Avanza al siguiente elemento; retorna null si la colección se ha agotado
    @Override
    public T next() {
        current++;
        if (current < items.size()) {
            return items.get(current);
        }
        return null;
    }

    // Por qué: isDone() indica si el cursor ha pasado el último elemento, equivalente
    // a la verificación de fin de colección en el ejemplo del profesor.
    @Override
    public boolean isDone() {
        return current >= items.size();
    }

    @Override
    public T currentItem() {
        if (current >= 0 && current < items.size()) {
            return items.get(current);
        }
        return null;
    }
}
