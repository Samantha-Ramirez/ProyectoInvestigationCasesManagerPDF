package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;
import java.util.List;

/*
 * Vista de la cartelera de casos para administradores. Muestra los casos asignados y el tiempo sin
 * atención.
 */
public class CarteleraView extends BaseView {
    // Configurar la vista de cartelera de casos
    public CarteleraView() {
        super("Cartelera de casos", true);
        cargarDatos(this.usuarioActual.getId());
    }

    // Configurar componentes específicos de esta vista
    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Cartelera de casos", "Registrar", e -> {
            configurarVista(this, new RegistroCasoView());
        });

        String[] columnas = {"Caso", "Tiempo", "Status", "Acción"};
        configurarTabla(columnas);
    }

    // Cargar todos los casos y mostrar en la tabla
    private void cargarDatos(int idUsuario) {
        CasoDAO dao = new CasoDAO();
        List<Caso> casos = dao.consultarCasosAdministrador(idUsuario);

        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝"});
        }
    }
}
