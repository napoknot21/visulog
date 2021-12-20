package up.visulog.ui.views.objects.chart;

import javafx.scene.chart.Chart;
import javafx.scene.control.RadioButton;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.objects.SceneChild;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.Collection;
import java.util.LinkedList;

public abstract class ChartButton extends RadioButton
        implements ChartButtons, SceneChild {

    private final String chartName;
    private Model model;
    private Collection<Chart> charts;

    public ChartButton(String label) {
        super(label);
        String v = "";
        if (ChartButton.NAME_TO_CHART_FILTER.containsKey(label)) v = ChartButton.NAME_TO_CHART_FILTER.get(label);
        this.chartName = v;
        this.setToggleGroup(ChartButton.GROUP);
        charts = new LinkedList<Chart>();
    }

    public String getChartName() {
        return chartName;
    }

    public abstract void update(String chartTitle); //Met a jour le graphe

    @Override
    public void setup(VisulogScene scene) {
        this.model = scene.getModel();
    }

    public Model getModel() {
        return model;
    }

    public Collection<Chart> getChart() {
        return charts;
    }

    public void setChart(Chart chart) {
        if(charts != null)
        this.charts.add(chart);
    }

    public void setChartNull(){
        this.charts = new LinkedList<Chart>();
    }

    public boolean isChartSet() {
        return !charts.isEmpty();
    }
}
