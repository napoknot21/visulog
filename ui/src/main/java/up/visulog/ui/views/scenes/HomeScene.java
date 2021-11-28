package up.visulog.ui.views.scenes;

import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeScene extends VisulogScene {

    public HomeScene(Stage view) {
        super(new Label("Bienvenue sur Visulog"));
        setRoot(new Label("Test"));

    }

    @Override
    public void update() {

    }

    @Override
    public void update(String s) {

    }

}
