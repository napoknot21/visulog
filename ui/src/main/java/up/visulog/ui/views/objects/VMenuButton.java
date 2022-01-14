package up.visulog.ui.views.objects;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu de boutons lateral
 */
public class VMenuButton extends VBox implements SceneChild, IndependentsButtonsMenu {
    private static double nextPosY = 0;
    private static Controller controller;


    public VMenuButton() {
        super();
        this.getChildren().add(new SearchBar());
        this.getChildren().addAll(initializeMenuButtonItem());

    }

    /**
     * Initialise les boutons automatiquement
     *
     * @return la liste de boutons generes
     */
    private List<MenuButtonItem> initializeMenuButtonItem() { //
        ArrayList<MenuButtonItem> buttons = new ArrayList<>();
        for (String key : Model.BUTTON_NAME_TO_PLUGIN_NAME.keySet()) {
            MenuButtonItem b = new MenuButtonItem(key);
            buttons.add(b);
        }
        return buttons;
    }

    /**
     * Initialise l'action des boutons
     */
    public void initMenuButtonAction() {
        Controller controller = getController();
        for (Node node : this.getChildren()) {
            if (node instanceof MenuButtonItem) {
                MenuButtonItem b = (MenuButtonItem) node;
                b.setOnAction((event) -> controller.executeAction(b));
            }
        }
    }

    @Override
    public void setup(VisulogScene scene) {
        controller = scene.getController();
    }

    public Controller getController() {
        return controller;
    }

    /**
     * Represente les boutons du menu
     */
    static class MenuButtonItem extends MethodButton implements PluginButtons {

        public MenuButtonItem(String label) {
            super(label);
            this.setLayoutY(nextPosY);
            this.setLayoutX(0);
            nextPosY = this.getLayoutY() + 30;
        }
    }
}
