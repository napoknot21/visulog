package up.visulog.ui.views.objects.chart;

import javafx.scene.chart.Chart;
import javafx.scene.control.RadioButton;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.objects.SceneChild;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Represente les boutons generant les chart
 */
public abstract class ChartButton extends RadioButton implements ChartButtons, SceneChild {

    private final String chartName;
    private Model model;
    private Collection<Chart> charts;

    public ChartButton(String label) {
        super(label);
        String v = "";
        if (ChartButton.NAME_TO_CHART_FILTER.containsKey(label)) v = ChartButton.NAME_TO_CHART_FILTER.get(label);
        this.chartName = v;
        this.setToggleGroup(ChartButton.GROUP);
        charts = new LinkedList<>();
    }

    public String getChartName() {
        return chartName;
    }

    /**
     * Met a jour le graphe
     *
     * @param chartTitle est le titre du graph
     */
    public abstract void update(String chartTitle);

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

    /**
     * Ajoute un chart a la liste
     *
     * @param chart ets le chart a ajouter
     */
    public void addChart(Chart chart) {
        if (charts != null) this.charts.add(chart);
    }

    /**
     * Reinitialise la liste de chart
     */
    public void setChartNull() {
        this.charts = new LinkedList<>();
    }

    public boolean isChartSet() {
        return !charts.isEmpty();
    }

}
