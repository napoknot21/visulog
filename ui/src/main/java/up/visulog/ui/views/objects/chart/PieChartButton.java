package up.visulog.ui.views.objects.chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.LinkedList;
import java.util.Map;

public class PieChartButton extends ChartButton {

    public PieChartButton(String label) {
        super(label);
    }

    @Override
    public void update(String chartName) {
        ObservableList<PieChart.Data> newData = groupData(5);

        Chart chart = new PieChart(newData);

        chart.setTitle(chartName);
        setChart(chart);
        chart.setLegendVisible(false);

        setTooltip(newData);
    }

    private ObservableList<PieChart.Data> groupData(int n){
        ObservableList<PieChart.Data> newData = FXCollections.observableArrayList();
        PieChart.Data other = new PieChart.Data("Autres", 0);
        for(PieChart.Data d : getData()){
            if(d.getPieValue() > n) newData.add(d);
            else {
                other.setPieValue(other.getPieValue() + d.getPieValue());
            }
        }
        newData.add(other);
        return newData;
    }

    private static void setTooltip(ObservableList<PieChart.Data> dataList){
        for(PieChart.Data d : dataList){
            d.getNode().setCursor(Cursor.HAND);
            Tooltip t = new Tooltip(d.getName());
            Tooltip.install(d.getNode(), t);
        }
    }

    protected ObservableList<PieChart.Data> getData() { //Recupere les donnees du plugin pour les utiliser dans le graphe
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
