package up.visulog.ui.views;

import javafx.scene.Node;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;

import java.util.ArrayList;

//Genre le menu de boutons lateral
public class VMenuButton extends VMenu
        implements SceneChild {
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
        for (String key : Model.BUTTON_NAME_TO_PLUGIN_NAME.keySet()) {
            MenuButtonItem b = new MenuButtonItem(key);
            buttons.add(b);
        }
        return buttons;
    }

    void initMenuButtonAction() { //Initialise l'action des boutons
        Controller controller = getController();
        for (Node node : this.getChildren()) {
            MenuButtonItem b = (MenuButtonItem) node;
            if (b != null) {
                b.setOnAction((event) -> controller.executeAction(b));
            }
        }
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
