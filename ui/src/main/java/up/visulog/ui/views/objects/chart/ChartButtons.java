package up.visulog.ui.views.objects.chart;

import javafx.scene.control.ToggleGroup;

import java.util.HashMap;

/**
 * Initialise le necessaire au bon fonctionnement des boutons ce generation de graphs
 */
public interface ChartButtons {

    ToggleGroup GROUP = new ToggleGroup();
    HashMap<String, String> NAME_TO_CHART_FILTER = initNameToChartFilter();


    /**
     * Map servant a la creation des boutons generant les graphs (nom sur l'ui -> graph)
     */
    private static HashMap<String, String> initNameToChartFilter() {//
        HashMap<String, String> NAME_TO_CHART_FILTER = new HashMap<>();
        NAME_TO_CHART_FILTER.put("Pie Chart", "PieChart");
        NAME_TO_CHART_FILTER.put("BarChart", "BarChart");

        return NAME_TO_CHART_FILTER;
    }
}
