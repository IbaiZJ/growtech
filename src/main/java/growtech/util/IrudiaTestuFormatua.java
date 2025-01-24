package growtech.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IrudiaTestuFormatua {

    public static ImageIcon irudiaEskalatu(URL irudiPath, int x, int y) {
        ImageIcon ikonoOriginala = new ImageIcon(irudiPath);
        Image eskalatutakoIrudia = ikonoOriginala.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon eskalatutaIkonoa = new ImageIcon(eskalatutakoIrudia);
        return eskalatutaIkonoa;
    }

    public static Component sortuTestuaFormatuarekin(String testua, Font font, Color color) {
        JLabel label = new JLabel(testua);
        label.setFont(font);
		label.setForeground(color);
        return label;
    }
}
