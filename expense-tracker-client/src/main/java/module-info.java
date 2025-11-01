// File: expense-tracker-client/src/main/java/module-info.java
module expense.tracker.client {
    requires javafx.controls;
    requires javafx.graphics;

    requires com.google.gson;

    // PDFBox 3 is modular
    requires org.apache.pdfbox;
    requires org.apache.fontbox;   // transitive of pdfbox, safe to require
    requires java.desktop;         // fonts/graphics helpers used by pdfbox

    // Currency converter (HTTP client)
    requires java.net.http;

    exports org.example;
    exports org.example.controllers;
    exports org.example.views;
    exports org.example.dialogs;
    exports org.example.components;
    exports org.example.models;
    exports org.example.utils;
}