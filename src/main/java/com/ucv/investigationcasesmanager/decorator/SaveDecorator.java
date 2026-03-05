package com.ucv.investigationcasesmanager.decorator;

/**
 * PDyF: Decorator – Decorador abstracto que envuelve un SaveOperation. Siguiendo el ejemplo del
 * profesor: el Decorator mantiene una referencia al componente y delega a él.
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
