module expense.tracker.client {
    requires javafx.controls;
    requires com.google.gson;

    // this is crucial to be able to read data from models and store them into our tables
    opens org.example.models to javafx.base;

    exports org.example;
}