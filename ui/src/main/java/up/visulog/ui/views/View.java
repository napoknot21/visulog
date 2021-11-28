package up.visulog.ui.views;

import javafx.stage.Stage;

import java.io.IOException;

public class View extends Stage {

    private VisulogScene scene;

    public View() throws IOException {
        super();
        setScene(new WebEngineScene(this));
        //setScene(new HomeScene(this));
        setTitle("Visulog");
    }

    public void updateScene(VisulogScene scene) {
        this.scene = scene;
        setScene(scene);
    }

}
