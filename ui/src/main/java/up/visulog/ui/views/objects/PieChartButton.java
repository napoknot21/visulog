package up.visulog.ui.views.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PieChartButton extends ChartButton{

    public PieChartButton (String label) {
        super(label);
        //Fixme: made PieChartButton preSelected
    }

    @Override
    public void update() {
        setChart(new PieChart((ObservableList<PieChart.Data>)getData()));
    }

    protected ObservableList<PieChart.Data> getData() {
        Map<String, Integer> result = getModel().getResultAsMap();
        LinkedList<PieChart.Data> list = new LinkedList<>();
        result.forEach((key,value) -> {
            list.add(new PieChart.Data(key,value));
        });
        return FXCollections.observableArrayList(list);
        }

    @Override
    public void setup(VisulogScene scene) {
        super.setup(scene);
    }
}
