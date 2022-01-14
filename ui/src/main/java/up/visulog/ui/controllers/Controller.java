package up.visulog.ui.controllers;

import javafx.scene.chart.Chart;
import javafx.scene.layout.HBox;
import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.View;
import up.visulog.ui.views.objects.*;
import up.visulog.ui.views.objects.chart.ChartButton;
import up.visulog.ui.views.objects.chart.ChartButtons;
import up.visulog.ui.views.scenes.VisulogScene;

import java.nio.file.FileSystems;
import java.util.*;

/**
 * Controller de l'interface graphique
 */
public class Controller {

    /**
     * Represenete la vue de la gui
     */
    protected View view;
    /**
     * Represente le modele de la gui.
     */
    protected Model model;
    /**
     * Represente la scene principale de la gui
     */
    protected VisulogScene scene;
    /**
     * Pannel contenant les selecteurs de graphique
     */
    GraphParameter graphParameter;
    /**
     * Represente le panel central avec le webView ou les graphiques
     */
    MainContainer mainContainer;
    /**
     * Represente le panel contenant les boutons des fiultres de plugins
     */
    MenuRadioButton menuRadioButton;

    public Controller(View view, Model model, VisulogScene scene) {
        this.view = view;
        this.model = model;
        this.scene = scene;
    }

    /**
     * Execute le plugin lie au bouton
     *
     * @param b represente le bouton active
     */
    public void executeAction(PluginButtons b) {
        if (b == null) return;
        runPlugin(b);
        this.scene.update(model.toHtml());
    }

    /**
     * Execute le plugin lie au bouton
     *
     * @param b represente le bouton active
     */
    public void executeAction(MethodButton b) {
        if (b == null) return;
        executeAction((PluginButtons) b);
        if (menuRadioButton != null) {
            menuRadioButton.initMenuButtonAction(b.getPlugin());
        }

    }

    /**
     * Execute le plugin
     *
     * @param b represente le bouton active
     */
    protected void runPlugin(PluginButtons b) {
        var PLUGINS = Model.PLUGINS;
        if (!PLUGINS.containsKey(b.getValue())) return;
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        plugin.put(b.getValue().split("-")[0], PLUGINS.get(b.getValue()));
        var config = new Configuration(gitPath, plugin);
        model.update(new Analyzer(config).computeResults(), b.getText());
        this.updateMainContainer();
    }

    /**
     * Met à jour les graphes du mainContainer
     */
    private void updateMainContainer() {
        graphParameter.getGraphicSelector().setVisible(true);
        graphParameter.getChildren().forEach(node -> {
            if (node instanceof ChartButton && ((ChartButton) node).isSelected()) {
                ChartButton button = (ChartButton) node;
                mainContainer.getChildren().removeAll(button.getChart());
                button.setChartNull();
                button.update(model.getCurrentPlugin());
                Collection<Chart> charts = button.getChart();
                if (graphParameter.getGraphicSelector().isSelected()) {
                    for (Chart c : charts) mainContainer.getChildren().add(c);
                }
            }
        });
    }

    /**
     * Passe l'application en affichage web
     *
     * @param container represente le panel contenant les boutons de selection de graphique
     */
    public void switchWebMode(GraphParameter container) {
        container.getChildren().forEach(node -> {
            if (node instanceof ChartButtons) node.setVisible(false);
        });
        mainContainer.getChildren().removeIf(n -> n instanceof Chart);
        mainContainer.getChildren().add(mainContainer.getWeb());
    }

    /**
     * Passe l'application en affichage graphe
     *
     * @param container represente le panel contenant les boutons de selection de graphique
     */
    public void switchGraphMode(HBox container) {
        container.getChildren().forEach(node -> {
            if (node instanceof ChartButtons) {
                node.setVisible(true);
                Collection<Chart> charts = ((ChartButton) node).getChart();
                if (charts != null && !charts.isEmpty()) {
                    for (Chart c : charts)
                        mainContainer.getChildren().add(c);
                }
            }
        });
        mainContainer.getChildren().remove(mainContainer.getWeb());
    }

    /**
     * Applique le filtre lie au bouton
     *
     * @param b est le bouton active
     */
    public void applyFilter(ChartButton b) {
        if (b.isSelected()) {
            b.update(model.getCurrentPlugin());
            for (Chart c : b.getChart())
                mainContainer.getChildren().add(c);

        } else {
            b.setChartNull();
            LinkedList<Object> toRemove = new LinkedList<>();
            for (Object o : mainContainer.getChildren()) {
                if (o instanceof Chart) toRemove.add(o);
            }

            mainContainer.getChildren().removeAll(toRemove);
        }
    }

    public void setGraphParameter(GraphParameter graphParameter) {
        this.graphParameter = graphParameter;
    }

    public void setMenuRadioButton(MenuRadioButton menuRadioButton) {
        this.menuRadioButton = menuRadioButton;
    }

    public void setMainContainer(MainContainer mainContainer) {
        this.mainContainer = mainContainer;
    }

    /**
     * lance le plugin --reserach avec les mots-clé donne en arguments
     *
     * @param text represente les mots cles
     */
    public void search(String text) {
        graphParameter.getGraphicSelector().setVisible(true);
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        HashMap<String, List<String>> pluginConfig = new HashMap<>();
        pluginConfig.put("keyWords", getKeyWord(text));
        plugin.put("research", new PluginConfig(pluginConfig));
        var config = new Configuration(gitPath, plugin);
        model.update(new Analyzer(config).computeResults(), "research");
        this.scene.update(model.toHtml());
        this.updateMainContainer();
    }

    private List<String> getKeyWord(String text) {
        List<String> keyWords = new ArrayList<>();
        try (Scanner sc = new Scanner(text)) {
            while (sc.hasNext()) {
                keyWords.add(sc.next());
            }
        }
        return keyWords;
    }
}
