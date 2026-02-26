package com.ucv.investigationcasesmanager.ui.factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * PDyF: Este código implementa el patrón Abstract Factory para crear componentes visuales con un
 * estilo consistente basado en los wireframes.
 */

// Fabrica abstracta
public abstract class PantallaAbstractFactory {
    public abstract JButton crearBotonEncabezado(String texto, ActionListener accion);

    public abstract JButton crearBotonPrimario(String texto, ActionListener accion);

    public abstract JButton crearBotonMenu(String texto, ActionListener accion);

    public abstract void estilizarTexto(JComponent componente);

    public abstract void estilizarTabla(JTable tabla);

    public abstract JLabel crearEstatusIcono(String estatus);

    public abstract Color obtenerColorPrimario();
}
