package up.visulog.ui.views.objects;

import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.control.RadioButton;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.List;

public abstract class ChartButton extends RadioButton
implements ChartButtons, SceneChild {

    private String chartName;
    private Model model;
    private Chart chart;

    public ChartButton (String label) {
        super(label);
        String v = "";
        if (ChartButton.NAME_TO_CHART_FILTER.containsKey(label)) v = ChartButton.NAME_TO_CHART_FILTER.get(label);
        this.chartName = v;
        this.setToggleGroup(ChartButton.GROUP);
    }

    public String getChartName() {
        return chartName;
    }

    public abstract void update(String chartTitle);

    @Override
    public void setup(VisulogScene scene) {
        this.model = scene.getModel();
    }

    public Model getModel() {
        return model;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }
}
