package com.a51gjj.jsonserverapp.stateMachine;

/**
 * Created by Administrator on 2017/11/2 0002.
 */

public class EventMachine {

    private static  EventMachine machine = null;
    private  OnStateHandler handler;
    public static EventMachine getMachine(){
        if(machine ==null){
            machine = new EventMachine();
        }
        return machine;
    }
    public  void setHandler(OnStateHandler handler ){
        this.handler = handler;
    }
    public void onFinished(EventState state){
        handler.onHandlerFinished(state);
    };
    public  interface OnStateHandler{
        void onHandlerFinished(EventState state);
    }
}
