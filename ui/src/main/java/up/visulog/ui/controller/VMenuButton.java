package up.visulog.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class VMenuButton extends VMenu
        implements WebViewModifier {
    private static double nextPosY = 0;


    public VMenuButton() {
        super();
        initialize();

    }

    private void initialize() {
        this.getChildren().addAll(initializeMenuButtonItem());
    }

    private ArrayList<MenuButtonItem> initializeMenuButtonItem() {
        ArrayList<MenuButtonItem> buttons = new ArrayList<>();
        for (String key : MenuButtonItem.NAME_TO_PLUGIN_NAME.keySet()) {
            MenuButtonItem b = new MenuButtonItem(key);
            buttons.add(b);
        }
        return buttons;
    }

    public void initMenuButtonAction() {
        for (Node node : this.getChildren()) {
            MenuButtonItem b = (MenuButtonItem) node;
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String s = b.toHtml(b.run());
                    getWebEngine().loadContent(s);
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
        }
    }

    @Override
    public void setup(Stage PrimaryStage) {
        super.setup(PrimaryStage);
        initMenuButtonAction();
    }

    class MenuButtonItem extends MethodButton { // Represente les boutons du menu
        WebEngine webEngine = getWebEngine();

        public MenuButtonItem(String label) {
            super(label);
            this.setLayoutY(nextPosY);
            this.setLayoutX(0);
            nextPosY = this.getLayoutY() + 30;
        }
    }
}
