package com.ucv.investigationcasesmanager.service;

import java.util.HashMap;
import java.util.Map;

/**
 * PDyF: Este código implementa el patrón Service Locator para gestionar la creación y acceso a
 * servicios de manera centralizada, evitando la necesidad de inyectar dependencias manualmente en
 * cada clase que las requiera.
 */
public final class ServiceLocator {
    private static final Map<Class<?>, Object> SERVICES = new HashMap<>();

    private ServiceLocator() {}

    public static <T> T obtenerServicio(Class<T> tipo) {
        Object servicio = SERVICES.computeIfAbsent(tipo, ServiceLocator::newInstance);
        return tipo.cast(servicio);
    }

    private static Object newInstance(Class<?> tipo) {
        try {
            return tipo.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo crear el servicio: " + tipo.getName(), e);
        }
    }
}
