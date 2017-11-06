package com.a51gjj.jsonserverapp.stateMachine;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class TimeMachine  {
    private static TimeMachine machine = null;
    public TimeHander timeHander = null;
    public static TimeMachine getInstance(TimeHander hander){
        if(machine==null){
            machine= new TimeMachine();
            machine.timeHander = hander;
        }

        return machine;
    }


    private int time = 0;//基础事件为天

    public int getCurrentTime(){
        return time;
    }

    public boolean timeGo(){
//        time++;
       return timeHander.onTimeGo(++time);
    }

   public interface TimeHander{
        boolean onTimeGo(int time);
    }
}
