module galasefu.lab6incercare1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires jdk.jshell;
    requires java.desktop;

    opens galasefu.lab6incercare1 to javafx.fxml;
    opens galasefu.lab6incercare1.controller to javafx.fxml;


    exports galasefu.lab6incercare1;
    exports galasefu.lab6incercare1.domain;
    exports galasefu.lab6incercare1.controller;
}