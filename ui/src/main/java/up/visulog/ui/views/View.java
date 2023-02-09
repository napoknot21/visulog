package up.visulog.ui.views;

import javafx.application.Platform;
import javafx.stage.Stage;
import up.visulog.ui.views.scenes.VisulogScene;
import up.visulog.ui.views.scenes.WebEngineScene;

/**
 * Fenetre de Visulog
 */
public class View extends Stage {

    private VisulogScene scene;

    public View() {
        super();
        updateScene(new WebEngineScene(this));
        setTitle("Visulog");
        sizeToScene();
        setOnCloseRequest((event -> {
            Platform.exit();
            System.exit(0);
        }));
    }

    /**
     * Met a jour la scene principale
     *
     * @param scene represente la nouvelle scene
     */
    public void updateScene(VisulogScene scene) {
        this.scene = scene;
        setScene(scene);
    }

}
