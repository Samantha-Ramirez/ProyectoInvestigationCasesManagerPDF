package com.ucv.investigationcasesmanager.iterator;

/**
 * PDyF: Patrón Iterator - agregador abstracto que define el contrato para crear un iterador
 * sobre la colección de entidades, desacoplando la colección de su mecanismo de recorrido.
 */
public abstract class EntityAggregate<T> {
    public abstract EntityIterator<T> createIterator();
}
