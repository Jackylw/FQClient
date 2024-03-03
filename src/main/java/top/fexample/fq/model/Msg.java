/**
 * @description: 此类必须严格保持与双端一致
 */
package top.fexample.fq.model;

import java.io.Serializable;

public class Msg implements Serializable {
    public static final String LOGIN_SUCCESS = "login_success";
    public static final String LOGIN_ERROR = "login_error";
    public static final String REGISTER_SUCCESS = "register_success";
    public static final String REGISTER_ERROR = "register_error";
    public static final String CONNECTION_SERVER_ERROR = "connection_server_error";
    public static final String CONNECTION_SERVER_SUCCESS = "connection_server_success";

    private String msgType;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType.getMsgType();
    }
}

enum MsgType {
    LOGIN_SUCCESS("login_success"),
    LOGIN_ERROR("login_error"),
    REGISTER_SUCCESS("register_success"),
    REGISTER_ERROR("register_error"),
    CONNECTION_SERVER_ERROR("connection_server_error"),
    CONNECTION_SERVER_SUCCESS("connection_server_success");

    private final String msgType;

    MsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }
}