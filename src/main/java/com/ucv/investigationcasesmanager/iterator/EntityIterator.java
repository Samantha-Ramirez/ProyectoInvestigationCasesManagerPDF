package com.ucv.investigationcasesmanager.iterator;

/**
 * PDyF: Iterator - define el contrato para recorrer una colección de elementos sin exponer su
 * representación interna. Usado en UC13 para poblar combos de entidades del sistema.
 */
public interface EntityIterator<T> {
    boolean hasNext();

    T next();
}
