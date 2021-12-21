package up.visulog.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import up.visulog.ui.views.View;

import java.awt.*;

public class VisulogLauncher extends Application {
    private View view;

    public static void main(String[] args) {
        if (args.length != 0) System.exit(0);
        //Todo: A modifier si on veut ajouter l'execution de l'ui via un plugin specifique
        EventQueue.invokeLater(() -> launch(args));
    }

    @Override
    public void start(Stage primaryStage) {
        view = new View();
        view.show();

    }

}
