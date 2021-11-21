package up.visulog.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    private Stage primaryStage;
    //private BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //FixMe: Trouver un moyen de changer la scene pour afficher le plugin demande
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Test");
        initRootLayout();
        /*Scene scene = new Scene(new VMenuButton());
        //Scene scene = new Scene(new MenuRadioButton("CountCommits"));
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Test.class.getResource("/up/visulog/ui/RootLayout.fxml"));
            //String fxmlDocPath = "view/RootLayout.fxml";
            //FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
