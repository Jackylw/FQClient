package top.fexample.fq;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import top.fexample.fq.controller.LoginController;
import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage loginStage) throws IOException {
        // 获取LoginController
        FXMLLoader loginViewLoader = new FXMLLoader(Application.class.getResource("views/LoginView.fxml"));
        Parent loginView = loginViewLoader.load();
        LoginController loginController = loginViewLoader.getController();

        // 通过LoginController设置登录界面
        loginController.setLoginStage(loginStage);

        // 显示登录界面
        loginStage.setResizable(false);
        loginStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("images/qq.gif")).toExternalForm()));
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(loginView));
        loginStage.show();

        // 关闭登录窗口的监听事件
        loginStage.setOnCloseRequest(event -> {
            Platform.exit();
        });
    }
    public static void main(String[] args) {
        launch();
    }
}