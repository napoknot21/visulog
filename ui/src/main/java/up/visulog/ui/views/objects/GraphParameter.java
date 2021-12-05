package up.visulog.ui.views.objects;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.objects.chart.ChartButton;
import up.visulog.ui.views.scenes.VisulogScene;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedList;

public class GraphParameter extends HBox
        implements SceneChild {
    Controller controller;
    Model model;
    Pane mainContainer;
    CheckBox graphique;

    public GraphParameter(MainContainer mainContainer) {
        this.mainContainer = mainContainer;
        graphique = new CheckBox("Graphique");
        this.getChildren().add(graphique);
        graphique.setVisible(false);
        GraphParameter container = this;
        graphique.selectedProperty().addListener(e -> {
            if (graphique.isSelected()) controller.switchGraphMode(container);
            else controller.switchWebMode(container);
        });
    }
//Cree automatiquement une instance d'un bouton si la classe existe
    private static ChartButton classInstance(String label, String plugin) throws ReflectiveOperationException {
        Constructor<?> classConstructor = findChartButton(plugin).getConstructor(String.class);
        return (ChartButton) classConstructor.newInstance(label);
    }

    //Cherche la classe definie par le nom du plugin
    private static Class<?> findChartButton(String plugin) throws ClassNotFoundException {
        return Class.forName("up.visulog.ui.views.objects.chart." + plugin + "Button"); // returns the Class object for the plugin
    }

    @Override
    public void setup(VisulogScene scene) {
        this.model = scene.getModel();
        this.controller = scene.getController();
        controller.setGraphParameter(this);
        if (mainContainer instanceof MainContainer)
            this.getChildren().addAll(initGraphParameters((MainContainer) mainContainer));
    }

    //Initialise les ChartButton automatiquement
    private Collection<ChartButton> initGraphParameters(MainContainer mainContainer) {
        LinkedList<ChartButton> ret = new LinkedList<>();
        for (String name : ChartButton.NAME_TO_CHART_FILTER.keySet()) {
            String plugin = ChartButton.NAME_TO_CHART_FILTER.get(name);
            try {
                ret.add(initChartButton(name, mainContainer, plugin));
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return ret;
    }

    //Initialise les ChartButton automatiquement
    private ChartButton initChartButton(String label, MainContainer mainContainer, String plugin) throws ReflectiveOperationException {
        ChartButton b = classInstance(label, plugin);
        b.setVisible(false);
        b.selectedProperty().addListener(e -> controller.applyFilter(b));
        return b;
    }

    public CheckBox getGraphique() {
        return graphique;
    }
}
