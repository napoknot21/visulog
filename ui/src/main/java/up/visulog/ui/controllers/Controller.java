package up.visulog.ui.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.View;
import up.visulog.ui.views.objects.*;
import up.visulog.ui.views.scenes.VisulogScene;
import up.visulog.ui.views.objects.GraphParameter.ChartButton;

import java.lang.invoke.MethodType;
import java.nio.file.FileSystems;
import java.util.HashMap;

public class Controller {

    protected View view;
    protected Model model;
    protected VisulogScene scene;

    public Controller(View view, Model model, VisulogScene scene) {
        this.view = view;
        this.model = model;
        this.scene = scene;
    }

    MenuRadioButton menuRadioButton;

    public void executeAction(VisulogButtons b) {
        if (b == null) return;
        runPlugin(b);
        this.scene.update(model.toHtml());
    }

    public void executeAction(MethodButton b) {
        if (b == null) return;
        executeAction((VisulogButtons) b);
        if (menuRadioButton != null) {
            menuRadioButton.initMenuButtonAction(b.getPlugin());
        }

    }

    protected void runPlugin(VisulogButtons b) {  //Execute le plugin Todo: amelioration de cette partie
        var PLUGINS = Model.PLUGINS;
        if (!PLUGINS.containsKey(b.getValue())) return;
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        plugin.put(b.getValue(), PLUGINS.get(b.getValue()));
        var config = new Configuration(gitPath, plugin);
        model.update(new Analyzer(config).computeResults());
    }

    public void setMenuRadioButton() { //Cherche dans l'arborescene le menuRadioButton
        var nodeList = scene.getRoot().getChildrenUnmodifiable();
        if (nodeList == null) return;
        for (Node node : nodeList) {

            setMenuRadioButton(node);
        }
    }

    private void setMenuRadioButton(Node node) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (node == null) return;
        if (node instanceof MenuRadioButton) {
            menuRadioButton = (MenuRadioButton) node;
            return;
        }
        if (node instanceof SplitPane) setMenuRadioButton((SplitPane) node);
        else setMenuRadioButton((Parent) node);
    }

    private void setMenuRadioButton(SplitPane parent) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (parent == null) return;
        var nodeList = parent.getItems();
        for (Node node : nodeList) {
            setMenuRadioButton(node);
        }
    }

    private void setMenuRadioButton(Parent parent) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (parent == null) return;
        var nodeList = parent.getChildrenUnmodifiable();
        for (Node node : nodeList) {
            setMenuRadioButton(node);
        }
    }

    public void switchWebMode(GraphParameter container, MainContainer mainContainer) {
        container.getChildren().forEach(node -> {if (node instanceof VisulogChartButtons) node.setVisible(false);});
        mainContainer.getChildren().add(mainContainer.getWeb());
    }

    public void switchGraphMode(HBox container, MainContainer mainContainer) {
        container.getChildren().forEach(node -> {if (node instanceof VisulogChartButtons) node.setVisible(true);});
        mainContainer.getChildren().remove(mainContainer.getWeb());
    }

    public void applyFilter (ChartButton b, MainContainer mainContainer) {
            if (b.isSelected()) System.out.println(b.getText() + " checked");
            else System.out.println(b.getText() + " unchecked");
    }


}
