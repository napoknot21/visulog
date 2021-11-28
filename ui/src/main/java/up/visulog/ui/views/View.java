package up.visulog.ui.views;

import javafx.application.Platform;
import javafx.stage.Stage;
import up.visulog.ui.views.scenes.*;


public class View extends Stage {

    private VisulogScene scene;

    public View() {
        super();
        updateScene(new WebEngineScene(this));
        setTitle("Visulog");
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
