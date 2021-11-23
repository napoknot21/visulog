package up.visulog.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class MenuRadioButton extends VMenu //Genere le menu de filtres
        implements WebViewModifier {
    private static double nextPosX = 0;

    public MenuRadioButton() {
        super();
        initialize();
    }

    public void initialize() {
        this.getChildren().addAll(initializeMenuRadioButtonItem());
    }

    public ArrayList<MenuRadioButtonItem> initializeMenuRadioButtonItem() { //Cree tous les filtres automatiquement
        ArrayList<MenuRadioButtonItem> buttons = new ArrayList<>();
        for (String key : MenuRadioButtonItem.RADIO_BUTTON_NAME.keySet()) {
            MenuRadioButtonItem b = new MenuRadioButtonItem(key);
            buttons.add(b);
        }
        return buttons;
    }


    void initMenuButtonAction(String plugin) { //Initialise l'action de chaque Radio button en fonction de leur filtre
        for (Node node : this.getChildren()) {
            MenuRadioButtonItem b = (MenuRadioButtonItem) node;

            if (b != null) {
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        b.setValue(plugin);
                        System.out.println(b.getValue());
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
    }


    class MenuRadioButtonItem extends MethodRadioButton { // Correspond a un radio button
        public MenuRadioButtonItem(String label) {
            super(label);
            this.setLayoutX(nextPosX);
            this.setLayoutX(0);
            nextPosX = this.getLayoutY() + 30;
        }
    }

}
