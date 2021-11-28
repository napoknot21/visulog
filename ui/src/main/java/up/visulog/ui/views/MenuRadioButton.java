package up.visulog.ui.views;

import javafx.scene.Node;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;

import java.util.ArrayList;


public class MenuRadioButton extends VMenu {//Genere le menu de filtres

    private static double nextPosX = 0;

    public MenuRadioButton() {
        super();
        initialize();
    }

    public void initialize() {
        this.getChildren().addAll(initializeMenuRadioButtonItem());
    }

    public ArrayList<MenuRadioButtonItem> initializeMenuRadioButtonItem() { //Cree tous les filtres automatiquement
        Model model = getModel();
        ArrayList<MenuRadioButtonItem> buttons = new ArrayList<>();
        for (String key : model.getRADIO_BUTTON_NAME().keySet()) {
            MenuRadioButtonItem b = new MenuRadioButtonItem(key);
            buttons.add(b);
        }
        return buttons;
    }


    public void initMenuButtonAction(String plugin) { //Initialise l'action de chaque Radio button en fonction de leur filtre
        Controller controller = getController();
        for (Node node : this.getChildren()) {
            MenuRadioButtonItem b = (MenuRadioButtonItem) node;

            if (b != null) {
                b.setOnAction((event) -> controller.executeAction(b));
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

        @Override
        public String getPlugin() {
            return null;
        }

        @Override
        public Node getStyleableNode() {
            return super.getStyleableNode();
        }
    }

}
