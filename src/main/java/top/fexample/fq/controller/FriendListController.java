package top.fexample.fq.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import top.fexample.fq.Application;
import top.fexample.fq.model.Msg;
import top.fexample.fq.model.User;

import java.util.*;

public class FriendListController {

    @FXML
    private VBox friendListContainer;

    public String[] userStatusMsg;

    // 存储每个好友节点，便于后续操作此节点
    public static final HashMap<String, Node> friendListNodes = new HashMap<>();

    // 创建并显示好友列表
    public void showFriendListStage(String userId) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("views/FriendListView.fxml"));
            Parent friendList = loader.load();

            // 这里friendListContainer才被初始化
            FriendListController friendListController = loader.getController();
            friendListController.initFriendList(userId);

            Stage friendListStage = new Stage();
            friendListStage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResource("images/qq.gif")).toExternalForm()));
            friendListStage.setResizable(false);
            friendListStage.setTitle(userId);
            friendListStage.setScene(new Scene(friendList));
            friendListStage.show();

            // 关闭好友列表的监听事件
            friendListStage.setOnCloseRequest(event -> Platform.exit());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 初始化好友列表
    public void initFriendList(String userId) {
        // 获取好友列表
        List<User> friends = getUserList(userId);

        for (User user : friends) {
            FXMLLoader userComponentLoader = new FXMLLoader(Application.class.getResource("views/UserComponent.fxml"));
            try {
                // 加载UserComponent.fxml并获取其根节点
                Node userNode = userComponentLoader.load();

                friendListNodes.put(user.getAccountId(), userNode);

                UserController userController = userComponentLoader.getController();
                userController.setUserController(user.getAccountId(), user.getStatus());
                userNode.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {

                        // 调用ChatController的showChatStage方法
                        FXMLLoader loader = new FXMLLoader(Application.class.getResource("views/chatView.fxml"));
                        try {
                            Parent chatView = loader.load();
                            ChatController chatController = loader.getController();
                            chatController.showChatStage(userId, user.getAccountId(), user.getStatus(), chatView);
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

    // 设置
    public void setUserStatusMsg(String[] userStatusMsg) {
        this.userStatusMsg = userStatusMsg;
    }

    //todo 根据userId获取该用户的好友列表,userId为根据此获取该id的好友列表
    private List<User> getUserList(String userId) {
        System.out.println("获取好友列表");
        return new ArrayList<>(Arrays.asList(new User[]{
                new User("10001", "123", "offline"),
                new User("10002", "456", "offline"),
                new User("10003", "789", "offline")
        }));
    }

    // 更新好友列表,但是此处并未更新User类的status，User类统一默认为offline
    public void updateFriendList() {
        for (String onlineUserId : userStatusMsg) {
            if (friendListNodes.containsKey(onlineUserId)) {
                Node node = friendListNodes.get(onlineUserId);
                Label statusLabel = (Label) node.lookup("#statusLabel");
                ImageView imageView = (ImageView) node.lookup("#imageHead");
                Platform.runLater(() -> {
                    statusLabel.setText("online");
                    imageView.setStyle("");
                });
            }
        }
    }
}