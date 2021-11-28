package up.visulog.ui.views;

import javafx.scene.layout.Pane;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;

//Classe mere des menus de visulog (boutons et radio boutons)
public abstract class VMenu extends Pane
        implements SceneChild {

    private Controller controller;
    private Model model;

    @Override
    public void setup(VisulogScene scene) {
        this.controller = scene.getController();
        this.model = scene.getModel();
    }

    public Controller getController() {
        return controller;
    }

    public Model getModel() {
        return model;
    }
}
