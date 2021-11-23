package up.visulog.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import up.visulog.ui.controller.VMenu;

import java.io.IOException;

public class Test extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Test");
        initRootLayout();
    }

    public void initRootLayout() { //initialise la racine de l'application
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Test.class.getResource("/up/visulog/ui/RootLayout.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            setChildrenStage(scene.getRoot());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Fonction donnant aux enfants la possibilité d'agir avec le primaryStage
    private void setChildrenStage(Node node) { //Fonction de parcours
        if (node == null) return;
        if (node instanceof SplitPane) setChildrenStage((SplitPane) node);
        setChildrenStage((Parent) node);
    }

    private void setChildrenStage(Parent parent) { //Fonction de parcours
        if (parent == null) return;
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof VMenu) ((VMenu) node).setup(primaryStage);
            setChildrenStage(node);
        }
    }

    private void setChildrenStage(SplitPane parent) { //Fonction de parcours
        if (parent == null) return;
        for (Node node : parent.getItems()) {
            if (node instanceof VMenu) ((VMenu) node).setup(primaryStage);
            setChildrenStage(node);
        }
    }
}
