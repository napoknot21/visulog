package up.visulog.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MenuRadioButton extends Pane {
    private String plugin;
    private static double nextPosX = 0;

    public MenuRadioButton (String plugin) {
        super();
        this.plugin=plugin;
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
}
