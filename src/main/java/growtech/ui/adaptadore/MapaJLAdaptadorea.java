package growtech.ui.adaptadore;

import javax.swing.*;

import growtech.ui.Negutegia;

import java.awt.*;

public class MapaJLAdaptadorea implements ListCellRenderer<Negutegia> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Negutegia> list, Negutegia value, int index,
            boolean isSelected, boolean cellHasFocus) {
        JPanel panela = new JPanel(new BorderLayout());
        JLayeredPane panelaLayered = new JLayeredPane();
        panelaLayered.setPreferredSize(new Dimension(200,200));
        
        JLabel irudia = new JLabel(new ImageIcon(getClass().getResource(
            "/img/negutegiak/istockphoto-505307313-612x612.jpg")));
        irudia.setBounds(0, 0, 200, 200);
        panelaLayered.add(irudia, Integer.valueOf(0));

        JLabel text = new JLabel("Texto encima de la imagen", SwingConstants.CENTER);
        text.setBounds(0, 0, 400, 300); // Posicionarlo en el centro
        text.setForeground(Color.WHITE); // Cambiar color del texto para contraste
        text.setFont(new Font("Arial", Font.BOLD, 20));
        panelaLayered.add(text, Integer.valueOf(1));

        panela.add(panelaLayered);

        return panela;
    }
}
