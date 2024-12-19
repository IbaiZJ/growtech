package growtech.ui.adaptadore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import growtech.util.IrudiaTestuFormatua;
import growtech.util.userKudeaketa.Erabiltzailea;

public class negutegiInfoPanelAdaptadore implements ListCellRenderer<Erabiltzailea> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Erabiltzailea> list, Erabiltzailea value, int index,
            boolean isSelected, boolean cellHasFocus) {
        JPanel panela = new JPanel(new BorderLayout());

        StringBuilder izena = new StringBuilder(value.getIzena()).
            append(" ").append(value.getAbizena());

        panela.add(IrudiaTestuFormatua.sortuTestuaFormatuarekin(
            izena.toString(), new Font("Arial", Font.BOLD, 18), Color.BLACK));

        return panela;
    }

}
