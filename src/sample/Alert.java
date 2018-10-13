package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {

    public static void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);

        Label messageLabel = new Label();
        messageLabel.setText(message);

        Button closeButton = new Button("OK");
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox();
        layout.getChildren().addAll(messageLabel, closeButton);


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
