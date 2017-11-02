package com.a51gjj.jsonserverapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a51gjj.jsonserverapp.network.AppNetWork;
import com.a51gjj.jsonserverapp.network.DataReceiveResponseHandler;
import com.a51gjj.jsonserverapp.player.Player;

import com.a51gjj.jsonserverapp.stateMachine.EventMachine;
import com.a51gjj.jsonserverapp.stateMachine.EventState;


import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements EventMachine.OnStateHandler{





    public static Player currentPlayer;

    @BindView(R.id.game_view)
    RelativeLayout game_view;


    private EventState currentEvent = EventState.begin;
    private LayoutInflater layoutInflater = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       init();
       initGameViews();
       //eventBus(currentPlayer);
        beginEvent();

    }

    private View user_first_view= null;
    private View user_event_view = null;
    private View user_second_event_view = null;
    private void init(){
        ButterKnife.bind(this);
        currentPlayer = new Player();
        layoutInflater = getLayoutInflater();
        eventMachine = EventMachine.getMachine();
        eventMachine.setHandler(this);
    }

    private TextView name,age,shouru,caichan,des,job,eventDes,secondDes;
    private Button firstBtn,secondBtn,thirdBtn;
    private void initGameViews(){
        user_first_view = layoutInflater.inflate(R.layout.user_first_view,null);
        user_event_view = layoutInflater.inflate(R.layout.user_event_view,null);
        user_second_event_view = layoutInflater.inflate(R.layout.user_second_event_view,null);
        name = user_first_view.findViewById(R.id.name);
        age = user_first_view.findViewById(R.id.age);
        shouru = user_first_view.findViewById(R.id.shouru);
        caichan = user_first_view.findViewById(R.id.caichan);
        des = user_first_view.findViewById(R.id.description);
        job = user_first_view.findViewById(R.id.job);

        firstBtn = user_first_view.findViewById(R.id.ok);
        secondBtn = user_event_view.findViewById(R.id.second_ok);
        thirdBtn = user_second_event_view.findViewById(R.id.third_ok);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMachine.onFinished(EventState.first_event);
            }
        });
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMachine.onFinished(EventState.second_event);
            }
        });
        thirdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMachine.onFinished(EventState.first_event);
            }
        });

        eventDes = user_event_view.findViewById(R.id.event_des);
        secondDes= user_second_event_view.findViewById(R.id.second_event_des);
    }
    private void eventBus(EventState state){



    }

    private String currentPlayerTag = "player_1";

    private void beginEvent(){
        game_view.removeAllViews();
        game_view.addView(user_first_view);
        AppNetWork.getUserData(currentPlayerTag,new DataReceiveResponseHandler(){
            @Override
            public void onResult(String error, Object content) {
                super.onResult(error, content);
                if(error!=null){
                    Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show();
                }
                if(content!=null){
                    JSONObject jo = (JSONObject)content;
                    try {
                        currentPlayer.setAge(jo.getInt("age"));
                        currentPlayer.setName(jo.getString("name"));
                        currentPlayer.setShouru(jo.getInt("shouru"));
                        currentPlayer.setCaichan(jo.getInt("caichan"));
                        currentPlayer.setJob(jo.getString("job"));
                        name.setText("名字： "+currentPlayer.getName());
                        age.setText("年龄： "+currentPlayer.getAge()+"岁");
                        shouru.setText("月收入： "+currentPlayer.getShouru());
                        caichan.setText("财产： "+currentPlayer.getCaichan());
                        job.setText("职业： "+"美发师");

                        initFirstDes(currentPlayer.getJob());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initFirstDes(String job){
        String tag = job+"_job_des";
        AppNetWork.getUserData(tag,new DataReceiveResponseHandler(){
            @Override
            public void onResult(String error, Object content) {
                super.onResult(error, content);
                if(error!=null){
                    Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show();
                }
                if(content!=null){
                    JSONObject jo = (JSONObject)content;
                    try {
                      des.setText(jo.getString("lifashi"));
                     //eventMachine.onFinished(currentEvent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private EventMachine eventMachine ;

    @Override
    public void onHandlerFinished(EventState state) {

        switch (state){
            case first_event:
                currentEvent = EventState.second_event;
                 //eventBus(EventState.second_event);
                secondEvent();
                break;
            case second_event:
                currentEvent = EventState.third_event;
                thirdEvent();
                break;

        }
    }

    private void secondEvent(){
        game_view.removeAllViews();
        game_view.addView(user_event_view);
        //eventDes.setText("hello");
        AppNetWork.getUserData("job_random_event",new DataReceiveResponseHandler(){
            @Override
            public void onResult(String error, Object content) {
                super.onResult(error, content);
                if(content!=null){
                    JSONObject jo = (JSONObject)content;
                    try {
                        JSONArray jsonArray = jo.getJSONArray("lifashi_event");
                        eventDes.setText(jsonArray.getJSONObject(0).getString("event_des"));
                        currentPlayer.setCaichan(currentPlayer.getCaichan()+jsonArray.getJSONObject(0).getInt("shouru"));
                        Toast.makeText(MainActivity.this,"收入增加"+jsonArray.getJSONObject(0).getInt("shouru"),Toast.LENGTH_SHORT).show();
                        //eventMachine.onFinished(currentEvent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void thirdEvent(){
        game_view.removeAllViews();
        game_view.addView(user_second_event_view);

        AppNetWork.getUserData("lifashi_choice_result_good",new DataReceiveResponseHandler(){
            @Override
            public void onResult(String error, Object content) {
                super.onResult(error, content);
                if(content!=null){
                    JSONObject jo = (JSONObject)content;
                    try {
                        JSONArray jsonArray = jo.getJSONArray("lifashi_event");
                        secondDes.setText(jsonArray.getJSONObject(1).getString("event_des"));
                        currentPlayer.setCaichan(currentPlayer.getCaichan()+jsonArray.getJSONObject(1).getInt("shouru"));
                        Toast.makeText(MainActivity.this,"收入增加"+jsonArray.getJSONObject(1).getInt("shouru"),Toast.LENGTH_SHORT).show();
                        //eventMachine.onFinished(currentEvent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
