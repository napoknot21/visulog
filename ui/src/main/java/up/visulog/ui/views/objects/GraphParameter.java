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

/**
 * Panel contenant les boutons servant a selectionner les graphiques
 */
public class GraphParameter extends HBox implements SceneChild {
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


    /**
     * Cree automatiquement une instance d'un bouton si la classe existe
     *
     * @param label  represente le label affichable du bouton
     * @param plugin represente le nom du plugin relie au bouton
     * @return le bouton genere
     * @throws ReflectiveOperationException si la classe est introuvable
     */
    private static ChartButton classInstance(String label, String plugin) throws ReflectiveOperationException {
        Constructor<?> classConstructor = findChartButton(plugin).getConstructor(String.class);
        return (ChartButton) classConstructor.newInstance(label);
    }

    /**
     * Cherche la classe definie par le nom du plugin
     *
     * @param plugin represnete le plugin a chercher
     * @return la classe du bouton elle existe
     * @throws ClassNotFoundException  si la classe est introuvable
     */
    private static Class<?> findChartButton(String plugin) throws ClassNotFoundException {
        return Class.forName("up.visulog.ui.views.objects.chart." + plugin + "Button"); // returns the Class object for the plugin
    }

    @Override
    public void setup(VisulogScene scene) {
        this.model = scene.getModel();
        this.controller = scene.getController();
        controller.setGraphParameter(this);
        if (mainContainer instanceof MainContainer) this.getChildren().addAll(initGraphParameters());
    }

    /**
     * Initialise les ChartButton automatiquement
     *
     * @return la liste des boutons generes
     */
    private Collection<ChartButton> initGraphParameters() {
        LinkedList<ChartButton> ret = new LinkedList<>();
        for (String name : ChartButton.NAME_TO_CHART_FILTER.keySet()) {
            String plugin = ChartButton.NAME_TO_CHART_FILTER.get(name);
            try {
                ret.add(initChartButton(name, plugin));
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return ret;
    }

    /**
     * Initialise les ChartButton automatiquement
     *
     * @param label  represente le label affichable du bouton
     * @param plugin represente le nom du plugin relie au bouton
     * @return le bouton genere
     * @throws ReflectiveOperationException si la classe est introuvable
     */
    private ChartButton initChartButton(String label, String plugin) throws ReflectiveOperationException {
        ChartButton b = classInstance(label, plugin);
        b.setVisible(false);
        b.selectedProperty().addListener(e -> controller.applyFilter(b));
        return b;
    }

    public CheckBox getGraphicSelector() {
        return graphique;
    }
}
