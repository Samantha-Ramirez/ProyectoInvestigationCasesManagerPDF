package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;
import java.util.List;

/*
 * Vista de la bandeja de casos para investigadores. Muestra los casos asignados y el tiempo sin
 * atención.
 */
public class BandejaView extends BaseView {
    public BandejaView(int idUsuario) {
        super("Bandeja de casos");
        inicializarComponentesEspecificos();
        cargarDatos(idUsuario);
        setVisible(true);
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Bandeja de casos", "Registrar");

        String[] columnas = {"Caso", "Tiempo", "Status", "Acción"};
        configurarTabla(columnas);
    }

    private void cargarDatos(int idUsuario) {
        CasoDAO dao = new CasoDAO();
        List<Caso> casos = dao.consultarCasosInvestigador(idUsuario);

        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝"});
        }
    }
}
