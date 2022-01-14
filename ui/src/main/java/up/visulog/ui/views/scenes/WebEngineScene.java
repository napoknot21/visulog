package up.visulog.ui.views.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.views.View;
import up.visulog.ui.views.objects.IndependentsButtonsMenu;
import up.visulog.ui.views.objects.SceneChild;

import java.io.IOException;

/**
 * Scene possedant un navigateur web
 */
public class WebEngineScene extends VisulogScene implements WebViewModifier {

    Stage view;
    WebEngine webEngine;
    WebView web;

    public WebEngineScene(View view) {
        super(new Label("Hello World"));
        this.view = view;
        setController(new Controller(view, getModel(), this));
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WebEngineScene.class.getResource("/up/visulog/ui/views/RootLayout.fxml"));
            Parent root = loader.load();
            setRoot(root);
            initWebEngine();
            setupChildren();
        } catch (IOException e) {
            System.out.println("Error caused by the FXMLLoader");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void update(String s) {
        webEngine.loadContent(s);
        webEngine.setUserStyleSheetLocation(String.valueOf(WebEngineScene.class.getResource("/up/visulog/ui/views/styleHTML.css")));
    }

    /**
     * Realise le setup de tous les enfants
     */
    private void setupChildren() {
        var nodeList = getRoot().getChildrenUnmodifiable();
        if (nodeList == null) return;
        for (Node node : nodeList) {
            setupChildren(node);
        }
    }


    /**
     * Fonction de parcours, realise le setup pour tous les enfants
     *
     * @param parent represente le parent direct
     */
    private void setupChildren(SplitPane parent) {
        if (parent == null) return;
        var nodeList = parent.getItems();
        for (Node node : nodeList) {
            setupChildren(node);
        }
    }

    /**
     * Fonction de parcours, realise le setup pour tous les enfants
     *
     * @param parent represente le parent direct
     */
    private void setupChildren(Parent parent) { //Fonction de parcours
        if (parent == null) return;
        var nodeList = parent.getChildrenUnmodifiable();
        for (Node node : nodeList) {
            setupChildren(node);
        }
    }

    /**
     * Fonction de parcours, realise le setup pour tous les enfants
     *
     * @param node represente le noeud fils qui doit realiser le setup
     */
    private void setupChildren(Node node) {
        if (node == null) return;
        if (node instanceof SceneChild) ((SceneChild) node).setup(this);
        if (node instanceof IndependentsButtonsMenu) ((IndependentsButtonsMenu) node).initMenuButtonAction();

        if (node instanceof SplitPane) setupChildren((SplitPane) node);
        else setupChildren((Parent) node);
    }

    @Override
    public void initWebEngine() {
        var nodeList = this.getRoot().getChildrenUnmodifiable();
        if (nodeList == null) return;
        for (Node node : nodeList) {
            initWebEngine(node);
        }
    }

    /**
     * Fonction de parcours, initialise le webEngine
     *
     * @param parent represente le parent direct
     */
    private void initWebEngine(SplitPane parent) {
        if (parent == null) return;
        var nodeList = parent.getItems();
        for (Node node : nodeList) {
            initWebEngine(node);
        }
    }

    /**
     * Fonction de parcours, initialise le webEngine
     *
     * @param parent represente le parent direct
     */
    private void initWebEngine(Parent parent) {
        if (parent == null) return;
        var nodeList = parent.getChildrenUnmodifiable();
        for (Node node : nodeList) {
            initWebEngine(node);
        }
    }

    /**
     * Fonction de parcours, initialise le webEngine
     *
     * @param node represente le noeud fils dont on verifie si c'est un webEngine
     */
    private void initWebEngine(Node node) { //Fonction de parcours, initialise le webEngine
        if (node == null) return;
        if (node instanceof WebView) {
            web = (WebView) node;
            webEngine = web.getEngine();
            return;
        }
        if (node instanceof SplitPane) initWebEngine((SplitPane) node);
        initWebEngine((Parent) node);
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }

    public WebView getWeb() {
        return web;
    }
}
