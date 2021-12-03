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
    RadioButton pieChart,barChart;

    public RadioButton getPieChart() {
        return pieChart;
    }

    public RadioButton getBarChart() {
        return barChart;
    }

    public GraphParameter (Pane mainContainer) {
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
        this.getChildren().addAll(initGraphParameters());
    }

    private Collection<Node> initGraphParameters() {
        LinkedList<Node> ret = new LinkedList<>();
        ToggleGroup group = new ToggleGroup();
        pieChart = initRadioButton("Pie Chart",group);
        barChart = initRadioButton("Bar Chart", group);

        ret.add(pieChart); ret.add(barChart);
        return ret;
    }

    private RadioButton initRadioButton(String label, ToggleGroup group) {
        RadioButton ret = new RadioButton(label);
        ret.setToggleGroup(group);
        ret.setVisible(false);
        controller.initAction(ret);
        return ret;
    }
}
