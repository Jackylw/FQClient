package top.fexample.fq.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionServer {

    private Socket clientSocket;

    // 登录验证
    public Msg sendLoginInfoToServer(User user) {
        Msg msg = new Msg();
        try {
//            Socket clientSocket;
            try {
                clientSocket = new Socket("127.0.0.1", 9000);
            } catch (Exception e){
                System.out.println("连接服务器失败");
                msg.setMsgType(MsgType.CONNECTION_SERVER_ERROR);
                return msg;
            }

            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(user);
            System.out.println("发送账号为" + user.getAccountId());
            System.out.println("发送密码为" + user.getPassword());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            msg = (Msg) ois.readObject();
            System.out.println("接受信息为" + msg.getMsgType());

            // 如果msgType为login_success，创建一个ConServerThread
            if(msg.getMsgType().equals(Msg.LOGIN_SUCCESS)){
                ConServerThread conServerThread = new ConServerThread(clientSocket);
                conServerThread.start();
                ManageServerThread.addServer(user.getAccountId(), conServerThread);
            }
            return msg;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
