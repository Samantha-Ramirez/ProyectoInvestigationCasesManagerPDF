package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;
import java.util.List;

/*
 * Vista de la bandeja de casos para investigadores. Muestra los casos asignados y el tiempo sin
 * atención.
 */
public class BandejaView extends BaseView {
    // Configurar la vista de bandeja de casos para el investigador
    public BandejaView() {
        super("Bandeja de casos", true);
        cargarDatos(this.usuarioActual.getId());
    }

    // Configurar componentes específicos de esta vista
    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Bandeja de casos", "Registrar", e -> {
            configurarVista(this, new RegistroCasoView());
        });

        String[] columnas = {"Caso", "Tiempo", "Status", "Acción"};
        configurarTabla(columnas);
    }

    // Cargar los casos asignados al investigador y mostrar en la tabla
    private void cargarDatos(int idUsuario) {
        CasoDAO dao = new CasoDAO();
        List<Caso> casos = dao.consultarCasosInvestigador(idUsuario);

        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝"});
        }
    }
}
