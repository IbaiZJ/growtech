package growtech.ui.modeloak;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.tree.TreePath;

import growtech.ui.panelak.InformazioPanela;

public class InformazioKudeatzailea {
    private PropertyChangeSupport aldaketak;
    public final static String P_TESTUA_ALDATU = "P_TESTUA_ALDATU";

    public InformazioKudeatzailea(InformazioPanela informazioPanela) {
        aldaketak = new PropertyChangeSupport(informazioPanela);
    }

    public void textAreaInformazioaAldatu(TreePath path) {
        StringBuilder pathOsoa = new StringBuilder();
        for (Object i : path.getPath()) {
            pathOsoa.append(i.toString()).append("/");
        }
        if (pathOsoa.length() > 0) {
            pathOsoa.setLength(pathOsoa.length() - 1);
        }

        aldaketak.firePropertyChange(P_TESTUA_ALDATU, null, "src/main/resources/" + pathOsoa.toString() + ".md");
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.removePropertyChangeListener(listener);
    }
}
