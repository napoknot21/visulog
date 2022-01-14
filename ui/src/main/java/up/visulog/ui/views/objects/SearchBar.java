package up.visulog.ui.views.objects;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.model.Model;
import up.visulog.ui.views.scenes.VisulogScene;

public class SearchBar extends VBox
implements SceneChild{
    private Controller controller;
    private Model model;
    private final SearchField searchField;
    private final SearchButton searchButton;
    public SearchBar() {
        super();
        searchField = new SearchField();
        searchButton = new SearchButton();
        getChildren().add(searchField);
        getChildren().add(searchButton);
    }

    @Override
    public void setup(VisulogScene scene) {
        this.controller = scene.getController();
        this.model = scene.getModel();
        searchButton.setOnAction(e -> controller.search(searchField.getText()));
    }

    private class SearchField extends TextField {
        private SearchField() {
            super();
            this.setPromptText("Entrez votre recherche");
        }
    }

    private class SearchButton extends Button {
        private SearchButton() {
            super("Rechercher");
        }
    }
}
