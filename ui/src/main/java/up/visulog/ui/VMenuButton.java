package up.visulog.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import up.visulog.ui.controller.MethodButton;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class VMenuButton extends Pane {
    private static double nextPosY = 0;

    public VMenuButton() {
        super();
        initialize();
    }

    private void initialize() {
        this.getChildren().addAll(initializeMenuButtonItem(this));
    }

    private ArrayList<MenuButtonItem> initializeMenuButtonItem(Pane p) {
        ArrayList<MenuButtonItem> buttons = new ArrayList<>();
        for (String key : MenuButtonItem.NAME_TO_PLUGIN_NAME.keySet()) {
            MenuButtonItem b = new MenuButtonItem(key);
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

    class MenuButtonItem extends MethodButton { // Represente les boutons du menu

        public MenuButtonItem(String label) {
            super(label);
            this.setLayoutY(nextPosY);
            this.setLayoutX(0);
            nextPosY = this.getLayoutY() + 30;
        }

    }
}
