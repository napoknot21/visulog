package up.visulog.ui.views.objects;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import up.visulog.ui.controllers.Controller;
import up.visulog.ui.views.scenes.VisulogScene;

/**
 * Barre de recherche
 */
public class SearchBar extends VBox implements SceneChild {
    private final SearchField searchField;
    private final SearchButton searchButton;
    private Controller controller;

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
        searchButton.setOnAction(e -> controller.search(searchField.getText()));
    }

    /**
     * Represente le champ texte de la barre de recherche
     */
    private static class SearchField extends TextField {
        private SearchField() {
            super();
            this.setPromptText("Entrez votre recherche");
        }
    }

    /**
     * Represente le bouton recherche de la barre de recherche
     */
    private static class SearchButton extends Button {
        private SearchButton() {
            super("Rechercher");
        }
    }
}
