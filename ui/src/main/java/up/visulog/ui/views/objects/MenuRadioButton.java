package up.visulog.ui.views.objects;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.ArrayList;

/**
 * Menu des filtres
 */
public class MenuRadioButton extends HBox implements SceneChild {//

    private static double nextPosX = 0;
    private Controller controller;

    public MenuRadioButton() {
        super();
        initialize();
    }

    public void initialize() {
        this.getChildren().addAll(initializeMenuRadioButtonItem());
    }

    /**
     * Cree tous les filtres automatiquement
     *
     * @return la lise de tous les filtres
     */
    public ArrayList<MenuRadioButtonItem> initializeMenuRadioButtonItem() {
        ArrayList<MenuRadioButtonItem> buttons = new ArrayList<>();
        for (String key : Model.RADIO_BUTTON_NAME.keySet()) {
            MenuRadioButtonItem b = new MenuRadioButtonItem(key);
            setMargin(b, new Insets(10));
            buttons.add(b);
            b.setVisible(false);
        }
        return buttons;
    }

    @Override
    public void setup(VisulogScene scene) {
        this.controller = scene.getController();
        this.getController().setMenuRadioButton(this);
    }

    /**
     * Initialise l'action de chaque Radio button en fonction de leur filtre
     *
     * @param plugin represente le nom de l'action
     */
    public void initMenuButtonAction(String plugin) {
        Controller controller = getController();
        for (Node node : this.getChildren()) {
            MenuRadioButtonItem b = (MenuRadioButtonItem) node;
            if (b != null) {
                b.setValue(plugin);
                b.setOnAction((event) -> controller.executeAction(b));
                b.setVisible(Model.PLUGINS.containsKey(b.getValue()));
            }
        }
    }

    public Controller getController() {
        return controller;
    }

    /**
     * RadioButton du menu
     */
    static class MenuRadioButtonItem extends MethodRadioButton implements PluginButtons {
        public MenuRadioButtonItem(String label) {
            super(label);
            this.setLayoutX(nextPosX);
            this.setLayoutX(0);
            nextPosX = this.getLayoutY() + 30;
        }

        @Override
        public String getPlugin() {
            return null;
        }
    }
}
