/**
 * @description: 此类必须严格保持与双端一致
 */
package top.fexample.fq.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Msg implements Serializable {
    private String senderId;
    private String receiverId;
    private String msgContent;
    private String sendTime;
    public static final String LOGIN = "login";
    public static final String LOGIN_SUCCESS = "login_success";
    public static final String LOGIN_ERROR = "login_error";
    public static final String REGISTER = "register";
    public static final String REGISTER_SUCCESS = "register_success";
    public static final String REGISTER_ERROR = "register_error";
    public static final String CONNECTION_SERVER_ERROR = "connection_server_error";
    public static final String CONNECTION_SERVER_SUCCESS = "connection_server_success";
    public static final String GET_USER_LIST = "get_user_list";
    public static final String GET_USER_ONLINE = "get_user_online";
    public static final String REC_USER_ONLINE = "rec_user_online";
    public static final String TEXT_MSG = "text_msg";
    public static final String FIND_PASSWORD = "find_password";
    public static final String FIND_PASSWORD_SUCCESS = "find_password_success";
    public static final String FIND_PASSWORD_ERROR = "find_password_error";

    private String msgType;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType.getMsgType();
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getSendTime() {
        return sendTime;
    }


    public void setSendTime() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        this.sendTime = now.format(formatter);
    }
}

enum MsgType {
    LOGIN("login"),
    LOGIN_SUCCESS("login_success"),
    LOGIN_ERROR("login_error"),
    REGISTER("register"),
    REGISTER_SUCCESS("register_success"),
    REGISTER_ERROR("register_error"),
    CONNECTION_SERVER_ERROR("connection_server_error"),
    CONNECTION_SERVER_SUCCESS("connection_server_success"),
    GET_USER_LIST("get_user_list"),
    GET_USER_ONLINE("get_user_online"),
    REC_USER_ONLINE("rec_user_online"),
    TEXT_MSG("text_msg"),
    FIND_PASSWORD("find_password"),
    FIND_PASSWORD_SUCCESS("find_password_success"),
    FIND_PASSWORD_ERROR("find_password_error");

    private final String msgType;

    MsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }
}