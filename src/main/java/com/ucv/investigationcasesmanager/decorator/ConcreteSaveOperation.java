package com.ucv.investigationcasesmanager.decorator;

/**
 * PDyF: Decorator - componente concreto que ejecuta la operación de guardado real delegando en un
 * Runnable recibido en el constructor.
 */
public class ConcreteSaveOperation extends SaveOperation {
    private final Runnable task;

    public ConcreteSaveOperation(Runnable task) {
        this.task = task;
    }

    @Override
    public void guardar() {
        task.run();
    }
}
