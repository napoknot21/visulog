package up.visulog.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import up.visulog.ui.views.View;

import java.awt.*;

public class VisulogLauncher extends Application {
    private View view;

    public static void run(String[] args) {
        if (args.length != 0) return;
        EventQueue.invokeLater(() -> launch(args));
    }

    @Override
    public void start(Stage primaryStage) {
        view = new View();
        view.show();
    }

}
