package up.visulog.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class MenuRadioButton extends Pane {
    private static double nextPosX = 0;
    private String plugin;
    private Stage PrimaryStage;
    private WebEngine webEngine;

    public MenuRadioButton(String plugin, Stage PrimaryStage) {
        super();
        this.plugin = plugin;
        this.PrimaryStage = PrimaryStage;
        //webEngine = getWebEngine();
        initialize();
    }

    public void initialize() {
        this.getChildren().addAll(initializeMenuRadioButtonItem());
    }

    public ArrayList<MenuRadioButtonItem> initializeMenuRadioButtonItem() {
        ArrayList<MenuRadioButtonItem> buttons = new ArrayList<>();
        for (String key : MenuRadioButtonItem.RADIO_BUTTON_NAME.keySet()) {
            MenuRadioButtonItem b = new MenuRadioButtonItem(key);
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
        return buttons;
    }

    class MenuRadioButtonItem extends MethodRadioButton {
        public MenuRadioButtonItem(String label) {
            super(label, plugin);
            this.setLayoutX(nextPosX);
            this.setLayoutX(0);
            nextPosX = this.getLayoutY() + 30;
        }
    }

    /*public WebEngine getWebEngine() {
        var nodeList = PrimaryStage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node node : nodeList) {
            if (node instanceof Pane)

                return (WebEngine) node;
        }
        return null;
    }*/
}
