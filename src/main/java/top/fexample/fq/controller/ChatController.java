package top.fexample.fq.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import top.fexample.fq.Application;
import top.fexample.fq.model.ConnectionServer;
import top.fexample.fq.model.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatController implements Runnable {
    @FXML
    public TextArea sendInformation;

    @FXML
    public TextArea receiveInformation;

    @FXML
    private TextField chatUserId;

    @FXML
    private TextField chatUserStatus;

    private String senderId;

    private String receiverId;

    private static Map<String, Stage> openChatStages = new HashMap<>();

    public void showChatStage(Parent chat, String userId, String accountId, String status) {
        setSenderAndReceiverId(userId, accountId);
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
            chatStage.setTitle(userId + "正在与" + accountId + "聊天");
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

    // 设置发送者和接收者id
    public void setSenderAndReceiverId(String senderId, String receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    // 当点击发送按钮时，将消息发送到服务器
    @FXML
    public void onSendButtonClick() {
        Msg msg = new Msg();
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        msg.setMsgContent(sendInformation.getText());
        msg.setSendTime();

        // 发送消息到服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ConnectionServer.clientSocket.getOutputStream());
            oos.writeObject(msg);
            sendInformation.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while (true) {
            try {
                // 接收消息并显示
                ObjectInputStream ois = new ObjectInputStream(ConnectionServer.clientSocket.getInputStream());
                Msg msg = (Msg) ois.readObject();
                String info = msg.getSenderId() + " " + msg.getSendTime() + "\n" + msg.getMsgContent() + "\n";
                System.out.println(info);
                receiveInformation.appendText(info);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
