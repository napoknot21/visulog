package up.visulog.ui.views.objects.chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        ObservableList<PieChart.Data> newData = groupData();
        Chart chart = new PieChart(newData);
        /*for(PieChart.Data data: getData()){
            Tooltip tooltip = new Tooltip(data.getName() + ": " + data.getPieValue() +"%");
            Tooltip.install(data.getNode(), tooltip);

            //respond to change in value
            data.pieValueProperty().addListener((observable, oldPieValue, newPieValue)->{
                tooltip.setText(data.getName() + ": " + newPieValue + "%");
            });
        }*/
        chart.setTitle(chartName);
        setChart(chart);
    }

    public ObservableList<PieChart.Data> groupData(){
        ObservableList<PieChart.Data> newData = FXCollections.observableArrayList();
        PieChart.Data other = new PieChart.Data("Autres", 0);
        for(PieChart.Data d : getData()){
            if(d.getPieValue() > 5) newData.add(d);
            else {
                other.setPieValue(other.getPieValue() + d.getPieValue());
            }
        }
        newData.add(other);
        return newData;
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
