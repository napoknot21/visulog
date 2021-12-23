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
        XYChart.Series<Number, String> newdata = groupData(5);
        BarChart<Number, String> chart = new BarChart<>(getYAxis(), getXAxis());
        chart.getData().add(newdata);
        this.setChart(chart);
    }

    private XYChart.Series<Number, String> getData() { //Recupere les donnees du plugin pour l"utiliser dans le graphe
        Map<String, Integer> result = getModel().getResultAsMap();
        //String s = getModel().getCurrentPlugin();
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

    private XYChart.Series<Number, String> groupData(int n){
        XYChart.Series<Number, String> newData = new XYChart.Series<>();
        XYChart.Data other = new XYChart.Data<>(0, "Autres");
        for(XYChart.Data d : getData().getData()){
            if((int)d.getXValue() > n){
                newData.getData().add(d);
            }else{
                other.setXValue((int)other.getXValue() + (int)d.getXValue());
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
