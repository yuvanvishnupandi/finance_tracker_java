// File: expense-tracker-client/src/main/java/org/example/views/DashboardView.java
package org.example.views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.example.animations.LoadingAnimationPane;
import org.example.controllers.DashboardController;
import org.example.models.MonthlyFinance;
import org.example.utils.Utilitie;
import org.example.utils.ViewNavigator;
import org.example.utils.ThemeManager; 

import java.math.BigDecimal;
import java.time.Year;
import java.util.Objects;

public class DashboardView {

    private Label topGoalNameLabel;
    private ProgressBar topGoalProgressBar;
    private String email;
    private LoadingAnimationPane loadingAnimationPane;
    private Label currentBalanceLabel, currentBalance;
    private Label totalIncomeLabel, totalIncome;
    private Label totalExpenseLabel, totalExpense;

    private Label budgetStatusLabel, budgetRemaining;

    private Label userNameLabel, userEmailLabel;
    private ImageView userIcon;
    
    // ADDED: Theme Toggle Button
    private ToggleButton themeToggle; 
    private VBox mainContent; // ADDED: Reference to the VBox holding the main background

    private ComboBox<Integer> yearComboBox;
    private ComboBox<String> monthQuarterComboBox;

    private Button addTransactionButton, viewChartButton;
    private VBox recentTransactionBox;
    private MenuBar menuBar;

    // Menu Item Fields
    private MenuItem createCategoryMenuItem, viewCategoriesMenuItem, logoutMenuItem;
    private MenuItem exportDataMenuItem;
    private MenuItem generatePdfReportMenuItem;
    private MenuItem setMonthlyBudgetsMenuItem;
    private MenuItem viewBudgetProgressMenuItem;
    private MenuItem addGoalMenuItem;
    private MenuItem viewGoalsMenuItem;
    private MenuItem aboutUsMenuItem; // ADDED: About Us menu item

    // NEW: currency converter menu item
    private MenuItem convertCurrencyMenuItem;

    private TableView<MonthlyFinance> transactionTable;
    private TableColumn<MonthlyFinance, String> monthColumn;
    private TableColumn<MonthlyFinance, BigDecimal> incomeColumn;
    private TableColumn<MonthlyFinance, BigDecimal> expenseColumn;

    public DashboardView(String email) {
        this.email = email;
        loadingAnimationPane = new LoadingAnimationPane(Utilitie.APP_WIDTH, Utilitie.APP_HEIGHT);

        currentBalanceLabel = new Label("Current Balance:");
        totalIncomeLabel = new Label("Total Income:");
        totalExpenseLabel = new Label("Total Expense:");

        budgetStatusLabel = new Label("Budget Remaining:");
        budgetRemaining = new Label("₹0.00");

        currentBalance = new Label("₹0.00");
        totalIncome = new Label("₹0.00");
        totalExpense = new Label("₹0.00");
        addTransactionButton = new Button("+");

        monthQuarterComboBox = new ComboBox<>();

        userNameLabel = new Label("");
        userEmailLabel = new Label("");
        userNameLabel.getStyleClass().addAll("user-name-label");
        userEmailLabel.getStyleClass().addAll("user-email-label");

        userIcon = createUserIcon();
        
        // ADDED: Initialize Theme Toggle
        themeToggle = new ToggleButton(); 
        themeToggle.getStyleClass().add("theme-toggle");
        
        createCategoryMenuItem = new MenuItem("Add Category");
        viewCategoriesMenuItem = new MenuItem("View Categories");
        exportDataMenuItem = new MenuItem("Export Data (CSV)");
        generatePdfReportMenuItem = new MenuItem("Generate PDF Report");
        logoutMenuItem = new MenuItem("Logout");
        setMonthlyBudgetsMenuItem = new MenuItem("Set Monthly Budgets");
        viewBudgetProgressMenuItem = new MenuItem("View Budget Progress");
        addGoalMenuItem = new MenuItem("Add Goal");
        viewGoalsMenuItem = new MenuItem("View Goals");
        aboutUsMenuItem = new MenuItem("About Us"); // ADDED: About Us menu item
        
        // NEW: Initialize the Currency Converter Menu Item
        convertCurrencyMenuItem = new MenuItem("Convert Currency...");

        yearComboBox = new ComboBox<>();
        transactionTable = new TableView<>();
        recentTransactionBox = new VBox();
        topGoalNameLabel = new Label();
        topGoalProgressBar = new ProgressBar();
        viewChartButton = new Button("View Chart");
    }

    private ImageView createUserIcon() {
        String iconPath = "/images/userlogo.png";
        try {
            Image iconImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
            ImageView iv = new ImageView(iconImage);
            iv.setFitWidth(24);
            iv.setFitHeight(24);
            return iv;
        } catch (Exception e) {
            System.err.println("Could not load user icon at: " + iconPath + ". Using empty ImageView.");
            return new ImageView();
        }
    }

    public void show() {
        Scene scene = createScene();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        
        // --- ADDED THEME LOGIC ---
        // 1. Initial State: Start in Dark Mode
        ThemeManager.setDark(true); 
        
        // 2. Apply initial background class based on state
        mainContent.getStyleClass().add(ThemeManager.isDark() ? "main-background-dark" : "main-background-light");
        themeToggle.setText(ThemeManager.isDark() ? "🌙 Dark" : "☀ Light");
        themeToggle.setSelected(ThemeManager.isDark());
        
        // 3. Listener to switch ONLY the main background class
        themeToggle.selectedProperty().addListener((obs, oldV, isDark) -> {
            ThemeManager.setDark(isDark);
            if (isDark) {
                // Switch to Dark
                mainContent.getStyleClass().removeAll("main-background-light");
                mainContent.getStyleClass().add("main-background-dark");
                themeToggle.setText("🌙 Dark");
            } else {
                // Switch to Light (White background)
                mainContent.getStyleClass().removeAll("main-background-dark");
                mainContent.getStyleClass().add("main-background-light");
                themeToggle.setText("☀ Light");
            }
        });
        // --- END ADDED THEME LOGIC ---
        
        new DashboardController(this);
        scene.widthProperty().addListener((observable, oldVal, newVal) -> {
            loadingAnimationPane.resizeWidth(newVal.doubleValue());
            resizeTableWidthColumns();
        });
        scene.heightProperty().addListener((observable, oldVal, newVal) ->
                loadingAnimationPane.resizeHeight(newVal.doubleValue()));
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene() {
        menuBar = createMenuBar();

        StackPane rootStack = new StackPane();
        mainContent = new VBox(); // Assigned to class field
        // mainContent.getStyleClass().add("main-background"); // Removed standard class here

        HBox topBar = buildTopBar();

        VBox mainContainerWrapper = new VBox();
        mainContainerWrapper.getStyleClass().add("dashboard-padding");
        VBox.setVgrow(mainContainerWrapper, Priority.ALWAYS);
        HBox balanceSummaryBox = createBalanceSummaryBox();
        GridPane contentGridPane = createContentGridPane();
        VBox.setVgrow(contentGridPane, Priority.ALWAYS);
        mainContainerWrapper.getChildren().addAll(balanceSummaryBox, contentGridPane);

        mainContent.getChildren().addAll(topBar, mainContainerWrapper);
        rootStack.getChildren().addAll(mainContent, loadingAnimationPane);

        return new Scene(rootStack, Utilitie.APP_WIDTH, Utilitie.APP_HEIGHT);
    }

    private HBox buildTopBar() {
        HBox topBarContent = new HBox();
        topBarContent.setAlignment(Pos.CENTER_LEFT);
        topBarContent.setSpacing(10);
        topBarContent.setPadding(new Insets(6, 10, 6, 10));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox userDetailBox = new VBox(0, userNameLabel, userEmailLabel);
        userDetailBox.setAlignment(Pos.CENTER_RIGHT);

        HBox userBox = new HBox(6, userIcon, userDetailBox);
        userBox.setAlignment(Pos.CENTER_RIGHT);

        // ADDED: Combine Theme Toggle and User Info
        HBox rightControls = new HBox(10, themeToggle, userBox);
        rightControls.setAlignment(Pos.CENTER_RIGHT);

        topBarContent.getChildren().addAll(menuBar, spacer, rightControls); // Use rightControls

        HBox fullWidthBar = new HBox(topBarContent);
        fullWidthBar.getStyleClass().add("top-bar-background");
        HBox.setHgrow(topBarContent, Priority.ALWAYS);

        return fullWidthBar;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent;");
        menuBar.getStyleClass().remove("top-menu");

        Menu categoryMenu = new Menu("Category");
        categoryMenu.getItems().addAll(createCategoryMenuItem, viewCategoriesMenuItem);

        Menu savingsMenu = new Menu("Savings");
        savingsMenu.getItems().addAll(addGoalMenuItem, viewGoalsMenuItem);

        Menu budgetMenu = new Menu("Budget");
        budgetMenu.getItems().addAll(setMonthlyBudgetsMenuItem, viewBudgetProgressMenuItem);

        Menu exportMenu = new Menu("Export Data");
        exportMenu.getItems().addAll(exportDataMenuItem, generatePdfReportMenuItem);

        // NEW Currency menu before Logout
        Menu currencyMenu = new Menu("Currency");
        currencyMenu.getItems().add(convertCurrencyMenuItem);

        Menu helpMenu = new Menu("Help"); // ADDED Help Menu
        helpMenu.getItems().add(aboutUsMenuItem);

        Menu logoutMenu = new Menu("Logout");
        logoutMenu.getItems().add(logoutMenuItem);

        // ADDED helpMenu to the menuBar list
        menuBar.getMenus().addAll(categoryMenu, savingsMenu, budgetMenu, exportMenu, currencyMenu, helpMenu, logoutMenu);
        return menuBar;
    }

    private HBox createBalanceSummaryBox() {
        HBox statBox = new HBox(40);
        statBox.setAlignment(Pos.CENTER);
        statBox.setStyle("-fx-padding: 20 0 40 0;");

        VBox balanceCard = new VBox(10);
        balanceCard.getStyleClass().add("stat-card");
        currentBalanceLabel.getStyleClass().setAll("stat-label");
        currentBalance.getStyleClass().setAll("stat-amount");
        balanceCard.getChildren().addAll(currentBalanceLabel, currentBalance);
        HBox.setHgrow(balanceCard, Priority.ALWAYS);

        VBox incomeCard = new VBox(10);
        incomeCard.getStyleClass().add("stat-card");
        totalIncomeLabel.getStyleClass().setAll("stat-label");
        totalIncome.getStyleClass().setAll("stat-amount");
        incomeCard.getChildren().addAll(totalIncomeLabel, totalIncome);
        HBox.setHgrow(incomeCard, Priority.ALWAYS);

        VBox expenseCard = new VBox(10);
        expenseCard.getStyleClass().add("stat-card");
        totalExpenseLabel.getStyleClass().setAll("stat-label");
        totalExpense.getStyleClass().setAll("stat-amount");
        expenseCard.getChildren().addAll(totalExpenseLabel, totalExpense);
        HBox.setHgrow(expenseCard, Priority.ALWAYS);

        VBox budgetCard = new VBox(10);
        budgetCard.getStyleClass().add("stat-card");
        budgetStatusLabel.getStyleClass().setAll("stat-label");
        budgetRemaining.getStyleClass().setAll("stat-amount");
        budgetCard.getChildren().addAll(budgetStatusLabel, budgetRemaining);
        HBox.setHgrow(budgetCard, Priority.ALWAYS);

        VBox topGoalCard = new VBox(8);
        topGoalCard.getStyleClass().add("stat-card");
        Label topGoalLabel = new Label("Savings Progress:");
        topGoalLabel.getStyleClass().add("stat-label");
        // FIX: The text is now set by the controller based on the actual goal status
        Label topGoalNameLabel = new Label("Check Savings Menu"); 
        topGoalNameLabel.getStyleClass().add("stat-amount");
        topGoalProgressBar = new ProgressBar(0.0);
        topGoalProgressBar.setPrefWidth(200);
        topGoalCard.getChildren().addAll(topGoalLabel, topGoalNameLabel, topGoalProgressBar);
        HBox.setHgrow(topGoalCard, Priority.ALWAYS);

        statBox.getChildren().addAll(balanceCard, incomeCard, expenseCard, budgetCard, topGoalCard);
        return statBox;
    }

    private GridPane createContentGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        ColumnConstraints columnConstraint = new ColumnConstraints();
        columnConstraint.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(columnConstraint, columnConstraint);
        VBox transactionsTableSummaryBox = new VBox(20);

        HBox filterAndChartButtonBox = createFilterAndChartButtonBox();

        VBox transactionTableContentBox = createTransactionsTableContentBox();
        VBox.setVgrow(transactionTableContentBox, Priority.ALWAYS);

        transactionsTableSummaryBox.getChildren().addAll(filterAndChartButtonBox, transactionTableContentBox);

        VBox recentTransactionsVBox = createRecentTransactionsVBox();
        recentTransactionsVBox.getStyleClass().addAll("field-background", "rounded-border", "padding-10px");
        GridPane.setVgrow(recentTransactionsVBox, Priority.ALWAYS);
        gridPane.add(transactionsTableSummaryBox, 0, 0);
        gridPane.add(recentTransactionsVBox, 1, 0);
        return gridPane;
    }

    private HBox createFilterAndChartButtonBox() {
        HBox hbox = new HBox(15);

        yearComboBox = new ComboBox<>();
        yearComboBox.getStyleClass().add("text-size-md");
        yearComboBox.setValue(Year.now().getValue());

        monthQuarterComboBox = new ComboBox<>();
        monthQuarterComboBox.getStyleClass().add("text-size-md");
        monthQuarterComboBox.setPromptText("Month/Quarter");

        viewChartButton = new Button("View Chart");
        viewChartButton.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md");

        hbox.getChildren().addAll(yearComboBox, monthQuarterComboBox, viewChartButton);
        return hbox;
    }

    private VBox createTransactionsTableContentBox() {
        VBox vbox = new VBox();
        transactionTable = new TableView<>();
        VBox.setVgrow(transactionTable, Priority.ALWAYS);
        
        monthColumn = new TableColumn<>("Month");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");
        
        incomeColumn = new TableColumn<>("Income");
        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
        incomeColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");
        
        expenseColumn = new TableColumn<>("Expense");
        expenseColumn.setCellValueFactory(new PropertyValueFactory<>("expense"));
        expenseColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");
        
        transactionTable.getColumns().addAll(monthColumn, incomeColumn, expenseColumn);
        vbox.getChildren().add(transactionTable);
        resizeTableWidthColumns();
        return vbox;
    }

    private VBox createRecentTransactionsVBox() {
        VBox recentTransactionsVBox = new VBox();
        HBox labelAndButtonBox = new HBox();
        Label recentTransactionsLabel = new Label("Recent Transactions");
        recentTransactionsLabel.getStyleClass().addAll("text-size-lg", "text-light-gray");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        addTransactionButton.getStyleClass().addAll("field-background", "text-size-md", "text-light-gray", "rounded-border");
        labelAndButtonBox.getChildren().addAll(recentTransactionsLabel, spacer, addTransactionButton);
        recentTransactionBox = new VBox(10);
        ScrollPane recentTransactionsScrollPane = new ScrollPane(recentTransactionBox);
        recentTransactionsScrollPane.setFitToWidth(true);
        recentTransactionsScrollPane.setFitToHeight(true);
        recentTransactionsVBox.getChildren().addAll(labelAndButtonBox, recentTransactionsScrollPane);
        return recentTransactionsVBox;
    }

    private void resizeTableWidthColumns() {
        Platform.runLater(() -> {
            double width = transactionTable.getWidth() * 0.335;
            monthColumn.setPrefWidth(width);
            incomeColumn.setPrefWidth(width);
            expenseColumn.setPrefWidth(width);
        });
    }

    public Label getBudgetStatusLabel() { return budgetStatusLabel; }
    public Label getBudgetRemaining() { return budgetRemaining; }
    public MenuBar getMenuBar() { return this.menuBar; }
    public MenuItem getCreateCategoryMenuItem() { return createCategoryMenuItem; }
    public MenuItem getViewCategoriesMenuItem() { return viewCategoriesMenuItem; }
    public MenuItem getExportDataMenuItem() { return exportDataMenuItem; }
    public MenuItem getGeneratePdfReportMenuItem() { return generatePdfReportMenuItem; }
    public MenuItem getLogoutMenuItem() { return logoutMenuItem; }
    public MenuItem getSetMonthlyBudgetsMenuItem() { return setMonthlyBudgetsMenuItem; }
    public MenuItem getViewBudgetProgressMenuItem() { return viewBudgetProgressMenuItem; }
    public MenuItem getAddGoalMenuItem() { return addGoalMenuItem; }
    public MenuItem getViewGoalsMenuItem() { return viewGoalsMenuItem; }
    public MenuItem getAboutUsMenuItem() { return aboutUsMenuItem; }
    public String getEmail() { return email; }
    public Button getAddTransactionButton() { return addTransactionButton; }
    public VBox getRecentTransactionBox() { return recentTransactionBox; }
    public LoadingAnimationPane getLoadingAnimationPane() { return loadingAnimationPane; }
    public TableView<MonthlyFinance> getTransactionTable() { return transactionTable; }
    public TableColumn<MonthlyFinance, String> getMonthColumn() { return monthColumn; }
    public TableColumn<MonthlyFinance, BigDecimal> getIncomeColumn() { return incomeColumn; }
    public TableColumn<MonthlyFinance, BigDecimal> getExpenseColumn() { return expenseColumn; }
    public ComboBox<Integer> getYearComboBox() { return yearComboBox; }
    public Label getCurrentBalance() { return currentBalance; }
    public Label getTotalIncome() { return totalIncome; }
    public Label getTotalExpense() { return totalExpense; }
    public Button getViewChartButton() { return viewChartButton; }
    public ComboBox<String> getMonthQuarterComboBox() { return monthQuarterComboBox; }
    
    // User details getters
    public Label getUserNameLabel() { return userNameLabel; }
    public Label getUserEmailLabel() { return userEmailLabel; }
    
    // NEW: getter for currency menu
    public MenuItem getConvertCurrencyMenuItem() { return convertCurrencyMenuItem; }

    public Label getTopGoalNameLabel() { return topGoalNameLabel; }
    public ProgressBar getTopGoalProgressBar() { return topGoalProgressBar; }
    
    // ADDED: Getter for the Theme Toggle
    public ToggleButton getThemeToggle() { return themeToggle; }
}