package up.visulog.ui.views.objects;

import javafx.scene.control.RadioButton;

public abstract class ChartButton extends RadioButton {

    String chartName;

    public ChartButton (String label) {
        super(label);
        String v = "";
        if (GraphParameter.ChartButton.NAME_TO_CHART_FILTER.containsKey(label)) v = GraphParameter.ChartButton.NAME_TO_CHART_FILTER.get(label);
        this.chartName = v;
        this.setToggleGroup(GraphParameter.ChartButton.GROUP);
    }

    public String getChartName() {
        return chartName;
    }
}
