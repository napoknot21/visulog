package up.visulog.ui.views.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;

/**
 * Scene de l'application
 */
public abstract class VisulogScene extends Scene {

    private final Model model;
    private Controller controller;

    public VisulogScene(Parent root) {
        super(root);
        model = new Model();
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

    /**
     * Met a jour la scene
     */
    public abstract void update();

    /**
     * Met a jour la scene
     *
     * @param html represente le code html a introduire dans le webEngine
     */
    public abstract void update(String html);

}
