package org.example.dialogs;

import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.components.TransactionComponent;
import org.example.controllers.DashboardController;
import org.example.models.Transaction;
import org.example.models.TransactionCategory;
import org.example.utils.SqlUtil;
import org.example.utils.ThemeManager;
import org.example.utils.Utilitie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateOrEditTransactionDialog extends CustomDialog {

    private final List<TransactionCategory> transactionCategories;
    private final DashboardController dashboardController;
    private final boolean isEditing;

    private final TransactionComponent transactionComponent;

    private TextField transactionNameField;
    private TextField transactionAmountField;
    private DatePicker transactionDatePicker;
    private ComboBox<String> transactionCategoryBox;
    private ToggleGroup transactionTypeToggleGroup;

    public CreateOrEditTransactionDialog(DashboardController dashboardController, boolean isEditing) {
        this(dashboardController, null, isEditing);
    }

    public CreateOrEditTransactionDialog(DashboardController dashboardController,
                                         TransactionComponent transactionComponent,
                                         boolean isEditing) {
        super(dashboardController.getUser());

        this.dashboardController = dashboardController;
        this.transactionComponent = transactionComponent;
        this.isEditing = isEditing;

        setTitle(isEditing ? "Edit Transaction" : "Create New Transaction");
        setWidth(700);
        setHeight(595);

        transactionCategories = SqlUtil.getAllTransactionCategoriesByUser(user);

        VBox mainContentBox = createMainContentBox();
        getDialogPane().setContent(mainContentBox);

        // ✅ Apply dark/light theme dynamically
        // Platform.runLater(() -> ThemeManager.apply(getDialogPane().getScene()));
    }

    private VBox createMainContentBox() {
        VBox mainContentBox = new VBox(30);
        mainContentBox.setAlignment(Pos.CENTER);

        transactionNameField = new TextField();
        transactionNameField.setPromptText("Enter Transaction Name");
        transactionNameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");

        transactionAmountField = new TextField();
        transactionAmountField.setPromptText("Enter Transaction Amount");
        transactionAmountField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");

        transactionDatePicker = new DatePicker();
        transactionDatePicker.setPromptText("Enter Transaction Date");
        transactionDatePicker.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");
        transactionDatePicker.setMaxWidth(Double.MAX_VALUE);

        transactionCategoryBox = new ComboBox<>();
        transactionCategoryBox.setPromptText("Choose Category");
        transactionCategoryBox.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");
        transactionCategoryBox.setMaxWidth(Double.MAX_VALUE);

        for (TransactionCategory category : transactionCategories) {
            transactionCategoryBox.getItems().add(category.getCategoryName());
        }

        if (isEditing && transactionComponent != null) {
            Transaction t = transactionComponent.getTransaction();
            transactionNameField.setText(t.getTransactionName());
            transactionAmountField.setText(String.valueOf(t.getTransactionAmount()));
            transactionDatePicker.setValue(t.getTransactionDate());

            if (t.getTransactionCategory() != null)
                transactionCategoryBox.setValue(t.getTransactionCategory().getCategoryName());
        }

        mainContentBox.getChildren().addAll(
                transactionNameField,
                transactionAmountField,
                transactionDatePicker,
                transactionCategoryBox,
                createTransactionTypeRadioButtonGroup(),
                createConfirmAndCancelButtonsBox()
        );

        return mainContentBox;
    }

    private HBox createTransactionTypeRadioButtonGroup() {
        HBox radioButtonsBox = new HBox(50);
        radioButtonsBox.setAlignment(Pos.CENTER);

        transactionTypeToggleGroup = new ToggleGroup();

        RadioButton income = new RadioButton("Income");
        income.setToggleGroup(transactionTypeToggleGroup);
        income.getStyleClass().addAll("text-size-md", "text-light-gray");

        RadioButton expense = new RadioButton("Expense");
        expense.setToggleGroup(transactionTypeToggleGroup);
        expense.getStyleClass().addAll("text-size-md", "text-light-gray");

        if (isEditing && transactionComponent != null) {
            String type = transactionComponent.getTransaction().getTransactionType();
            if ("income".equalsIgnoreCase(type)) income.setSelected(true);
            else expense.setSelected(true);
        }

        radioButtonsBox.getChildren().addAll(income, expense);
        return radioButtonsBox;
    }

    private HBox createConfirmAndCancelButtonsBox() {
        HBox box = new HBox(50);
        box.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(200);
        saveButton.getStyleClass().addAll("bg-light-blue", "text-white", "text-size-md", "rounded-border");

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(200);
        cancelButton.getStyleClass().addAll("text-size-md", "rounded-border");

        saveButton.setOnMouseClicked(e -> handleSave());
        cancelButton.setOnMouseClicked(e -> CreateOrEditTransactionDialog.this.close());

        box.getChildren().addAll(saveButton, cancelButton);
        return box;
    }

    private void handleSave() {
        JsonObject txData = new JsonObject();

        if (isEditing && transactionComponent != null) {
            txData.addProperty("id", transactionComponent.getTransaction().getId());
        }

        // Extract values
        String name = transactionNameField.getText().trim();
        String rawAmount = transactionAmountField.getText().trim();
        LocalDate date = transactionDatePicker.getValue();
        Toggle selectedToggle = transactionTypeToggleGroup.getSelectedToggle();
        String type = selectedToggle == null ? null : ((RadioButton) selectedToggle).getText();
        String categoryName = transactionCategoryBox.getValue();

        // Validate entries
        if (name.isEmpty() || rawAmount.isEmpty() || date == null || type == null) {
            Utilitie.showAlertDialog(Alert.AlertType.WARNING, "Please fill all required fields.");
            return;
        }

        try {
            double amount = Double.parseDouble(rawAmount);
            txData.addProperty("transactionName", name);
            txData.addProperty("transactionAmount", amount);
            txData.addProperty("transactionDate", date.format(DateTimeFormatter.ISO_LOCAL_DATE));
            txData.addProperty("transactionType", type);

            if (categoryName != null && !categoryName.isEmpty()) {
                TransactionCategory category = Utilitie.findTransactionCategoryByName(transactionCategories, categoryName);
                if (category != null) {
                    JsonObject catObj = new JsonObject();
                    catObj.addProperty("id", category.getId());
                    txData.add("transactionCategory", catObj);
                }
            }

            JsonObject userObj = new JsonObject();
            userObj.addProperty("id", user.getId());
            txData.add("user", userObj);

            boolean success = !isEditing
                    ? SqlUtil.postTransaction(txData)
                    : SqlUtil.putTransaction(txData);

            if (success) {
                Utilitie.showAlertDialog(Alert.AlertType.INFORMATION,
                        isEditing ? "Transaction updated!" : "Transaction created!");

                dashboardController.fetchUserData(); // ✅ Refresh dashboard
                this.close();
            } else {
                Utilitie.showAlertDialog(Alert.AlertType.ERROR,
                        isEditing ? "Failed to update transaction." : "Failed to create transaction.");
            }

        } catch (NumberFormatException ex) {
            Utilitie.showAlertDialog(Alert.AlertType.ERROR, "Enter a valid transaction amount!");
        } catch (Exception ex) {
            Utilitie.showAlertDialog(Alert.AlertType.ERROR, "Something went wrong.");
            ex.printStackTrace();
        }
    }
}