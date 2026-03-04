package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CasoController;
import com.ucv.investigationcasesmanager.factory.InicioClient;
import com.ucv.investigationcasesmanager.model.Caso;
import com.ucv.investigationcasesmanager.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Vista de registro de casos.
 */
public class RegistroCasoView extends BaseView {
    private final CasoController casoController;
    private JTextField txtNroExpediente, txtMovil, txtObjetivo, txtIncidencia, txtDuracion;
    private JTextArea txtModusOperandi, txtAreaApoyo, txtDeteccion, txtDiagnostico, txtConclusiones,
            txtObservaciones, txtSoporte;
    private JComboBox<String> cbTipoCaso, cbInvestigador, cbTipoIrregularidad, cbSubtipo, cbAccion;
    private List<Usuario> investigadores;

    public RegistroCasoView() {
        super("Registro de casos", true);
        this.casoController = new CasoController();
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
        cbInvestigador = cargarComboInvestigadores();
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

    private JComboBox<String> cargarComboInvestigadores() {
        investigadores = casoController.obtenerInvestigadores();
        JComboBox<String> combo = new JComboBox<>();

        if (investigadores.isEmpty()) {
            combo.addItem("Sin investigadores");
        } else {
            for (Usuario inv : investigadores) {
                combo.addItem(inv.getNombre() + " " + inv.getApellido());
            }
        }

        // Si el usuario actual es investigador, preseleccionar su propio nombre y deshabilitar
        if ("Investigador".equalsIgnoreCase(usuarioActual.getRol())) {
            for (int i = 0; i < investigadores.size(); i++) {
                if (investigadores.get(i).getId() == usuarioActual.getId()) {
                    combo.setSelectedIndex(i);
                    break;
                }
            }
            combo.setEnabled(false);
        }

        return combo;
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
        caso.setIdTipoIrregularidad(cbTipoIrregularidad.getSelectedIndex() + 1);
        caso.setIdSubtipoIrregularidad(cbSubtipo.getSelectedIndex() + 1);
        caso.setIdAccionRealizada(cbAccion.getSelectedIndex() + 1);

        int selectedIdx = cbInvestigador.getSelectedIndex();
        if (selectedIdx >= 0 && selectedIdx < investigadores.size()) {
            caso.setIdInvestigador(investigadores.get(selectedIdx).getId());
        } else {
            JOptionPane.showMessageDialog(this, "No hay investigadores disponibles para asignar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!casoController.guardarCaso(caso, usuarioActual, duracionStr)) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Caso registrado.");
        configurarVista(this, InicioClient.obtenerInicio(usuarioActual.getRol()));
    }
}
