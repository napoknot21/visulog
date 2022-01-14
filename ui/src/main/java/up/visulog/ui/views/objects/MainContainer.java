package up.visulog.ui.views.objects;

import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;
import up.visulog.ui.views.scenes.WebEngineScene;

/**
 * Panel central de la gui
 */
public class MainContainer extends VBox implements SceneChild {

    Controller controller;
    Model model;
    WebEngine webEngine;
    WebView web;


    public MainContainer() {
        super();
        this.getChildren().add(new GraphParameter(this));

    }

    @Override
    public void setup(VisulogScene scene) {

        this.controller = scene.getController();
        this.model = scene.getModel();
        controller.setMainContainer(this);
        if (scene instanceof WebEngineScene) this.web = ((WebEngineScene) scene).getWeb();
        this.webEngine = web.getEngine();
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }

    public WebView getWeb() {
        return web;
    }
}
