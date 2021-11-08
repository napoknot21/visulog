

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,400,400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
3. Create a Text Message

        We shall replace BorderPane with StackPane and add a Text shape to the StackPane. The complete JavaFX Class program is as shown in the following program.

        Main.java
        import javafx.application.Application;
        import javafx.stage.Stage;
        import javafx.scene.Scene;
        import javafx.scene.layout.StackPane;
        import javafx.scene.text.Text;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // create a new Text shape
            Text messageText = new Text("Hello World! Lets learn JavaFX.");

            // stack page
            StackPane root = new StackPane();

            // add Text shape to Stack Pane
            root.getChildren().add(messageText);

            Scene scene = new Scene(root,400,400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
4. Run the Application

        Follow the clicksMain Menu ->Run ->Run.
        Basic JavaFX Example Application - JavaFX Tutorial - www.tutorialkart.com
        Application Window
        Conclusion

        In this JavaFX Tutorial – Basic JavaFX Example Application, we have learnt to create a JavaFX application, display a Text Shape, and run the application.
        ❮ Previous
        Next ❯
        Most Read Articles
        Most frequently asked Java Interview Questions
        Learn Encapsulation in Java with Example Programs
        Kotlin Tutorial - Learn Kotlin Programming Language
        Java Example to Read a String from Console
        Test your Java Basics with our Java Quiz
        ➥ PDF Download - Basic JavaFX Example Application
        Popular Tutorials
        Salesforce Tutorial
        SAP Tutorials
        Kafka Tutorial
        Kotlin Tutorial
        Interview Questions
        Salesforce Visualforce Interview Questions
        Salesforce Apex Interview Questions
        Kotlin Interview Questions
        Tutorial Kart
        About Us
        Contact Us
        Careers - Write for us
        Privacy Policy
        Terms of Use
        ✎ Write Us Feedback

        www.tutorialkart.com - ©Copyright - TutorialKart 2021
