package growtech.theme;

import java.util.Collections;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class TemaKudeatzailea {

    public static void ezarriArgiaTema() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch(UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void ezarriIlunaTema() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch(UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public static void aktualizatuKoloreGlobala(String kolore) { // Predeterminatua = #42A5F5
        FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentBaseColor", kolore));

        Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();

        try {
            FlatLaf.setup(lafClass.newInstance());
            FlatLaf.updateUILater();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("Failed to change accent color");
        }
    }
}