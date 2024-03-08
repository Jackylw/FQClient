package top.fexample.fq.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import top.fexample.fq.Application;
import top.fexample.fq.model.ManageChatView;
import top.fexample.fq.model.ManageServerThread;
import top.fexample.fq.model.Msg;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatController {
    @FXML
    public TextArea sendInformation;

    @FXML
    public Button sendInformationButton;

    @FXML
    public TextArea receiveInformation;

    @FXML
    private TextField chatUserId;

    @FXML
    private TextField chatUserStatus;

    public void setMessage(Msg msg) {
        String info = msg.getSenderId() + " " + msg.getSendTime() + "\n" + msg.getMsgContent() + "\n";
        System.out.println(info);
        receiveInformation.appendText(info);
    }

    public void showChatStage(String senderId, String receiverId, String status, Parent chat) {
        // 如果聊天框已打开，将其置顶显示而非新建
        Stage exsitChatStage = ManageChatView.openChatStages.get(receiverId);
        if (exsitChatStage != null) {
            exsitChatStage.toFront();
        } else {
            // 初始化聊天框
            setChatStage(receiverId, status);
            Stage chatStage = new Stage();
            chatStage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResource("images/qq.gif")).toExternalForm()));
            chatStage.setResizable(false);
            chatStage.setTitle(senderId + "正在与" + receiverId + "聊天");
            chatStage.setScene(new Scene(chat));
            chatStage.show();

            // 窗口关闭监听器,chatView关闭后移出Map
            chatStage.setOnCloseRequest(event -> ManageChatView.removeChatStage(receiverId));

            // 存储打开的聊天窗口
            ManageChatView.addChatStage(receiverId, chatStage);
            ManageChatView.addChatController(receiverId, this);

            // 鼠标点击事件
            sendInformationButton.setOnMouseClicked(event -> {
                if (sendInformation.getText().isEmpty()) {
                    sendInformation.setStyle("-fx-border-color: red");
                } else {
                    sendInformation.setStyle("-fx-border-color: gray");
                    onSendButtonClick(senderId, receiverId);
                }
            });

            // 回车模拟点击事件
//            sendInformation.setOnKeyPressed(event -> {
//                KeyCode keyCode = event.getCode();
//                if (keyCode == KeyCode.ENTER) {
//                    if ((sendInformation.getText()).isEmpty()) {
//                        sendInformation.setStyle("-fx-border-color: red");
//                    } else {
//                        sendInformation.setStyle("-fx-border-color: gray");
//                        onSendButtonClick(senderId, receiverId);
//                    }
//                }
//            });
        }
    }

    // 设置聊天框信息
    public void setChatStage(String accountId, String status) {
        chatUserId.setText(accountId);
        chatUserStatus.setText(status);
    }

    // 当点击发送按钮时，将消息发送到服务器
    public void onSendButtonClick(String senderId, String receiverId) {
        Msg msg = new Msg();
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        msg.setMsgContent(sendInformation.getText());
        msg.setSendTime();
        msg.setMsgType(Msg.TEXT_MSG);
        // 发送消息到服务器
        try {
            // 取得对应userId的socket来建立输出流
            ObjectOutputStream oos = new ObjectOutputStream(ManageServerThread.getServer(senderId).getClientSocket().getOutputStream());
            oos.writeObject(msg);
            oos.flush();
            sendInformation.clear();
            String info = "->> 你 " + msg.getSendTime() + "\n" + msg.getMsgContent() + "\n";
            System.out.println(info);
            receiveInformation.appendText(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
