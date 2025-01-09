package growtech.util;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import growtech.mqtt.MQTTDatuak;

public class Grafikoa {
    static DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    public static LocalDate data = LocalDate.now();

    public static Component sortuTenpHezGrafikoa() {
        JPanel panela = new JPanel(new BorderLayout());

        grafikoDatasetEzarri();

        JFreeChart chart = ChartFactory.createLineChart(
                "IZENA",
                "",
                "",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        // Grafikoa
        CategoryPlot plot = chart.getCategoryPlot();

        // Lineen kolorea ezarri
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, new Color(255, 102, 102));
        renderer.setSeriesPaint(1, new Color(102, 178, 255));

        // Puntuak jarri
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);

        // Linea suabeak
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.getDomainAxis().setTickLabelsVisible(false);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));
        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.PLAIN, 14));
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.PLAIN, 14));
        plot.getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.getRangeAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 12));

        ChartPanel chartPanel = new ChartPanel(chart);

        panela.add(chartPanel);

        return panela;
    }

    public static void grafikoDatasetEzarri() {
        try {
            List<File> aurkitutakoArtxiboak = Files.list(Paths.get(MQTTDatuak.FITXERO_PATH))
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .filter(file -> file.getName().startsWith(String.valueOf(data)))
                    .collect(Collectors.toList());
            if (!aurkitutakoArtxiboak.isEmpty()) {
                for (File file : aurkitutakoArtxiboak) {
                    String datua;
                    int i = 0;
                    String getfile = String.valueOf(file);
                    String[] topic = getfile.split(" ");
                    String[] topicIzena = topic[1].split("\\.");

                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        while ((datua = br.readLine()) != null) {
                            dataset.addValue(Double.parseDouble(datua), topicIzena[0], String.valueOf(i));
                            i++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
