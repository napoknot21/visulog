package up.visulog.ui.views.objects.chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.Map;

public class BarChartButton extends ChartButton {

    public BarChartButton(String label) {
        super(label);
    }

    @Override
    public void update(String chartName) {

        BarChart<Number, String> chart = new BarChart<>(getYAxis(), getXAxis());
        chart.getData().add(getData());
        this.setChart(chart);
    }

    private XYChart.Series<Number, String> getData() { //Recupere les donnes du plugin pour l"utiliser dans le graphe
        Map<String, Integer> result = getModel().getResultAsMap();
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

    @Override
    public void setup(VisulogScene scene) {
        super.setup(scene);
    }
}
