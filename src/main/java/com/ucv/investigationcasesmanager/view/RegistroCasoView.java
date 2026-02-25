package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.factory.InicioClient;
import com.ucv.investigationcasesmanager.mediator.RegistroMediator;
import com.ucv.investigationcasesmanager.model.Caso;

import javax.swing.*;
import java.awt.*;

/*
 * Vista de registro de casos, con un formulario unificado para diferentes tipos de casos.
 */
public class RegistroCasoView extends BaseView {
    private JTextField txtNroExpediente, txtMovil, txtObjetivo, txtIncidencia, txtDuracion;
    private JTextArea txtModusOperandi, txtAreaApoyo, txtDeteccion, txtDiagnostico, txtConclusiones,
            txtObservaciones, txtSoporte;
    private JComboBox<String> cbTipoCaso, cbInvestigador, cbTipoIrregularidad, cbSubtipo, cbAccion;

    public RegistroCasoView() {
        super("Registro de casos", true);
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Registro de casos", null, null);
        panelContenido.add(crearPanelRegistro(), BorderLayout.CENTER);

        panelContenido.add(
                crearPanelAccionesInferior(crearBotonPrimario("Registrar", e -> accionRegistrar())),
                BorderLayout.SOUTH);
    }

    private JComponent crearPanelRegistro() {
        JPanel card = crearTarjeta();
        JPanel form = crearFormularioEtiquetado();

        txtNroExpediente = new JTextField();
        cbTipoCaso = new JComboBox<>(new String[] {"Gestión", "Reclamo", "Caso"});
        cbInvestigador = new JComboBox<>(new String[] {"Investigador 1", "Investigador 2"});
        txtMovil = new JTextField();
        txtObjetivo = new JTextField();
        txtIncidencia = new JTextField();
        txtDuracion = new JTextField();

        cbTipoIrregularidad = new JComboBox<>(new String[] {"Tipo Irregularidad 1", "Tipo 2"});
        cbSubtipo = new JComboBox<>(new String[] {"Subtipo A", "Subtipo B"});
        cbAccion = new JComboBox<>(new String[] {"Acción Realizada 1", "Acción 2"});
        txtModusOperandi = crearAreaTextoEstilizada(3, 20, 80);
        txtAreaApoyo = crearAreaTextoEstilizada(2, 20, 60);
        txtDeteccion = crearAreaTextoEstilizada(2, 20, 60);
        txtDiagnostico = crearAreaTextoEstilizada(3, 20, 80);
        txtConclusiones = crearAreaTextoEstilizada(3, 20, 80);
        txtObservaciones = crearAreaTextoEstilizada(2, 20, 60);
        txtSoporte = crearAreaTextoEstilizada(2, 20, 60);

        estilizarEntrada(txtNroExpediente);
        estilizarEntrada(cbTipoCaso);
        estilizarEntrada(cbInvestigador);
        estilizarEntrada(txtMovil);
        estilizarEntrada(txtObjetivo);
        estilizarEntrada(txtIncidencia);
        estilizarEntrada(txtDuracion);
        estilizarEntrada(cbTipoIrregularidad);
        estilizarEntrada(cbSubtipo);
        estilizarEntrada(cbAccion);

        int fila = 0;
        fila = agregarCampoEtiquetado(form, fila, "Nro. expediente", txtNroExpediente);
        fila = agregarCampoEtiquetado(form, fila, "Tipo de caso", cbTipoCaso);
        fila = agregarCampoEtiquetado(form, fila, "Investigador", cbInvestigador);
        fila = agregarCampoEtiquetado(form, fila, "Móvil afectado", txtMovil);
        fila = agregarCampoEtiquetado(form, fila, "Objetivo/Agraviado", txtObjetivo);
        fila = agregarCampoEtiquetado(form, fila, "Incidencia", txtIncidencia);
        fila = agregarCampoEtiquetado(form, fila, "Duración (días)", txtDuracion);
        fila = agregarCampoEtiquetado(form, fila, "Tipo irregularidad", cbTipoIrregularidad);
        fila = agregarCampoEtiquetado(form, fila, "Subtipo", cbSubtipo);
        fila = agregarCampoEtiquetado(form, fila, "Acción realizada", cbAccion);
        fila = agregarCampoEtiquetado(form, fila, "Modus operandi",
                envolverEnScroll(txtModusOperandi));
        fila = agregarCampoEtiquetado(form, fila, "Área de apoyo", envolverEnScroll(txtAreaApoyo));
        fila = agregarCampoEtiquetado(form, fila, "Detección", envolverEnScroll(txtDeteccion));
        fila = agregarCampoEtiquetado(form, fila, "Diagnóstico", envolverEnScroll(txtDiagnostico));
        fila = agregarCampoEtiquetado(form, fila, "Conclusiones",
                envolverEnScroll(txtConclusiones));
        fila = agregarCampoEtiquetado(form, fila, "Observaciones",
                envolverEnScroll(txtObservaciones));
        agregarCampoEtiquetado(form, fila, "Soporte", envolverEnScroll(txtSoporte));

        card.add(envolverEnScroll(form), BorderLayout.CENTER);
        return card;
    }

    private void accionRegistrar() {
        Caso caso = new Caso();

        caso.setNroExpediente(txtNroExpediente.getText());
        caso.setMovilAfectado(txtMovil.getText());
        caso.setObjetivoAgraviado(txtObjetivo.getText());
        caso.setIncidencia(txtIncidencia.getText());
        String duracionStr = txtDuracion.getText();

        caso.setDescripcionModusOperandi(txtModusOperandi.getText());
        caso.setAreaApoyoResolver(txtAreaApoyo.getText());
        caso.setDeteccionProcedencia(txtDeteccion.getText());
        caso.setDiagnosticoDetalleFraude(txtDiagnostico.getText());
        caso.setConclusionesRecomendaciones(txtConclusiones.getText());
        caso.setObservaciones(txtObservaciones.getText());
        caso.setSoporte(txtSoporte.getText());
        caso.setIdTipoCaso(cbTipoCaso.getSelectedIndex() + 1);
        caso.setIdInvestigador(cbInvestigador.getSelectedIndex() + 1);
        caso.setIdTipoIrregularidad(cbTipoIrregularidad.getSelectedIndex() + 1);
        caso.setIdSubtipoIrregularidad(cbSubtipo.getSelectedIndex() + 1);
        caso.setIdAccionRealizada(cbAccion.getSelectedIndex() + 1);

        if (!RegistroMediator.validarYPreparar(caso, usuarioActual, duracionStr)) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
            return;
        }

        if (new CasoDAO().guardarCaso(caso)) {
            JOptionPane.showMessageDialog(this, "Caso registrado.");
            configurarVista(this, InicioClient.inicioSegunRol(usuarioActual.getRol()));
        }
    }
}
