module com.bank.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.bank.app to javafx.fxml;
    exports com.bank.app;
    exports com.bank.app.controllers;
    exports com.bank.app.controllers.admin;
    exports com.bank.app.controllers.client;
    exports com.bank.app.models;
    exports com.bank.app.views;
}