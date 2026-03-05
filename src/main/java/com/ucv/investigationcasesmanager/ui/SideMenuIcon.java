package com.ucv.investigationcasesmanager.ui;

import javax.swing.Icon;
import java.awt.*;
import java.awt.geom.*;

/**
 * PDyF: Fábrica de íconos vectoriales para el menú lateral, dibujados con Java2D.
 * Cada ícono es una clase interna que implementa Icon sin dependencias externas ni fuentes
 * especiales, garantizando visualización correcta en cualquier JVM.
 */
public final class SideMenuIcon {

    private static final Color COLOR = new Color(67, 67, 67);
    private static final int SIZE = 18;

    private SideMenuIcon() {}

    /** Ícono de casa para el ítem "Inicio". */
    public static Icon home() { return new HomeIcon(); }

    /** Ícono de descarga (flecha hacia abajo + bandeja) para "Reportes". */
    public static Icon download() { return new DownloadIcon(); }

    /** Ícono de etiqueta/tag para "Auditoría". */
    public static Icon tag() { return new TagIcon(); }

    /** Ícono de círculo con signo más para "Entidades". */
    public static Icon plusCircle() { return new PlusCircleIcon(); }

    /** Ícono de papelera para "Archivos Negados". */
    public static Icon trash() { return new TrashIcon(); }

    /** Ícono de flecha de salida para "Cerrar sesión". */
    public static Icon logout() { return new LogoutIcon(); }

    /** Ícono de lápiz-cuadro para la columna "Acción" (editar) de las tablas. */
    public static Icon edit() { return new EditIcon(); }

    // ─────────────────────────────────────────────────────────────────────────
    // Base
    // ─────────────────────────────────────────────────────────────────────────

    private abstract static class BaseIcon implements Icon {
        @Override public int getIconWidth()  { return SIZE; }
        @Override public int getIconHeight() { return SIZE; }

        protected Graphics2D setup(Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_PURE);
            g2.setColor(COLOR);
            g2.translate(x, y);
            return g2;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Home (casa sólida con puerta hueca)
    // ─────────────────────────────────────────────────────────────────────────
    private static final class HomeIcon extends BaseIcon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = setup(g, x, y);

            // Por qué: se usa Area para restar la puerta del cuerpo sin usar
            // relleno blanco, que fallaría en fondos no blancos.
            GeneralPath house = new GeneralPath();
            house.moveTo(9, 1);    // punta del tejado
            house.lineTo(17, 9);   // extremo derecho del tejado
            house.lineTo(14, 9);   // hombro derecho
            house.lineTo(14, 17);  // esquina inferior derecha
            house.lineTo(4, 17);   // esquina inferior izquierda
            house.lineTo(4, 9);    // hombro izquierdo
            house.lineTo(1, 9);    // extremo izquierdo del tejado
            house.closePath();

            Area houseArea = new Area(house);
            // Puerta: rectángulo inferior centrado
            houseArea.subtract(new Area(new Rectangle2D.Float(6.5f, 11, 5, 6)));
            g2.fill(houseArea);

            g2.dispose();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Download (flecha hacia abajo + bandeja de tres lados)
    // ─────────────────────────────────────────────────────────────────────────
    private static final class DownloadIcon extends BaseIcon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = setup(g, x, y);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Flecha: eje vertical + cabeza triangular
            g2.drawLine(9, 1, 9, 11);
            int[] ax = {4, 9, 14};
            int[] ay = {8, 14, 8};
            g2.fillPolygon(ax, ay, 3);

            // Bandeja (U sin la base superior): izquierda → abajo → derecha
            g2.drawLine(2, 12, 2, 16);
            g2.drawLine(2, 16, 16, 16);
            g2.drawLine(16, 16, 16, 12);

            g2.dispose();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Tag / label (etiqueta de precio con orificio)
    // ─────────────────────────────────────────────────────────────────────────
    private static final class TagIcon extends BaseIcon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = setup(g, x, y);

            // Forma de etiqueta: punta izquierda + cuerpo cuadrado
            GeneralPath tag = new GeneralPath();
            tag.moveTo(2, 9);    // punta izquierda
            tag.lineTo(7, 2);    // esquina superior izquierda
            tag.lineTo(16, 2);   // esquina superior derecha
            tag.lineTo(16, 16);  // esquina inferior derecha
            tag.lineTo(7, 16);   // esquina inferior izquierda
            tag.closePath();

            Area tagArea = new Area(tag);
            // Orificio redondo en la esquina superior derecha
            tagArea.subtract(new Area(new Ellipse2D.Float(11, 4, 3.5f, 3.5f)));
            g2.fill(tagArea);

            g2.dispose();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Plus-circle (círculo + signo más)
    // ─────────────────────────────────────────────────────────────────────────
    private static final class PlusCircleIcon extends BaseIcon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = setup(g, x, y);

            // Círculo exterior
            g2.setStroke(new BasicStroke(1.8f));
            g2.drawOval(1, 1, 16, 16);

            // Signo más centrado
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(9, 5, 9, 13);   // vertical
            g2.drawLine(5, 9, 13, 9);   // horizontal

            g2.dispose();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Trash (papelera con tapa y líneas internas)
    // ─────────────────────────────────────────────────────────────────────────
    private static final class TrashIcon extends BaseIcon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = setup(g, x, y);
            g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Tapa (asa + barra horizontal)
            g2.drawLine(7, 2, 11, 2);   // asa
            g2.drawLine(3, 4, 15, 4);   // barra de la tapa

            // Cuerpo rectangular
            g2.drawRect(4, 5, 10, 11);

            // Líneas internas (contenido)
            g2.drawLine(7, 7, 7, 13);
            g2.drawLine(11, 7, 11, 13);

            g2.dispose();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Logout (flecha saliendo hacia la derecha + marco de puerta)
    // ─────────────────────────────────────────────────────────────────────────
    private static final class LogoutIcon extends BaseIcon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = setup(g, x, y);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Flecha horizontal apuntando a la derecha
            g2.drawLine(1, 9, 12, 9);     // eje
            g2.drawLine(8, 5, 13, 9);     // mitad superior de la punta
            g2.drawLine(8, 13, 13, 9);    // mitad inferior de la punta

            // Marco de puerta en el borde derecho (L rotada: arriba + derecha + abajo)
            g2.drawLine(11, 2, 16, 2);    // borde superior
            g2.drawLine(16, 2, 16, 16);   // borde derecho (vertical)
            g2.drawLine(11, 16, 16, 16);  // borde inferior

            g2.dispose();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Edit (lápiz diagonal sobre cuadro – ícono estándar de "editar")
    // ─────────────────────────────────────────────────────────────────────────
    private static final class EditIcon extends BaseIcon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = setup(g, x, y);
            g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Por qué: el ícono se dibuja con Java2D para garantizar visibilidad en cualquier JVM,
            // sin depender de fuentes Unicode como Arial que no incluyen ✎ en todos los sistemas.

            // Cuadro del documento (tres lados, esquina inferior-derecha abierta para el lápiz)
            g2.drawLine(2, 14, 2, 2);   // lado izquierdo
            g2.drawLine(2, 2, 12, 2);   // lado superior
            g2.drawLine(12, 2, 12, 7);  // lado derecho (parcial)
            g2.drawLine(2, 14, 7, 14);  // lado inferior (parcial)

            // Cuerpo del lápiz: paralelogramo estrecho a 45°
            GeneralPath pencil = new GeneralPath();
            pencil.moveTo(7,  14);   // esquina inferior-izquierda
            pencil.lineTo(15,  6);   // esquina superior-derecha
            pencil.lineTo(13,  4);   // esquina superior-izquierda
            pencil.lineTo(5,  12);   // esquina inferior-derecha del cuerpo
            pencil.closePath();
            g2.fill(pencil);

            // Punta del lápiz (triángulo)
            GeneralPath tip = new GeneralPath();
            tip.moveTo(5,  12);
            tip.lineTo(4,  15);   // vértice de la punta
            tip.lineTo(7,  14);
            tip.closePath();
            g2.fill(tip);

            g2.dispose();
        }
    }
}

