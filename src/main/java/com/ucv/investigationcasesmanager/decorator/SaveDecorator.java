package com.ucv.investigationcasesmanager.decorator;

/**
 * PDyF: Decorator – Decorador abstracto que envuelve un SaveOperation.
 */
public abstract class SaveDecorator extends SaveOperation {
    protected SaveOperation component;

    public void setComponent(SaveOperation component) {
        this.component = component;
    }

    @Override
    public void guardar() {
        if (component != null) {
            component.guardar();
        }
    }
}
