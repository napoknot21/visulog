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

public class Controller {

    protected View view;
    protected Model model;
    protected VisulogScene scene;
    GraphParameter graphParameter;
    MainContainer mainContainer;
    MenuRadioButton menuRadioButton;
    public Controller(View view, Model model, VisulogScene scene) {
        this.view = view;
        this.model = model;
        this.scene = scene;
    }

    public void executeAction(PluginButtons b) {// Execute le plugin lie au bouton
        if (b == null) return;
        runPlugin(b);
        this.scene.update(model.toHtml());
    }

    public void executeAction(MethodButton b) {// Execute le plugin lie au bouton
        if (b == null) return;
        executeAction((PluginButtons) b);
        if (menuRadioButton != null) {
            menuRadioButton.initMenuButtonAction(b.getPlugin());
        }

    }

    protected void runPlugin(PluginButtons b) {  //Execute le plugin Todo: amelioration de cette partie
        var PLUGINS = Model.PLUGINS;
        if (!PLUGINS.containsKey(b.getValue())) return;
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        plugin.put(b.getValue().split("-")[0], PLUGINS.get(b.getValue()));
        var config = new Configuration(gitPath, plugin);
        model.update(new Analyzer(config).computeResults(), b.getText());
        this.updateMainContainer();
    }

    private void updateMainContainer() {//Met à jour les graphes du mainContainer
        graphParameter.getGraphicSelector().setVisible(true);
        graphParameter.getChildren().forEach(node -> {
            if (node instanceof ChartButton && ((ChartButton) node).isSelected()) {
                ChartButton button = (ChartButton) node;
                mainContainer.getChildren().removeAll(button.getChart());
                button.setChartNull();
                button.update(model.getCurrentPlugin());
                Collection<Chart> charts = button.getChart();
                if (graphParameter.getGraphicSelector().isSelected()){
                    for(Chart c : charts) mainContainer.getChildren().add(c);
                }
            }
        });
    }

    public void switchWebMode(GraphParameter container) { //Passe l'application en affichage web
        container.getChildren().forEach(node -> {
            if (node instanceof ChartButtons) node.setVisible(false);
        });
        mainContainer.getChildren().removeIf(n -> n instanceof Chart);
        mainContainer.getChildren().add(mainContainer.getWeb());
    }

    public void switchGraphMode(HBox container) {   ////Passe l'application en affichage graphe
        container.getChildren().forEach(node -> {
            if (node instanceof ChartButtons) {
                node.setVisible(true);
                Collection<Chart> charts = ((ChartButton) node).getChart();
                if (charts != null && !charts.isEmpty()) {
                    for(Chart c : charts)
                    mainContainer.getChildren().add(c);
                }
            }
        });
        mainContainer.getChildren().remove(mainContainer.getWeb());
    }

    public void applyFilter(ChartButton b) {    //Applique le filtre lie au bouton
        if (b.isSelected()) {
            b.update(model.getCurrentPlugin());
            for(Chart c : b.getChart())
                mainContainer.getChildren().add(c);

        } else {
            b.setChartNull();
            LinkedList<Object> toRemove = new LinkedList<>();
            for(Object o : mainContainer.getChildren()){
                if(o instanceof Chart) toRemove.add(o);
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

    public void search(String text) {
        graphParameter.getGraphicSelector().setVisible(true);
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        HashMap <String, List<String>> pluginConfig = new HashMap<>();
        pluginConfig.put("keyWords",getKeyWord(text));
        plugin.put("research", new PluginConfig(pluginConfig));
        var config = new Configuration(gitPath, plugin);
        model.update(new Analyzer(config).computeResults(), "research");
        this.scene.update(model.toHtml());
        this.updateMainContainer();
    }

    private List<String> getKeyWord(String text) {
        List<String> keyWords = new ArrayList<>();
        try(Scanner sc = new Scanner(text)) {
            while (sc.hasNext()) {
                keyWords.add(sc.next());
            }
        }
        return keyWords;
    }
}
