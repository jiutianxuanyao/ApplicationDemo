package com.byron.eventbus;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by company-ios on 2017/6/9.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.builder().addIndex(new EventBusIndex()).installDefaultEventBus();
    }
}
