package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.model.*;
import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.factory.InicioClient;
import com.ucv.investigationcasesmanager.mediator.RegistroMediator;
import javax.swing.*;

public class RegistroCasoView extends BaseView {
    // Campos de texto y combos basados en el modelo Caso
    private JTextField txtNroExpediente, txtMovil, txtObjetivo, txtIncidencia, txtDuracion;
    private JTextArea txtModusOperandi, txtAreaApoyo, txtDeteccion, txtDiagnostico, txtConclusiones,
            txtObservaciones, txtSoporte;
    private JComboBox<String> cbTipoCaso, cbInvestigador, cbTipoIrregularidad, cbSubtipo, cbAccion;

    // Configurar la vista de registro de casos
    public RegistroCasoView() {
        super("Registro de casos", true);
    }

    // Configurar componentes específicos de esta vista
    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Registro de casos", null, null);
        configurarFormulario();

        txtNroExpediente = new JTextField("Nro. Expediente");
        cbTipoCaso = new JComboBox<>(new String[] {"Gestión", "Reclamo", "Caso"});
        cbInvestigador = new JComboBox<>(new String[] {"Investigador 1", "Investigador 2"});
        txtMovil = new JTextField("Móvil afectado");
        txtObjetivo = new JTextField("Objetivo / Agraviado");
        txtIncidencia = new JTextField("Incidencia");
        txtDuracion = new JTextField("Duración (Días)");
        cbTipoIrregularidad = new JComboBox<>(new String[] {"Tipo Irregularidad 1", "Tipo 2"});
        cbSubtipo = new JComboBox<>(new String[] {"Subtipo A", "Subtipo B"});
        cbAccion = new JComboBox<>(new String[] {"Acción Realizada 1", "Acción 2"});
        txtModusOperandi = new JTextArea("Descripción Modus Operandi", 3, 20);
        txtAreaApoyo = new JTextArea("Área Apoyo a Resolver", 2, 20);
        txtDeteccion = new JTextArea("Detección / Procedencia del Caso", 2, 20);
        txtDiagnostico = new JTextArea("Diagnóstico / Detalle de Comprobación", 3, 20);
        txtConclusiones = new JTextArea("Conclusiones / Recomendaciones", 3, 20);
        txtObservaciones = new JTextArea("Observaciones", 2, 20);
        txtSoporte = new JTextArea("Soporte", 2, 20);
        agregarCampoFormulario(txtNroExpediente);
        agregarCampoFormulario(cbTipoCaso);
        agregarCampoFormulario(cbInvestigador);
        agregarCampoFormulario(txtMovil);
        agregarCampoFormulario(txtObjetivo);
        agregarCampoFormulario(txtIncidencia);
        agregarCampoFormulario(txtDuracion);
        agregarCampoFormulario(cbTipoIrregularidad);
        agregarCampoFormulario(cbSubtipo);
        agregarCampoFormulario(cbAccion);
        agregarCampoFormulario(txtModusOperandi);
        agregarCampoFormulario(txtAreaApoyo);
        agregarCampoFormulario(txtDeteccion);
        agregarCampoFormulario(txtDiagnostico);
        agregarCampoFormulario(txtConclusiones);
        agregarCampoFormulario(txtObservaciones);
        agregarCampoFormulario(txtSoporte);

        agregarBotonAccionPrincipal("Registrar", e -> accionRegistrar());
    }

    // Registrar un caso nuevo, en la base de datos
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
