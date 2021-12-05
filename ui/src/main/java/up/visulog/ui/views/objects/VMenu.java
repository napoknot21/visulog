package up.visulog.ui.views.objects;

import javafx.scene.layout.Pane;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

//Classe mere des menus de visulog (boutons et radio boutons)
public abstract class VMenu extends Pane    //Todo: Pane ou VBox ?
        implements SceneChild {

    private Controller controller;
    private Model model;

    public VMenu() {
        super();
    }

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
