package up.visulog.ui.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.View;
import up.visulog.ui.views.objects.MenuRadioButton;
import up.visulog.ui.views.objects.MethodButton;
import up.visulog.ui.views.objects.VisulogButtons;
import up.visulog.ui.views.scenes.VisulogScene;

import java.nio.file.FileSystems;
import java.util.HashMap;

public class WebEngineController extends Controller {

    MenuRadioButton menuRadioButton;

    public WebEngineController(View view, Model model, VisulogScene scene) {
        super(view, model, scene);
    }

    @Override
    public void executeAction(VisulogButtons b) {
        if (b == null) return;
        runPlugin(b);
        this.scene.update(model.toHtml());
    }

    @Override
    public void executeAction(MethodButton b) {
        if (b == null) return;
        executeAction((VisulogButtons) b);
        if (menuRadioButton != null) {
            menuRadioButton.initMenuButtonAction(b.getPlugin());
        }

    }

    @Override
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

}
