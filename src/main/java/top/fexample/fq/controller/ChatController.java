package top.fexample.fq.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import top.fexample.fq.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatController {

    @FXML
    private TextField chatUserId;

    @FXML
    private TextField chatUserStatus;

    private static Map<String, Stage> openChatStages = new HashMap<>();

    public void showChatStage(Parent chat, String accountId, String status) {
        // 如果聊天框已打开，将其置顶显示而非新建
        Stage exsitChatStage = openChatStages.get(accountId);
        if (exsitChatStage != null) {
            exsitChatStage.toFront();
        } else {
            // 初始化聊天框
            setChatStage(accountId, status);

            Stage chatStage = new Stage();
            chatStage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResource("images/qq.gif")).toExternalForm()));
            chatStage.setResizable(false);
            chatStage.setTitle("正在与" + accountId + "聊天");
            chatStage.setScene(new Scene(chat));

            // 窗口关闭监听器
            chatStage.setOnCloseRequest(event -> onChatStageClose(accountId));

            // 存储打开的聊天窗口
            openChatStages.put(accountId, chatStage);

            chatStage.show();
        }
    }

    // 设置聊天框信息
    public void setChatStage(String accountId, String status) {
        chatUserId.setText(accountId);
        chatUserStatus.setText(status);
    }

    // 当关闭某个聊天窗口时，从map中移除它，否则导致关闭后无法再次打开
    public void onChatStageClose(String accountId) {
        Stage chatStage = openChatStages.remove(accountId);
        if (chatStage != null) {
            chatStage.close();
        }
    }
}
