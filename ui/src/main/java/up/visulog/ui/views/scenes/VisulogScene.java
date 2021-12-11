package up.visulog.ui.views.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;

public abstract class VisulogScene extends Scene {

    private final Model model;
    private final Parent root;
    private Controller controller;

    public VisulogScene(Parent root) {
        super(root);
        model = new Model();
        this.root = root;
    }

    public Model getModel() {
        return model;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public abstract void update();

    public abstract void update(String s);

}
