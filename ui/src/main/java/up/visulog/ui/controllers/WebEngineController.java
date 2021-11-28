package up.visulog.ui.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.MenuRadioButton;
import up.visulog.ui.views.MethodButton;
import up.visulog.ui.views.View;
import up.visulog.ui.views.VisulogButtons;

import java.nio.file.FileSystems;
import java.util.HashMap;

public class WebEngineController extends Controller {

    MenuRadioButton menuRadioButton;

    public WebEngineController(Stage view, Model model, Scene scene) {
        super(view, model, scene);
    }

    @Override
    public void executeAction(VisulogButtons b) {
        if (b == null) return;
        b.setValue(b.getPlugin());
        System.out.println(b.getValue());
        runPlugin(b);
        scene.update(model.toHtml());
    }

    @Override
    public void executeAction(MethodButton b) {
        if (b == null) return;
        executeAction(b);
        if (menuRadioButton != null) {
            menuRadioButton.initMenuButtonAction(b.getValue());
        }
    }

    @Override
    protected void runPlugin(VisulogButtons b) {  //Execute le plugin Todo: amelioration de cette partie
        var plugins = model.getPLUGINS();
        if (!plugins.containsKey(b.getValue())) return;
        var gitPath = FileSystems.getDefault().getPath("."); //cree une variable qui contient le chemin vers ce fichier
        var plugin = new HashMap<String, PluginConfig>();
        plugin.put(b.getValue(), plugins.get(b.getValue()));
        var config = new Configuration(gitPath, plugin);
        model.update(new Analyzer(config).computeResults());
    }

    public void getMenuRadioButton() { //Cherche dans l'arborescene le menuRadioButton
        var nodeList = scene.getRoot().getChildrenUnmodifiable();
        if (nodeList == null) return;
        for (Node node : nodeList) {
            getMenuRadioButton(node);
        }
    }

    private void getMenuRadioButton(Node node) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (node == null) return;
        if (node instanceof SplitPane) getMenuRadioButton((SplitPane) node);
        else getMenuRadioButton((Parent) node);
    }

    private void getMenuRadioButton(SplitPane parent) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (parent == null) return;
        var nodeList = parent.getItems();
        for (Node node : nodeList) {
            if (node instanceof MenuRadioButton) {
                menuRadioButton = (MenuRadioButton) node;
            }

            getMenuRadioButton(node);
        }
    }

    private void getMenuRadioButton(Parent parent) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (parent == null) return;
        var nodeList = parent.getChildrenUnmodifiable();
        for (Node node : nodeList) {
            if (node instanceof MenuRadioButton) {
                menuRadioButton = (MenuRadioButton) node;
            }
            getMenuRadioButton(node);
        }
    }

}
