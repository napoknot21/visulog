package up.visulog.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import up.visulog.ui.views.View;

public class VisulogGUI extends Application {
    private View view;

    public static void main(String[] args) {
        if (args.length != 0) return;
        //Todo: A modifier si on veut ajouter l'execution de l'ui via un plugin specifique
        launch(args);
    }

    //Todo : rendre l'interface thread-safe
    @Override
    public void start(Stage primaryStage) {
        view = new View();
        view.show();

    }

}
