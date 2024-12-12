package growtech.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import lombok.Getter;

public class AdminUserDialogoa extends JDialog {
    public @Getter boolean ITXI_DA_X = true;

    public AdminUserDialogoa(JFrame leihoa) {
        super(leihoa, "Grow Tech", true);
        this.setSize(500,400);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png")); 
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setContentPane(sortuDialogoPanela());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        return panela;
    }
}
