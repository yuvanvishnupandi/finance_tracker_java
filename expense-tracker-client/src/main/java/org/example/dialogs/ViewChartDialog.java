package org.example.dialogs;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.models.MonthlyFinance;
import org.example.models.User;


public class ViewChartDialog extends CustomDialog{
    public ViewChartDialog(User user, ObservableList<MonthlyFinance> monthlyFinances) {
        super(user);
        setTitle("View Chart");
        setWidth(700);
        setHeight(595);

        VBox barChartBox = new VBox();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month");
        xAxis.setTickLabelFill(Paint.valueOf("#BEB989"));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");
        yAxis.setTickLabelFill(Paint.valueOf("#BEB989"));

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setMinHeight(getWidth() - 25);
        barChart.setMinHeight(getHeight() - 50);
        barChart.getStyleClass().add("text-size-md");

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("income");

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("expense");

        // populated the data for the income and expense
        for(MonthlyFinance monthlyFinance : monthlyFinances){
            incomeSeries.getData().add(new XYChart.Data<>(monthlyFinance.getMonth(), monthlyFinance.getIncome()));
            expenseSeries.getData().add(new XYChart.Data<>(monthlyFinance.getMonth(), monthlyFinance.getExpense()));
        }

        barChart.getData().addAll(incomeSeries, expenseSeries);

        // update the bar colors for the income
        incomeSeries.getData().forEach(data -> data.getNode().setStyle("-fx-bar-fill: #33ba2f"));

        // update the bar colors for the expense
        expenseSeries.getData().forEach(data -> data.getNode().setStyle("-fx-bar-fill: #ba2f2f"));

        barChartBox.getChildren().add(barChart);
        getDialogPane().setContent(barChartBox);
    }
}










