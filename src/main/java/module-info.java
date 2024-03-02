module top.fexample.fqclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens top.fexample.fq to javafx.fxml;
    exports top.fexample.fq;
    exports top.fexample.fq.controller;
    opens top.fexample.fq.controller to javafx.fxml;
}