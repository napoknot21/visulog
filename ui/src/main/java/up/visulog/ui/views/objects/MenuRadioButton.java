package up.visulog.ui.views.objects;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

import java.util.ArrayList;


public class MenuRadioButton extends HBox
        implements SceneChild {//Genere le menu de filtres

    private static double nextPosX = 0;
    private Controller controller;
    private Model model;

    public MenuRadioButton() {
        super();
        initialize();
    }

    public void initialize() {
        this.getChildren().addAll(initializeMenuRadioButtonItem());
    }

    public ArrayList<MenuRadioButtonItem> initializeMenuRadioButtonItem() { //Cree tous les filtres automatiquement
        ArrayList<MenuRadioButtonItem> buttons = new ArrayList<>();
        for (String key : Model.RADIO_BUTTON_NAME.keySet()) {
            MenuRadioButtonItem b = new MenuRadioButtonItem(key);
            setMargin(b,new Insets(10));
            buttons.add(b);
            b.setVisible(false);
        }
        return buttons;
    }

    @Override
    public void setup(VisulogScene scene) {
        this.controller = scene.getController();
        this.model = scene.getModel();
        this.getController().setMenuRadioButton(this);
    }

    public void initMenuButtonAction(String plugin) { //Initialise l'action de chaque Radio button en fonction de leur filtre
        Controller controller = getController();
        for (Node node : this.getChildren()) {
            MenuRadioButtonItem b = (MenuRadioButtonItem) node;
            if (b != null) {
                b.setValue(plugin);
                b.setOnAction((event) -> controller.executeAction(b));
                b.setVisible(true);
            }
        }
    }


    static class MenuRadioButtonItem extends MethodRadioButton
            implements PluginButtons { // Correspond a un radio button
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

    public Controller getController() {
        return controller;
    }
}
