/**
 * @author Jacky Feng
 * @Description 客户端和服务器保持通讯的线程
 */
package top.fexample.fq.model;

import top.fexample.fq.controller.ChatController;
import top.fexample.fq.controller.FriendListController;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ConServerThread extends Thread {
    private Socket clientSocket;

    public ConServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (true) {
            // 读取从服务器发来的消息
            try {
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                Msg msg = (Msg) ois.readObject();

                // 如果消息类型是请求用户状态，返回类型是rec
                // 用户状态请求包content格式为:"user1 user2 ...",代表在线id列表
                if (msg.getMsgType().equals(Msg.REC_USER_ONLINE)) {
                    System.out.println("收到服务器发来的消息：" + msg.getMsgContent());
                    String onlineUsers = msg.getMsgContent();
                    String[] onlineUsersList = onlineUsers.split(" ");

                    // 请求用户的id(服务器视角)
                    String getter = msg.getReceiverId();
                    System.out.println("请求用户的id：" + getter);

                    // 修改相应的好友列表
                    FriendListController friendListController = ManageFriendList.getFriendListController(getter);
                    if (friendListController != null) {
                        friendListController.setUserStatusMsg(onlineUsersList);
                        friendListController.updateFriendList();
                    }

                } else {
                    // 获得聊天窗口，此处senderId为你聊天的对象id，即ChatController的receiverId
                    ChatController chatController = ManageChatView.getChatController(msg.getSenderId());
                    chatController.setMessage(msg);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
