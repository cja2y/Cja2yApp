package com.a51gjj.jsonserverapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a51gjj.jsonserverapp.network.AppNetWork;
import com.a51gjj.jsonserverapp.network.DataReceiveResponseHandler;
import com.a51gjj.jsonserverapp.player.Player;

import com.a51gjj.jsonserverapp.stateMachine.EventMachine;
import com.a51gjj.jsonserverapp.stateMachine.EventState;
import com.a51gjj.jsonserverapp.stateMachine.TimeMachine;


import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements EventMachine.OnStateHandler{





    public static Player currentPlayer;
    public static TimeMachine timeMachine;
    private EventMachine eventMachine ;

    @BindView(R.id.game_view)
    RelativeLayout game_view;


    private EventState currentEvent = EventState.begin;
    private LayoutInflater layoutInflater = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//         requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
//         getWindow().setFlags(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);  //设置全屏
        setContentView(R.layout.activity_main);

       init();
       initGameViews();
       //eventBus(currentPlayer);
        beginEvent();

    }


    private void init(){
        ButterKnife.bind(this);
        currentPlayer = new Player();
        layoutInflater = getLayoutInflater();
        eventMachine = EventMachine.getMachine();
        eventMachine.setHandler(this);
        timeMachine = TimeMachine.getInstance();
    }
    private View user_first_view= null;
    private View user_event_view = null;
    private View user_second_event_view = null;
    private View user_choice_result_view = null;
    private View user_shequ_scene = null;
    private TextView name,age,shouru,caichan,des,job,eventDes,secondDes,choice_result_des;
    private Button firstBtn,secondBtn,goto_shequ,goto_sleep;
    private LinearLayout choiceList;


    private void initGameViews(){
        user_first_view = layoutInflater.inflate(R.layout.user_first_view,null);
        user_event_view = layoutInflater.inflate(R.layout.user_event_view,null);
        user_second_event_view = layoutInflater.inflate(R.layout.user_second_event_view,null);
        user_choice_result_view = layoutInflater.inflate(R.layout.user_choice_result_view,null);
        user_shequ_scene = layoutInflater.inflate(R.layout.shequ_scene,null);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        shouru = findViewById(R.id.shouru);
        caichan = findViewById(R.id.caichan);
        des = user_first_view.findViewById(R.id.description);
        job = findViewById(R.id.job);

        firstBtn = user_first_view.findViewById(R.id.ok);
        secondBtn = user_event_view.findViewById(R.id.second_ok);
        choiceList = user_second_event_view.findViewById(R.id.choice_list);
        goto_shequ = user_choice_result_view.findViewById(R.id.goto_shequ);
        goto_sleep = user_choice_result_view.findViewById(R.id.goto_sleep);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMachine.onFinished(EventState.des_event);
            }
        });
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMachine.onFinished(EventState.choice_event);
            }
        });
        goto_shequ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMachine.onFinished(EventState.shequ_event);
            }
        });
        goto_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMachine.onFinished(EventState.day_finish_event);
            }
        });
//        thirdBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                eventMachine.onFinished(EventState.des_event);
//            }
//        });

        eventDes = user_event_view.findViewById(R.id.event_des);
        secondDes= user_second_event_view.findViewById(R.id.second_event_des);
        choice_result_des = user_choice_result_view.findViewById(R.id.choice_result_des);

        shaxianXiaochi = user_shequ_scene.findViewById(R.id.shaxian_xiaochi);
        wangba = user_shequ_scene.findViewById(R.id.wangba);
        huoguodian = user_shequ_scene.findViewById(R.id.huoguodian);
        tianshangrenjian = user_shequ_scene.findViewById(R.id.tianshangrenjian);
        anmodian = user_shequ_scene.findViewById(R.id.anmoxiaodian);
        fangchanzhongjie = user_shequ_scene.findViewById(R.id.fangchanzhongjie);

        shaxianXiaochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"吃了一份鸭腿饭",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(100);

                initGenView();
            }
        });
        wangba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"超神啦超神啦",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(100);
                currentPlayer.loseBsd(1);
                currentPlayer.loseRp(1);
                currentPlayer.addJszt(1);
                initGenView();
            }
        });
        huoguodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"怎么感觉菊花有点疼",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(100);
                currentPlayer.loseMoney(1000);
                currentPlayer.addJszt(2);
                initGenView();
            }
        });
        tianshangrenjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"三分钟花了10000块",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(100);
                currentPlayer.loseJszt(3);
                initGenView();
            }
        });
        anmodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"小丽又长胖了",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(100);
                currentPlayer.loseJszt(2);
                initGenView();
            }
        });
        fangchanzhongjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"被房产中介打了一顿给赶了出来:这里不是你们这些穷逼能来的地方",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(100);
                currentPlayer.loseJszt(10);
                initGenView();
            }
        });






    }
    private Button shaxianXiaochi,wangba,huoguodian,tianshangrenjian,anmodian,fangchanzhongjie;

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

                        initGenView();
                       // initFirstDes(currentPlayer.getJob());
                        eventMachine.onFinished(EventState.begin);
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



    @Override
    public void onHandlerFinished(EventState state) {

        initGenView();
        switch (state){
            case des_event:
                //currentEvent = EventState.second_event;
                 //eventBus(EventState.second_event);
                desEvent();
                break;
            case choice_event:
                //currentEvent = EventState.third_event;
                choiceEvent();
                break;

            case begin:
                initFirstDes(currentPlayer.getJob());
                break;
            case shequ_event:
                shequEvent();
                break;

        }
    }

    public void initGenView(){
        name.setText("名字： "+currentPlayer.getName());
        age.setText("年龄： "+currentPlayer.getAge()+"岁");
        shouru.setText("月收入： "+currentPlayer.getShouru());
        caichan.setText("财产： "+currentPlayer.getCaichan());
        job.setText("职业： "+"美发师");
    }
    //---------------------------------------文字事件场景
    private void desEvent(){
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
                       // initGenView();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    //---------------------------------------------选择事件场景
    private void choiceEvent(){
        game_view.removeAllViews();
        game_view.addView(user_second_event_view);

        AppNetWork.getUserData("choice_event",new DataReceiveResponseHandler(){
            @Override
            public void onResult(String error, Object content) {
                super.onResult(error, content);
                if(content!=null){
                    JSONObject jo = (JSONObject)content;
                    try {
                        JSONArray jsonArray = jo.getJSONArray("lifashi_event");
                        JSONObject result = jsonArray.getJSONObject(0);
                        secondDes.setText(result.getString("title"));
                        JSONArray temp = result.getJSONArray("choice");
                        //currentPlayer.setCaichan(currentPlayer.getCaichan()+jsonArray.getJSONObject(1).getInt("shouru"));
                        //Toast.makeText(MainActivity.this,"收入增加"+jsonArray.getJSONObject(1).getInt("shouru"),Toast.LENGTH_SHORT).show();
                        //eventMachine.onFinished(currentEvent);
                        choiceList.removeAllViews();
                        for (int i=0;i<temp.length();i++){
                            JSONObject choice = temp.getJSONObject(i);
                            Button btn = new Button(MainActivity.this);
                            btn.setText(choice.getString("title"));
                            btn.setTag(choice.getString("id"));
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    choiceEventResult((String) v.getTag(),currentPlayer.getJob());
                                }
                            });
                            choiceList.addView(btn);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void choiceEventResult(String id,String job){
        AppNetWork.getUserData(job+"_choice_result_good"+"/"+id,new DataReceiveResponseHandler(){
            @Override
            public void onResult(String error, Object content) {
                super.onResult(error, content);
                game_view.removeAllViews();
                game_view.addView(user_choice_result_view);
                if(content!=null){
                    JSONObject jo = (JSONObject)content;
                    try {
//                        JSONArray jsonArray = jo.getJSONArray("lifashi_event");
//                        eventDes.setText(jsonArray.getJSONObject(0).getString("event_des"));
//                        currentPlayer.setCaichan(currentPlayer.getCaichan()+jsonArray.getJSONObject(0).getInt("shouru"));
                        choice_result_des.setText(jo.getString("des"));
                        currentPlayer.setCaichan(currentPlayer.getCaichan()+jo.getInt("shouru"));
                        Toast.makeText(MainActivity.this,"收入增加"+jo.getInt("shouru"),Toast.LENGTH_SHORT).show();
                        initGenView();
                       // eventMachine.onFinished(EventState.des_event);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setCurrentGameView(View v){
        game_view.removeAllViews();
        game_view.addView(v);
    }
    private void shequEvent(){
        setCurrentGameView(user_shequ_scene);


    }
    class ChoiceInfo{
        String title = "";
        String id = "";
    }
}
