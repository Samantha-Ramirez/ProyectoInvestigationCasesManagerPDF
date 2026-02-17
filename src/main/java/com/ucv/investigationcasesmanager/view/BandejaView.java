package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;
import java.util.List;

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
        List<Caso> casos = dao.listarCasosPorInvestigador(idUsuario);

        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝"});
        }
    }
}
