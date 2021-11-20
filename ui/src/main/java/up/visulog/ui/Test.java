package up.visulog.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
                    String s = b.toHtml(b.run());
                    try {
                        File resultHtml = new File("src\\result.html");
                        PrintWriter writer = new PrintWriter(resultHtml);
                        writer.println(s);
                        writer.close();
                        File var2 = new File("src\\result.html");
                        Desktop.getDesktop().browse(var2.toURI());
                    } catch (IOException var3) {
                        var3.printStackTrace();
                    }
                }
            });
            buttons.add(b);
        }
        p.getChildren().addAll(buttons);
        Scene scene = new Scene(p);
        //scene.getStylesheets().add("src\\style.css");
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
