package up.visulog.ui.controller;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

//Classe mere des menus de visulog (boutons et radio boutons)
public abstract class VMenu extends Pane
        implements WebViewModifier {
    protected Stage PrimaryStage;
    private WebEngine webEngine;

    public Stage getPrimaryStage() {
        return PrimaryStage;
    }

    @Override
    public void setup(Stage PrimaryStage) { //Initialise le priomary stage et le webEngine
        this.PrimaryStage = PrimaryStage;
        initWebEngine();
        System.out.println(webEngine);
    }

    @Override
    public void initWebEngine() { //Initialise le webEngine
        if (PrimaryStage == null) return;
        var nodeList = PrimaryStage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node node : nodeList) {
            if (node instanceof WebView) {
                webEngine = ((WebView) node).getEngine();
                return;
            }

            initWebEngine(node);
        }
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }

    private void initWebEngine(SplitPane parent) { //Fonction de parcours, initialise le webEngine
        if (parent == null) return;
        var nodeList = parent.getItems();
        for (Node node : nodeList) {
            if (node instanceof WebView) {
                webEngine = ((WebView) node).getEngine();
                return;
            }
            initWebEngine(node);
        }
    }

    private void initWebEngine(Parent parent) { //Fonction de parcours, initialise le webEngine
        if (parent == null) return;
        var nodeList = parent.getChildrenUnmodifiable();
        for (Node node : nodeList) {
            if (node instanceof WebView) {
                webEngine = ((WebView) node).getEngine();
                return;
            }
            initWebEngine(node);
        }
    }

    private void initWebEngine(Node node) { //Fonction de parcours, initialise le webEngine
        if (node == null) return;
        if (node instanceof SplitPane) initWebEngine((SplitPane) node);
        initWebEngine((Parent) node);
    }
}
