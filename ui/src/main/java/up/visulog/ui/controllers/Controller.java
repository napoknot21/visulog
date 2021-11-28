package up.visulog.ui.controllers;

import up.visulog.ui.model.Model;
import up.visulog.ui.views.View;
import up.visulog.ui.views.objects.MethodButton;
import up.visulog.ui.views.objects.VisulogButtons;
import up.visulog.ui.views.scenes.VisulogScene;

public abstract class Controller {

    protected View view;
    protected Model model;
    protected VisulogScene scene;

    public Controller(View view, Model model, VisulogScene scene) {
        this.view = view;
        this.model = model;
        this.scene = scene;
    }

    protected abstract void runPlugin(VisulogButtons b);

    public abstract void executeAction(VisulogButtons b);

    public abstract void executeAction(MethodButton b);


}
