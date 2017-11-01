package com.a51gjj.jsonserverapp.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能
    }
}
