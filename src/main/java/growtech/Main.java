package growtech;

import java.awt.Insets;
import java.awt.Toolkit;
import java.lang.reflect.InaccessibleObjectException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.util.SystemInfo;

import growtech.config.AppKonfigurazioa;
import growtech.theme.TemaKudeatzailea;
import growtech.ui.ItxuraPrintzipala;

public class Main {

    public static void main(String[] args) {

        if(SystemInfo.isLinux) {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);

            try {
                var toolkit = Toolkit.getDefaultToolkit();
                var awtAppClassNameField = toolkit.getClass().getDeclaredField("awtAppClassName");
                awtAppClassNameField.setAccessible(true);
                awtAppClassNameField.set(toolkit, AppKonfigurazioa.APP_IZENA);
            } catch (NoSuchFieldException | InaccessibleObjectException | IllegalAccessException e) {
                System.out.println("Arazoa izena jartzerakoan!!!");
            }
        }

        // FlatLaf.registerCustomDefaultsSource("com.growtech.theme.custom");
        // FlatLaf.setup(new com.formdev.flatlaf.FlatLightLaf());
        
        // Konponente hauen diseinua aldatu
        final int rounding = 8;
        final int insets = 2;
        
        UIManager.put("CheckBox.icon.style", "filled");
        UIManager.put("Component.arrowType", "chevron");
        
        // UIManager.put("MenuBar.selectionBackground", Color.LIGHT_GRAY);
        // UIManager.put("Menu.selectionBackground", Color.LIGHT_GRAY);
        
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 1);
        
        UIManager.put("Button.arc", rounding);
        UIManager.put("Component.arc", rounding);
        UIManager.put("ProgressBar.arc", rounding);
        UIManager.put("TextComponent.arc", rounding);
        
        UIManager.put("ScrollBar.thumbArc", rounding);
        UIManager.put("ScrollBar.thumbInsets", new Insets(insets, insets, insets, insets));
        
        TemaKudeatzailea.ezarriArgiaTema();

        
        // AdminUserDialogoa adminUserDialogoa = new AdminUserDialogoa(null);
        // if(!adminUserDialogoa.isITXI_DA_X()) 
            new ItxuraPrintzipala();
    }
}