package growtech.ui.panelak;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import growtech.ui.ktrl.InformazioKtrl;
import growtech.ui.modeloak.InformazioKudeatzailea;

public class InformazioPanela implements PropertyChangeListener {
    private InformazioKtrl informazioKtrl;
    private InformazioKudeatzailea informazioKudeatzailea;
    private JEditorPane testua;
    private JScrollPane infoPanela;

    public InformazioPanela() {
        informazioKudeatzailea = new InformazioKudeatzailea(this);
        informazioKudeatzailea.addPropertyChangeListener(this);
        informazioKtrl = new InformazioKtrl(informazioKudeatzailea);
    }

    public Component sortuInformazioPanela() {
        JSplitPane panela = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, sortuJanariAukerak(), informazioPanelTestua());

        panela.setDividerLocation(300);
        panela.setDividerSize(10);

        return panela;
    }

    private Component informazioPanelTestua() {

        testua = new JEditorPane();
        testua.setContentType("text/html");
        testua.setEditable(false);

        infoPanela = new JScrollPane(testua);

        return infoPanela;
    }

    private Component sortuJanariAukerak() {
        DefaultMutableTreeNode janaria = new DefaultMutableTreeNode("Janaria");
        DefaultMutableTreeNode fruta = new DefaultMutableTreeNode("Frutak");
        DefaultMutableTreeNode barazki = new DefaultMutableTreeNode("Barazkiak");
        janaria.add(fruta);
        janaria.add(barazki);

        List<String> barazkiZerrenda = datuIzenakJaso("src/main/resources/Janaria/Barazkiak");
        for (String i : barazkiZerrenda) {
            barazki.add(new DefaultMutableTreeNode(i));
        }
        List<String> frutaZerrenda = datuIzenakJaso("src/main/resources/Janaria/Frutak");
        for (String i : frutaZerrenda) {
            fruta.add(new DefaultMutableTreeNode(i));
        }

        JTree tree = new JTree(janaria);
        tree.addTreeSelectionListener(informazioKtrl);
        JScrollPane panela = new JScrollPane(tree,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return panela;
    }

    public List<String> datuIzenakJaso(String path) {
        List<String> fitxeroIzenak = new ArrayList<>();
        File karpeta = new File(path);

        if (karpeta.exists() && karpeta.isDirectory()) {
            File[] fitxero = karpeta.listFiles();
            if (fitxero != null) {
                for (File artxiboa : fitxero) {
                    if (artxiboa.isFile()) {
                        String artxiboIzena = artxiboa.getName();
                        if (artxiboIzena.endsWith(".md")) {
                            artxiboIzena = artxiboIzena.substring(0, artxiboIzena.length() - 3);
                        }
                        fitxeroIzenak.add(artxiboIzena);
                    }
                }
            }
        } else {
            System.out.println("Ezin da path-a irakurri");
        }

        return fitxeroIzenak;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propietatea = evt.getPropertyName();

        if (propietatea.equals(InformazioKudeatzailea.P_TESTUA_ALDATU)) {
            String mdPath = (String) evt.getNewValue();
            try {
                String md = new String(Files.readAllBytes(Paths.get(mdPath)));
                Parser parser = Parser.builder().build();
                HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
                String htmlTestua = htmlRenderer.render(parser.parse(md));
                testua.setText(htmlTestua);

                // testua.setText egiterakoan, panela ez bajatzeko testua oso luzea bada, 
                // gora igotzeko balio du kodigo honek
                SwingUtilities.invokeLater(() -> {
                    JScrollBar verticalScrollBar = infoPanela.getVerticalScrollBar();
                    if (verticalScrollBar != null) {
                        verticalScrollBar.setValue(0);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                testua.setText("Errorea .md-a kargatzerakoan");
            }
        }
    }

}
