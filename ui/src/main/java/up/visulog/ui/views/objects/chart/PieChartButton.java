package up.visulog.ui.views.objects.chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.LinkedList;
import java.util.Map;

public class PieChartButton extends ChartButton {

    public PieChartButton(String label) {
        super(label);
    }

    @Override
    public void update(String chartName) {
        Chart chart = new PieChart(getData());
        chart.setTitle(chartName);
        setChart(chart);
    }

    protected ObservableList<PieChart.Data> getData() { //Recupere les donnes du plugin pour l'utiliser dans le graphe
        Map<String, Integer> result = getModel().getResultAsMap();
        LinkedList<PieChart.Data> list = new LinkedList<>();
        result.forEach((key, value) -> list.add(new PieChart.Data(key, value)));
        return FXCollections.observableArrayList(list);
    }

    @Override
    public void setup(VisulogScene scene) {
        super.setup(scene);
    }
}
