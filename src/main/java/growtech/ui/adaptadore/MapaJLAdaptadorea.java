package growtech.ui.adaptadore;

import javax.swing.*;

import growtech.util.negutegiKudeaketa.Negutegia;

import java.awt.*;

public class MapaJLAdaptadorea implements ListCellRenderer<Negutegia> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Negutegia> list, Negutegia value, int index,
            boolean isSelected, boolean cellHasFocus) {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel testua = new JLabel(value.getHerria() + ", " + value.getLurraldea());
        testua.setFont(new Font(null));
        testua.setForeground(Color.BLACK);

        if(isSelected) panela.setBackground(Color.LIGHT_GRAY);
            
        panela.add(testua);

        return panela;
    }
}
