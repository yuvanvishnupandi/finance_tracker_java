package org.example.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.controllers.LoginController;
import org.example.controllers.SignUpController;
import org.example.utils.Utilitie;
import org.example.utils.ViewNavigator;

public class SignUpView {
    private Label expenseTrackerLabel = new Label("Finance Tracker");
    private TextField nameField = new TextField();
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField rePasswordField = new PasswordField();
    private Button registerButton = new Button("Register");
    private Label loginLabel = new Label("Already have an account? Login here");

    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        new SignUpController(this);
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        VBox mainContainer = new VBox(44);
        mainContainer.getStyleClass().addAll("main-background");
        mainContainer.setAlignment(Pos.TOP_CENTER);

        expenseTrackerLabel.getStyleClass().addAll("header", "text-white");

        VBox signUpFormContainer = createSignUpForm();

        mainContainer.getChildren().addAll(expenseTrackerLabel, signUpFormContainer);
        return new Scene(mainContainer, Utilitie.APP_WIDTH, Utilitie.APP_HEIGHT);
    }

    private VBox createSignUpForm(){
        VBox signUpForm = new VBox(30);
        signUpForm.setAlignment(Pos.CENTER);

        nameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        nameField.setPromptText("Enter Name");
        nameField.setMaxWidth(473);

        usernameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        usernameField.setPromptText("Enter Email");
        usernameField.setMaxWidth(473);

        passwordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(473);

        rePasswordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        rePasswordField.setPromptText("Re-Enter Password");
        rePasswordField.setMaxWidth(473);

        registerButton.getStyleClass().addAll("text-size-lg", "bg-light-blue", "text-white", "text-weight-700", "rounded-border");
        registerButton.setMaxWidth(473);

        loginLabel.getStyleClass().addAll("text-size-md", "text-light-gray", "text-underline", "link-text");

        signUpForm.getChildren().addAll(nameField, usernameField, passwordField, rePasswordField, registerButton, loginLabel);
        return signUpForm;
    }

    public Label getExpenseTrackerLabel() {
        return expenseTrackerLabel;
    }

    public void setExpenseTrackerLabel(Label expenseTrackerLabel) {
        this.expenseTrackerLabel = expenseTrackerLabel;
    }

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
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

    public PasswordField getRePasswordField() {
        return rePasswordField;
    }

    public void setRePasswordField(PasswordField rePasswordField) {
        this.rePasswordField = rePasswordField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(Button registerButton) {
        this.registerButton = registerButton;
    }

    public Label getLoginLabel() {
        return loginLabel;
    }

    public void setLoginLabel(Label loginLabel) {
        this.loginLabel = loginLabel;
    }
}
