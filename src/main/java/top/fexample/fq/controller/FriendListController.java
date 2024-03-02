package top.fexample.fq.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import top.fexample.fq.Application;
import top.fexample.fq.model.User;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FriendListController {

    @FXML
    private VBox friendListContainer;

    public void initialize() {
        List<User> friends = getUserList();
        for (User user : friends) {
            FXMLLoader userComponentLoader = new FXMLLoader(Application.class.getResource("views/UserComponent.fxml"));
            try {
                // 加载UserComponent.fxml并获取其根节点
                Node userNode = userComponentLoader.load();

                UserController userController = userComponentLoader.getController();
                userController.setUserController(user.getAccountId(), "离线");
                userNode.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {

                        // 调用ChatController的showChatStage方法
                        FXMLLoader loader = new FXMLLoader(Application.class.getResource("views/chatView.fxml"));
                        try {
                            Parent chatView = loader.load();
                            ChatController chatController = loader.getController();
                            chatController.showChatStage(chatView, user.getAccountId(), "离线");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                // 将用户组件添加到好友列表的Vbox
                friendListContainer.getChildren().add(userNode);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 创建并显示好友列表
    public void showFriendListStage() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("views/FriendListView.fxml"));
            Parent friendList = loader.load();
            Stage friendListStage = new Stage();
            friendListStage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResource("images/qq.gif")).toExternalForm()));
            friendListStage.setResizable(false);
            friendListStage.setTitle("好友列表");
            friendListStage.setScene(new Scene(friendList));
            friendListStage.show();

            // 关闭好友列表的监听事件
            friendListStage.setOnCloseRequest(event -> Platform.exit());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 获取好友列表
    private List<User> getUserList() {
        return Arrays.asList(
                new User("10001", "123"),
                new User("10002", "123"),
                new User("10003", "123"),
                new User("10004", "123"),
                new User("10005", "123"),
                new User("10006", "123"),
                new User("10007", "123"),
                new User("10008", "123"),
                new User("10009", "123"),
                new User("10010", "123"),
                new User("10011", "123"),
                new User("10012", "123"),
                new User("10013", "123"),
                new User("10014", "123"),
                new User("10015", "123")
        );
    }
}