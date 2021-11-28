package up.visulog.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import up.visulog.ui.views.HomeScene;
import up.visulog.ui.views.View;
import up.visulog.ui.views.WebEngineScene;

import java.awt.*;
import java.io.IOException;

public class VisulogGUI extends Application {
    private View view;




    @Override
    public void start(Stage primaryStage) throws IOException {
        view = new View();
        /*Scene scene = new WebEngineScene(primaryStage);
        primaryStage.setScene(new HomeScene(primaryStage));
        primaryStage.setTitle("Visulog");
        primaryStage.show();*/
        view.show();

    }

    public static void main(String[] args) {
        if (args.length !=0 )return;
        //Todo: A modifier si on veut ajouter l'execution de l'ui via un plugin specifique
        launch(args);
    }

}
