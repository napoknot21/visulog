package up.visulog.ui.views.objects;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;
import up.visulog.ui.views.scenes.WebEngineScene;

public class MainContainer extends VBox
        implements SceneChild{

    Controller controller;
    Model model;
    WebEngine webEngine;


    public MainContainer () {
        this.getChildren().add(new GraphParameter(this));

    }

    @Override
    public void setup(VisulogScene scene) {
        this.controller = scene.getController();
        this.model = scene.getModel();
        if (scene instanceof WebEngineScene)
            this.webEngine = ((WebEngineScene) scene).getWebEngine();
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }
}
