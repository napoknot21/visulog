package up.visulog.ui.views;

import javafx.application.Platform;
import javafx.stage.Stage;
import up.visulog.ui.views.scenes.VisulogScene;
import up.visulog.ui.views.scenes.WebEngineScene;


public class View extends Stage {

    private VisulogScene scene;

    public View() {
        super();
        updateScene(new WebEngineScene(this));
        setTitle("Visulog");
        setHeight(480);
        setWidth(720);
        setOnCloseRequest(
                (event -> {
                    Platform.exit();
                    System.exit(0);
                })
        );
    }

    public void updateScene(VisulogScene scene) {
        this.scene = scene;
        setScene(scene);
    }

}
