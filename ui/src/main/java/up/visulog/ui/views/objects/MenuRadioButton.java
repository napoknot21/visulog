package up.visulog.ui.views.objects;

import javafx.scene.Node;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;

import java.util.ArrayList;


public class MenuRadioButton extends VMenu
        implements SceneChild {//Genere le menu de filtres

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
        for (String key : Model.RADIO_BUTTON_NAME.keySet()) {
            MenuRadioButtonItem b = new MenuRadioButtonItem(key);
            buttons.add(b);
            b.setVisible(false);
        }
        return buttons;
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


    class MenuRadioButtonItem extends MethodRadioButton
            implements VisulogButtons { // Correspond a un radio button
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
