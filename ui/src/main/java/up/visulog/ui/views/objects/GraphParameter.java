package up.visulog.ui.views.objects;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.Collection;
import java.util.LinkedList;

public class GraphParameter extends HBox
        implements SceneChild{
    Controller controller;
    Model model;
    Pane mainContainer;
    CheckBox graphique;
    public GraphParameter (MainContainer mainContainer) {
        this.mainContainer=mainContainer;
        graphique = new CheckBox("Graphique");
        this.getChildren().add(graphique);

        GraphParameter container = this;
        graphique.selectedProperty().addListener(e -> {
            if (graphique.isSelected()) controller.switchGraphMode(container,mainContainer);
            else controller.switchWebMode(container, mainContainer);
        });
    }

    @Override
    public void setup(VisulogScene scene) {
        this.model=scene.getModel();
        this.controller=scene.getController();
        if (mainContainer instanceof MainContainer)
        this.getChildren().addAll(initGraphParameters((MainContainer) mainContainer));
    }

    private Collection<ChartButton> initGraphParameters(MainContainer mainContainer) {
        LinkedList<ChartButton> ret = new LinkedList<>();
        for (String plugin : ChartButton.NAME_TO_CHART_FILTER.keySet()) {
            ret.add(initChartButton(plugin, mainContainer));
        }
        return ret;
    }

    private ChartButton initChartButton(String label, MainContainer mainContainer) {
        ChartButton b = new ChartButton(label);
        b.setVisible(false);
        b.selectedProperty().addListener(e -> controller.applyFilter(b, mainContainer));
        return b;
    }

    public static class ChartButton extends RadioButton
            implements VisulogChartButtons {

        String chartName;

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
    }
}
