package growtech.ui.ktrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import growtech.ui.modeloak.NegutegiInfoKudeatzailea;

public class NegutegiInfoKtrl implements ActionListener, ChangeListener {
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

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) e.getSource();

            if(slider.getName().equals("haizagailuaSlider")) {
                negutegiInfoKudeatzailea.haizagailuSliderKudeatu(slider.getValue());
            }
        }
    }
}
