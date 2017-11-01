package com.a51gjj.jsonserverapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a51gjj.jsonserverapp.network.AppNetWork;
import com.a51gjj.jsonserverapp.network.DataReceiveResponseHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppNetWork.getUserData(new DataReceiveResponseHandler(){
            @Override
            public void onResult(String error, Object content) {
                super.onResult(error, content);
            }
        });
    }
}
