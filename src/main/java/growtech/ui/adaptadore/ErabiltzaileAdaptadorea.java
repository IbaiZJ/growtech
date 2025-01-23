package growtech.ui.adaptadore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import growtech.util.userKudeaketa.ErabiltzaileMota;
import growtech.util.userKudeaketa.Erabiltzailea;

public class ErabiltzaileAdaptadorea implements ListCellRenderer<Erabiltzailea> {
    private boolean aukeratuDaiteke;

    public ErabiltzaileAdaptadorea(boolean aukeratuDaiteke) {
        this.aukeratuDaiteke = aukeratuDaiteke;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Erabiltzailea> list, Erabiltzailea value, int index,
            boolean isSelected, boolean cellHasFocus) {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel testua = new JLabel(value.getIzena() + " " + value.getAbizena());
        testua.setFont(new Font("Arial", Font.BOLD, 16));
        if (value.getMota() == ErabiltzaileMota.ADMIN)
            testua.setForeground(Color.BLUE);
        else
            testua.setForeground(Color.BLACK);
        testua.setHorizontalAlignment(SwingConstants.CENTER);

        if (isSelected && aukeratuDaiteke)
            panela.setBackground(Color.RED);

        panela.add(testua);

        return panela;
    }
}
