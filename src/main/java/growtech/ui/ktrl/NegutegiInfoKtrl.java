package growtech.ui.ktrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import growtech.ui.modeloak.NegutegiInfoKudeatzailea;

public class NegutegiInfoKtrl implements ActionListener {
    private NegutegiInfoKudeatzailea negutegiInfoKudeatzailea;

    public NegutegiInfoKtrl(NegutegiInfoKudeatzailea negutegiInfoKudeatzailea) {
        this.negutegiInfoKudeatzailea = negutegiInfoKudeatzailea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("onOffBotoia")) {
            negutegiInfoKudeatzailea.onOffBotoiaExekutatu();
        }
        if(e.getActionCommand().equals("historialaBotoia")) {
            negutegiInfoKudeatzailea.sortuHistorialTab();
        }
        if(e.getActionCommand().equals("informazioBotoia")) {
            negutegiInfoKudeatzailea.sortuInformazioTab();
        }
    }

}
