package up.visulog.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//Genre le menu de boutons lateral
public class VMenuButton extends VMenu
        implements WebViewModifier {
    private static double nextPosY = 0;
    private static MenuRadioButton menuRadioButton; //Filtres


    public VMenuButton() {
        super();
        initialize();

    }

    private void initialize() {
        this.getChildren().addAll(initializeMenuButtonItem());
    }

    private ArrayList<MenuButtonItem> initializeMenuButtonItem() { //Initialise les boutons automatiquement
        ArrayList<MenuButtonItem> buttons = new ArrayList<>();
        for (String key : MenuButtonItem.BUTTON_NAME_TO_PLUGIN_NAME.keySet()) {
            MenuButtonItem b = new MenuButtonItem(key);
            buttons.add(b);
        }
        return buttons;
    }

    void initMenuButtonAction() { //Initialise l'action des boutons
        WebEngine webEngine = getWebEngine();
        for (Node node : this.getChildren()) {
            MenuButtonItem b = (MenuButtonItem) node;
            if (b != null) {
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        String s = b.toHtml(b.run());

                        if(webEngine != null) webEngine.loadContent(s);
                        if (menuRadioButton != null) {
                            menuRadioButton.initMenuButtonAction(b.getValue());
                        }
                       /* try {
                            File resultHtml = new File("src\\result.html");
                            PrintWriter writer = new PrintWriter(resultHtml);
                            writer.println(s);
                            writer.close();
                            File var2 = new File("src\\result.html");
                            Desktop.getDesktop().browse(var2.toURI());
                        } catch (IOException var3) {
                            var3.printStackTrace();
                        }*/
                    }
                });
            }
        }
    }

    private void getMenuRadioButton() { //Cherche dans l'arborescene le menuRadioButton
        if (PrimaryStage == null) return;
        var nodeList = PrimaryStage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node node : nodeList) {
            if (node instanceof MenuRadioButton) {
                menuRadioButton = (MenuRadioButton) node;
                return;
            }
            getMenuRadioButton(node);
        }
    }

    private void getMenuRadioButton(Node node) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (node == null) return;
        if (node instanceof SplitPane) getMenuRadioButton((SplitPane) node);
        else getMenuRadioButton((Parent) node);
    }

    private void getMenuRadioButton(SplitPane parent) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (parent == null) return;
        var nodeList = parent.getItems();
        for (Node node : nodeList) {
            if (node instanceof MenuRadioButton) {
                menuRadioButton = (MenuRadioButton) node;
                return;
            }

            getMenuRadioButton(node);
        }
    }

    private void getMenuRadioButton(Parent parent) { //Fonction de parcours, Cherche dans l'arborescene le menuRadioButton
        if (parent == null) return;
        var nodeList = parent.getChildrenUnmodifiable();
        for (Node node : nodeList) {
            if (node instanceof MenuRadioButton) {
                menuRadioButton = (MenuRadioButton) node;
                return;
            }
            getMenuRadioButton(node);
        }
    }

    @Override
    public void setup(Stage PrimaryStage) { //Rend le menu fonctionnel
        super.setup(PrimaryStage);
        getMenuRadioButton();
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
