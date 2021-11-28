package up.visulog.ui.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.MethodButton;
import up.visulog.ui.views.View;
import up.visulog.ui.views.VisulogButtons;
import up.visulog.ui.views.VisulogScene;

public abstract class Controller {

    protected Stage view;
    protected Model model;
    protected VisulogScene scene;

    public Controller(Stage view, Model model, Scene scene) {
        this.view = view;
        this.model = model;
    }

    protected abstract void runPlugin(VisulogButtons b);

    public abstract void executeAction(VisulogButtons b);

    public abstract void executeAction(MethodButton b);


}
