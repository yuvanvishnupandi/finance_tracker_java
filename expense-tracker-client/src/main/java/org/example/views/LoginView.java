package org.example.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.controllers.LoginController;
import org.example.utils.Utilitie;
import org.example.utils.ViewNavigator;

public class LoginView {
    private Label expenseTrackerLabel = new Label("Finance Tracker");
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Login");
    private Label signupLabel = new Label("Don't have an account? Click Here");

    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        new LoginController(this);
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        VBox mainContainerBox = new VBox(74);
        mainContainerBox.getStyleClass().addAll("main-background");
        mainContainerBox.setAlignment(Pos.TOP_CENTER);

        expenseTrackerLabel.getStyleClass().addAll("header", "text-white");

        VBox loginFormBox = createLoginFormBox();

        mainContainerBox.getChildren().addAll(expenseTrackerLabel, loginFormBox);
        return new Scene(mainContainerBox, Utilitie.APP_WIDTH, Utilitie.APP_HEIGHT);
    }


private VBox createLoginFormBox() {
    VBox formCard = new VBox(20); // spacing
    formCard.getStyleClass().addAll("ui-card");
    formCard.setMaxWidth(500);

    usernameField.getStyleClass().addAll("input-field");
    usernameField.setPromptText("Enter Username");

    passwordField.getStyleClass().addAll("input-field");
    passwordField.setPromptText("Enter Password");

    loginButton.getStyleClass().addAll("primary-button");

    signupLabel.getStyleClass().addAll("link-label");

    formCard.getChildren().addAll(usernameField, passwordField, loginButton, signupLabel);
    return formCard;
}
    public Label getExpenseTrackerLabel() {
        return expenseTrackerLabel;
    }

    public void setExpenseTrackerLabel(Label expenseTrackerLabel) {
        this.expenseTrackerLabel = expenseTrackerLabel;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

    public Label getSignupLabel() {
        return signupLabel;
    }

    public void setSignupLabel(Label signupLabel) {
        this.signupLabel = signupLabel;
    }
}
















