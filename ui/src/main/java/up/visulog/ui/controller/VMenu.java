package up.visulog.ui.controller;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public abstract class VMenu extends Pane
        implements WebViewModifier {
    private Stage PrimaryStage;
    private WebEngine webEngine;

    public Stage getPrimaryStage() {
        return PrimaryStage;
    }

    @Override
    public void setup(Stage PrimaryStage) {
        this.PrimaryStage = PrimaryStage;
        initWebEngine();
    }

    @Override
    public void initWebEngine() {
        if (PrimaryStage == null) return;
        var nodeList = PrimaryStage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node node : nodeList) {
            initWebEngine(node);
        }
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }

    private void initWebEngine(SplitPane parent) {
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

    private void initWebEngine(Parent parent) {
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

    private void initWebEngine(Node node) {
        if (node == null) return;
        if (node instanceof SplitPane) initWebEngine((SplitPane) node);
        initWebEngine((Parent) node);
    }
}
