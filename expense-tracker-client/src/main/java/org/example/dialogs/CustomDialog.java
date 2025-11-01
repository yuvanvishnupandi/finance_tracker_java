package org.example.dialogs;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.example.models.User;

// FIX: Removed the restrictive "extends ButtonType" clause
public class CustomDialog<T> extends Dialog<T> { 
    protected User user;

    public CustomDialog(User user){
        this.user = user;

        getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
       // Remove specific styling; let ThemeManager apply based on current mode
// (nothing needed here except dynamic apply in each dialog)

        getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setVisible(false);
        okButton.setDisable(true);
    }
}