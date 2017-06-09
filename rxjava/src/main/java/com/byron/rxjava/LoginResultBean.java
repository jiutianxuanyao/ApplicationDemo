package com.byron.rxjava;

/**
 * Created by company-ios on 2017/6/6.
 */

public class LoginResultBean {

    /**
     * status : 2
     * message : 手机号或密码错误
     */

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginResultBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
