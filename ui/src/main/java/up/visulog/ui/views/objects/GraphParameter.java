package up.visulog.ui.views.objects;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

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
        for (String name : ChartButton.NAME_TO_CHART_FILTER.keySet()) {
            String plugin = ChartButton.NAME_TO_CHART_FILTER.get(name);
            try {
                ret.add(initChartButton(name, mainContainer, plugin));
            } catch (ReflectiveOperationException ignored) {}
        }
        return ret;
    }

    private ChartButton initChartButton(String label, MainContainer mainContainer, String plugin) throws ReflectiveOperationException {
        ChartButton b = classInstance(label,plugin);
        b.setVisible(false);
        b.selectedProperty().addListener(e -> controller.applyFilter(b, mainContainer));
        return b;
    }

    private static ChartButton classInstance (String label, String plugin) throws ReflectiveOperationException{
        Constructor<?> classConstructor = findChartButton(plugin).getConstructor(String.class);
        return (ChartButton)classConstructor.newInstance(label);
    }
    private static Class <?> findChartButton(String plugin) throws ClassNotFoundException {
        return Class.forName("up.visulog.ui.views.objects."+plugin+"Button"); // returns the Class object for the plugin
    }

}
