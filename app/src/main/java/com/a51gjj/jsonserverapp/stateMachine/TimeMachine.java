package com.a51gjj.jsonserverapp.stateMachine;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class TimeMachine  {
    private static TimeMachine machine = null;
    public static TimeMachine getInstance(){
        if(machine==null){
            machine= new TimeMachine();
        }

        return machine;
    }


    private int time = 0;//基础事件为天

    public int getCurrentTime(){
        return time;
    }

    public void timeGo(){
        time++;
    }
}
