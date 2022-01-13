package up.visulog.ui.views.objects;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.ArrayList;

//Genre le menu de boutons lateral
public class VMenuButton extends VBox
        implements SceneChild, IndependentsButtonsMenu {
    private static double nextPosY = 0;

    private Controller controller;

    public VMenuButton() {
        super();
        initialize();

    }

    private void initialize() {
        this.getChildren().addAll(initializeMenuButtonItem());
    }

    private ArrayList<MenuButtonItem> initializeMenuButtonItem() { //Initialise les boutons automatiquement
        ArrayList<MenuButtonItem> buttons = new ArrayList<>();
        for (String key : Model.BUTTON_NAME_TO_PLUGIN_NAME.keySet()) {
            MenuButtonItem b = new MenuButtonItem(key);
            buttons.add(b);
        }
        return buttons;
    }

    public void initMenuButtonAction() { //Initialise l'action des boutons
        Controller controller = getController();
        for (Node node : this.getChildren()) {
            MenuButtonItem b = (MenuButtonItem) node;
            if (b != null) {
                b.setOnAction((event) -> controller.executeAction(b));
            }
        }
    }

    public void setup(VisulogScene scene) {
        this.controller = scene.getController();
    }


    static class MenuButtonItem extends MethodButton
            implements PluginButtons { // Represente les boutons du menu

        public MenuButtonItem(String label) {
            super(label);
            this.setLayoutY(nextPosY);
            this.setLayoutX(0);
            nextPosY = this.getLayoutY() + 30;
        }
    }

    public Controller getController() {
        return controller;
    }
}
