package com.byron.mvp.login;

/**
 * Created by company-ios on 2017/6/8.
 */

public interface ILoginView {
    //定义和View相关的接口
    void setLoginButtonEnabled(boolean enabled);
    void showLoadingView(boolean loading);

}
