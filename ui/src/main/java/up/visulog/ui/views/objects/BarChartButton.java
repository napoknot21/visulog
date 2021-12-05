package up.visulog.ui.views.objects;

import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;

public class BarChartButton extends ChartButton{

    public BarChartButton(String label) {
        super(label);
    }

    @Override
    public void update() {
        setChart(new PieChart());
    }
}
