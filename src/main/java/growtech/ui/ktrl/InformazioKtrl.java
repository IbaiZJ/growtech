package growtech.ui.ktrl;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import growtech.ui.modeloak.InformazioKudeatzailea;

public class InformazioKtrl implements TreeSelectionListener {
    InformazioKudeatzailea informazioKudeatzailea;

    public InformazioKtrl(InformazioKudeatzailea informazioKudeatzailea) {
        this.informazioKudeatzailea = informazioKudeatzailea;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getNewLeadSelectionPath();
        if (path != null) {
            DefaultMutableTreeNode aukeratutakoNodoa = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (aukeratutakoNodoa.isLeaf()) {
                informazioKudeatzailea.textAreaInformazioaAldatu(path);
            }
        }
    }

}
