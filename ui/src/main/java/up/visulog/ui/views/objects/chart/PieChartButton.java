package up.visulog.ui.views.objects.chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.LinkedList;
import java.util.Map;

/**
 * GUI PieChart generator button
 *
 * @see ChartButton,ChartButtons
 */
public class PieChartButton extends ChartButton {

    public PieChartButton(String label) {
        super(label);
    }

    /**
     * Initialise l'affichage curseur sur le graph
     *
     * @param dataList reprenste le jeu de donnee
     */
    private static void setTooltip(ObservableList<PieChart.Data> dataList) {
        double per = 0;
        for (PieChart.Data d : dataList) per += d.getPieValue();
        for (PieChart.Data d : dataList) {
            d.getNode().setCursor(Cursor.HAND);
            Tooltip t = new Tooltip(cutName(d.getName()) + (int) (d.getPieValue() / per * 100) + "%");
            Tooltip.install(d.getNode(), t);
        }
    }

    /**
     * Coupe le nom pour ne pas garder l'adresse mail
     *
     * @param s represnete le nom a coupe
     * @return le nom coupe
     */
    private static String cutName(String s) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length() - 1; i++) {
            res.append(s.charAt(i));
            if (s.charAt(i + 1) == '<') return res.toString();
        }
        return res + " ";
    }

    @Override
    public void update(String chartName) {
        for (int i = 0; i < getModel().getResultAsMap().size(); i++) {
            Map<String, Integer> map = getModel().getResultAsMap().get(i);
            setRegularOrOtherData(getData(map), getModel().getCurrentPlugin(), i);
        }
    }

    /**
     * Choisit si les donnéees sont suffisament grande pour etre mise individuellement
     *
     * @param data      reprensenete le jeu de donnee
     * @param chartName reprensete le nom du bouton
     * @param i         reprensete la position du dans le tableau de LEGEND
     */
    private void setRegularOrOtherData(ObservableList<PieChart.Data> data, String chartName, int i) {
        ObservableList<PieChart.Data> newData = groupData(5, data);
        PieChart chart;
        if (newData.size() < 7)
            chart = new PieChart(data);
        else chart = new PieChart(newData);
        if (Model.LEGEND.containsKey(chartName)) {
            chart.setTitle(Model.LEGEND.get(chartName)[i]);
        }
        addChart(chart);
        chart.setLegendVisible(false);
        chart.setLabelsVisible(true);
        if (newData.size() < 7) setTooltip(data);
        else setTooltip(newData);
    }

    /**
     * Recupere les donnees du plugin pour l'utiliser dans le graphe
     *
     * @return le jeu de donnée obtenue depuis le resultat du plugin
     */
    private ObservableList<PieChart.Data> groupData(int n, ObservableList<PieChart.Data> data) {
        ObservableList<PieChart.Data> newData = FXCollections.observableArrayList();
        PieChart.Data other = new PieChart.Data("Autres", 0);
        for (PieChart.Data d : data) {
            if (d.getPieValue() > n) newData.add(d);
            else {
                other.setPieValue(other.getPieValue() + d.getPieValue());
            }
        }
        newData.add(other);
        return newData;
    }

    /**
     * Recupere les donnees du plugin pour les utiliser dans le graphe
     *
     * @param result represente le resultat du plugin lance
     * @return un jeu de donne utilisable par PieChart
     */
    protected ObservableList<PieChart.Data> getData(Map<String, Integer> result) {
        LinkedList<PieChart.Data> list = new LinkedList<>();
        result.forEach((key, value) -> list.add(new PieChart.Data(key, value)));
        return FXCollections.observableArrayList(list);
    }

    @Override
    public void setup(VisulogScene scene) {
        super.setup(scene);
    }
}
