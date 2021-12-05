package up.visulog.ui.views.objects.chart;

import javafx.scene.control.ToggleGroup;

import java.util.HashMap;


public interface ChartButtons {

    ToggleGroup GROUP = new ToggleGroup();
    HashMap<String, String> NAME_TO_CHART_FILTER = initNameToChartFilter();


    private static HashMap<String, String> initNameToChartFilter() {// Cree les boutons permettant de creer des graphs
        HashMap<String, String> NAME_TO_CHART_FILTER = new HashMap<>();
        NAME_TO_CHART_FILTER.put("Pie Chart", "PieChart"); //Todo changer le nom des radioButtons
        NAME_TO_CHART_FILTER.put("BarChart", "BarChart");

        return NAME_TO_CHART_FILTER;
    }
}
