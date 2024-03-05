/**
 * @author Jacky Feng
 * @Description 客户端和服务器保持通讯的线程
 */
package top.fexample.fq.model;

import javafx.stage.Stage;
import top.fexample.fq.controller.ChatController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ConServerThread extends Thread {
    private Socket clientSocket;

    public ConServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        while (true) {
            // 读取从服务器发来的消息
            try {
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                Msg msg = (Msg) ois.readObject();
                // 获得聊天窗口，此处senderId为你聊天的对象id，即ChatController的receiverId
                ChatController chatController = ManageChatView.getChatController(msg.getSenderId());
                chatController.setMessage(msg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
