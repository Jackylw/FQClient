package top.fexample.fq.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserController {
    @FXML
    private Label accountIdLabel;
    @FXML
    private Label statusLabel;
    // 初始化每一个好友的信息
    public void setUserController(String accountId, String status) {
        accountIdLabel.setText(accountId);
        statusLabel.setText(status);
    }
}
