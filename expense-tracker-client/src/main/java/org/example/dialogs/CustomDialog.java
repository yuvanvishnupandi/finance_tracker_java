package org.example.dialogs;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.example.models.User;

public class CustomDialog extends Dialog {
    protected User user;

    public CustomDialog(User user){
        this.user = user;

        // add the stylesheet
        getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        getDialogPane().getStyleClass().addAll("main-background");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setVisible(false);
        okButton.setDisable(true);
    }
}
