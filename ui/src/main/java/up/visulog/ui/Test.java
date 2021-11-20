package up.visulog.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane p = new Pane();
        ArrayList<MethodButton> buttons = new ArrayList<>();
        for (String key : MethodButton.NAME_TO_PLUGIN_NAME.keySet()) {
            MethodButton b = new MethodButton(key);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    b.toHtml(b.run());
                }
            });
            buttons.add(b);
        }
        p.getChildren().addAll(buttons);
        Scene scene = new Scene(p);
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
