package up.visulog.ui.views.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import up.visulog.ui.controllers.WebEngineController;
import up.visulog.ui.views.View;
import up.visulog.ui.views.objects.IndependentsButtonsMenu;
import up.visulog.ui.views.objects.SceneChild;

import java.io.IOException;

public class WebEngineScene extends VisulogScene
        implements WebViewModifier {

    Stage view;
    WebEngine webEngine;

    public WebEngineScene(View view) {
        super(new Label("Hello World"));
        this.view = view;
        setController(new WebEngineController(view, getModel(), this));
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WebEngineScene.class.getResource("/up/visulog/ui/views/RootLayout.fxml"));
            Parent root = loader.load();
            setRoot(root);
            ((WebEngineController) getController()).setMenuRadioButton();
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
    }

    private void setupChildren() {
        var nodeList = getRoot().getChildrenUnmodifiable();
        if (nodeList == null) return;
        for (Node node : nodeList) {
            setupChildren(node);
        }
    }


    private void setupChildren(SplitPane parent) { //Fonction de parcours, initialise le model et le controlleur pour
        if (parent == null) return;                //Les elements de l'ui
        var nodeList = parent.getItems();
        for (Node node : nodeList) {
            setupChildren(node);
        }
    }

    private void setupChildren(Parent parent) { //Fonction de parcours
        if (parent == null) return;
        var nodeList = parent.getChildrenUnmodifiable();
        for (Node node : nodeList) {
            setupChildren(node);
        }
    }

    private void setupChildren(Node node) { //Fonction de parcours
        if (node == null) return;
        if (node instanceof SceneChild) ((SceneChild) node).setup(this);
        if (node instanceof IndependentsButtonsMenu) ((IndependentsButtonsMenu) node).initMenuButtonAction();

        if (node instanceof SplitPane) setupChildren((SplitPane) node);
        else setupChildren((Parent) node);
    }

    public void initWebEngine() { //Initialise le webEngine
        var nodeList = this.getRoot().getChildrenUnmodifiable();
        if (nodeList == null) return;
        for (Node node : nodeList) {
            initWebEngine(node);
        }
    }

    private void initWebEngine(SplitPane parent) { //Fonction de parcours, initialise le webEngine
        if (parent == null) return;
        var nodeList = parent.getItems();
        for (Node node : nodeList) {
            initWebEngine(node);
        }
    }

    private void initWebEngine(Parent parent) { //Fonction de parcours, initialise le webEngine
        if (parent == null) return;
        var nodeList = parent.getChildrenUnmodifiable();
        for (Node node : nodeList) {
            initWebEngine(node);
        }
    }

    private void initWebEngine(Node node) { //Fonction de parcours, initialise le webEngine
        if (node == null) return;
        if (node instanceof WebView) {
            webEngine = ((WebView) node).getEngine();
            return;
        }
        if (node instanceof SplitPane) initWebEngine((SplitPane) node);
        initWebEngine((Parent) node);
    }


}
