package up.visulog.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class MenuRadioButton extends VMenu
        implements WebViewModifier {
    private static double nextPosX = 0;
    private final String plugin;

    public MenuRadioButton() {
        super();
        plugin = getPlugin();
        initialize();
    }

    public void initialize() {
        this.getChildren().addAll(initializeMenuRadioButtonItem());
    }

    public ArrayList<MenuRadioButtonItem> initializeMenuRadioButtonItem() {
        WebEngine webEngine = getWebEngine();
        ArrayList<MenuRadioButtonItem> buttons = new ArrayList<>();
        for (String key : MenuRadioButtonItem.RADIO_BUTTON_NAME.keySet()) {
            MenuRadioButtonItem b = new MenuRadioButtonItem(key);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String s = b.toHtml(b.run());
                    webEngine.loadContent(s);
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

    private String getPlugin() {
        var NodeList = this.getParent().getChildrenUnmodifiable();
        for (Node node : NodeList) {
            if (node instanceof javafx.scene.control.Label) {
                return ((Label) node).getText();
            }
        }
        return "";
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
