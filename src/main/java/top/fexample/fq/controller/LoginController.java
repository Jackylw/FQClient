package top.fexample.fq.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import top.fexample.fq.model.ConServerThread;
import top.fexample.fq.model.Msg;
import top.fexample.fq.model.User;
import top.fexample.fq.model.ConnectionServer;

public class LoginController {

    @FXML
    private TextField inputAccountId;

    @FXML
    private PasswordField inputAccountPwd;

    private Stage loginStage;

    public void setLoginStage(Stage primaryStage) {
        this.loginStage = primaryStage;
    }

    @FXML
    public void onLoginButtonClick() {

        try {
            // 账号密码的校验
            String userId = inputAccountId.getText().trim();
            String userPwd = inputAccountPwd.getText().trim();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (userId.isEmpty() || userPwd.isEmpty()) {
                setAlert(alert, "登录提示", "提示", "账号或密码不能为空");
                alert.showAndWait();
            } else {
                switch (checkLogin(userId, userPwd).getMsgType()) {
                    case Msg.LOGIN_SUCCESS:
                        setAlert(alert, "登录提示", "登录成功", "登录成功");
                        alert.showAndWait();

                        // 登录成功则关闭登录窗口
                        loginStage.close();

                        // 调用FriendListController的showFriendListStage方法，显示好友列表窗口
                        FriendListController friendListController = new FriendListController();
                        friendListController.showFriendListStage(userId);
                        break;
                    case Msg.LOGIN_ERROR:
                        setAlert(alert, "登录提示", "登录失败", "账号或密码错误");
                        alert.showAndWait();
                        break;
                    case Msg.CONNECTION_SERVER_ERROR:
                        setAlert(alert, "登录提示", "连接服务器失败", "连接服务器失败");
                        alert.showAndWait();
                        break;
                    default:
                        setAlert(alert, "登录提示", "登录失败", "未知错误");
                        alert.showAndWait();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 校验账号密码
    public Msg checkLogin(String accountId, String password) {
        User user = new User(accountId, password);
        ConnectionServer connectionServer = new ConnectionServer();
        return connectionServer.sendLoginInfoToServer(user);
    }

    // 提示窗口场景设置
    public void setAlert(Alert alert, String prompt, String title, String content) {
        alert.setTitle(title);
        alert.setHeaderText(prompt);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText(content);
    }
}
