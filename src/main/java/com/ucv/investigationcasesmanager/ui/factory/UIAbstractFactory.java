package com.ucv.investigationcasesmanager.ui.factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * PDyF: Este código implementa el patrón Abstract Factory para crear componentes visuales con un
 * estilo consistente basado en los wireframes.
 */

// Fabrica abstracta que define los métodos para crear componentes visuales
public interface UIAbstractFactory {
    JButton crearBotonEncabezado(String texto, ActionListener accion);

    JButton crearBotonPrimario(String texto, ActionListener accion);

    JButton crearBotonMenu(String texto, ActionListener accion);

    void estilizarTexto(JComponent componente);

    void estilizarTabla(JTable tabla);

    JLabel crearEstatusIcono(String estatus);

    Color obtenerColorPrimario();
}
