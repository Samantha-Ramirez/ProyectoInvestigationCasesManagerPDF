package com.ucv.investigationcasesmanager.service;

import java.util.HashMap;
import java.util.Map;

/**
 * PDyF: Service Locator pattern - centralizes service creation and access.
 */
public final class ServiceLocator {
    private static final Map<Class<?>, Object> SERVICES = new HashMap<>();

    private ServiceLocator() {}

    public static <T> T get(Class<T> type) {
        Object service = SERVICES.computeIfAbsent(type, ServiceLocator::newInstance);
        return type.cast(service);
    }

    private static Object newInstance(Class<?> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Could not create service: " + type.getName(), e);
        }
    }
}
