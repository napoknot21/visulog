package up.visulog.ui.views.objects.chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.Map;

/**
 * GUI BarChart generator button
 */
public class BarChartButton extends ChartButton {

    public BarChartButton(String label) {
        super(label);
    }

    @Override
    public void update(String chartName) {
        BarChart<Number, String> chart = new BarChart<>(getYAxis(), getXAxis());
        getModel().getResultAsMap().forEach(map -> setRegularOrOtherData(getData(map), chart));
        this.addChart(chart);
    }

    /**
     * Choisit si les donnéees sont suffisament grande pour etre mise individuellement
     *
     * @param data  reprensenete le jeu de donnee
     * @param chart est le bar chart qui sera affiche
     */
    private void setRegularOrOtherData(XYChart.Series<Number, String> data, BarChart<Number, String> chart) {
        XYChart.Series<Number, String> newdata = groupData(5, data);
        if (newdata.getData().size() < 7) chart.getData().add(data);
        else chart.getData().add(newdata);
        chart.setLegendVisible(true);
        //chart.setTitle();
        int i = 0;
        for (XYChart.Series<Number, String> s : chart.getData()) {
            if (Model.LEGEND.containsKey(getModel().getCurrentPlugin())) {
                s.setName(Model.LEGEND.get(getModel().getCurrentPlugin())[i++]);
            }
        }
    }

    /**
     * Recupere les donnees du plugin pour l'utiliser dans le graphe
     *
     * @param result est le resultats du plugin actuel
     * @return le jeu de donnée obtenue depuis le resultat du plugin
     */
    private XYChart.Series<Number, String> getData(Map<String, Integer> result) {
        XYChart.Series<Number, String> data = new XYChart.Series<>();
        result.forEach((key, value) -> data.getData().add(new XYChart.Data<>(value, key)));
        return data;
    }

    private CategoryAxis getXAxis() {
        return new CategoryAxis();
    }

    private NumberAxis getYAxis() {
        return new NumberAxis();
    }

    /**
     * rassembel les petites valeur dans autre, les autres sont individuelles
     *
     * @param n    represnet ele nombre qualifiant une donne comme petite
     * @param data represente le jeu de donnee a trier
     * @return un jeu de donnee triee
     */
    private XYChart.Series<Number, String> groupData(int n, XYChart.Series<Number, String> data) {
        XYChart.Series<Number, String> newData = new XYChart.Series<>();
        XYChart.Data<Number, String> other = new XYChart.Data<>(0, "Autres");
        for (var d : data.getData()) {
            if ((int) d.getXValue() > n) {
                newData.getData().add(d);
            } else {
                other.setXValue((int) other.getXValue() + (int) d.getXValue());
            }

        }
        newData.getData().add(other);
        return newData;
    }

    @Override
    public void setup(VisulogScene scene) {
        super.setup(scene);
    }
}
