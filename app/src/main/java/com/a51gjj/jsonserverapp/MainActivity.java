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

public class MainActivity extends AppCompatActivity implements EventMachine.OnStateHandler,TimeMachine.TimeHander{





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
        timeMachine = TimeMachine.getInstance(this);
    }
    private View user_first_view= null;
    private View user_event_view = null;
    private View user_second_event_view = null;
    private View user_choice_result_view = null;
    private View user_shequ_scene = null;
    private View game_finish_view = null;
    private View user_finished_view = null;
    private TextView name,age,shouru,caichan,des,job,eventDes,secondDes,choice_result_des,rp,jszt,fdl,bsd,game_finish_des;
    private Button firstBtn,secondBtn,goto_shequ,goto_sleep,shequ_goto_sleep;
    private LinearLayout choiceList;


    private void initGameViews(){
        user_first_view = layoutInflater.inflate(R.layout.user_first_view,null);
        user_event_view = layoutInflater.inflate(R.layout.user_event_view,null);
        user_second_event_view = layoutInflater.inflate(R.layout.user_second_event_view,null);
        user_choice_result_view = layoutInflater.inflate(R.layout.user_choice_result_view,null);
        user_shequ_scene = layoutInflater.inflate(R.layout.shequ_scene,null);
        game_finish_view = layoutInflater.inflate(R.layout.game_finish_view,null);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        shouru = findViewById(R.id.shouru);
        caichan = findViewById(R.id.caichan);
        rp = findViewById(R.id.rp);
        jszt = findViewById(R.id.jszt);
        fdl = findViewById(R.id.fdl);
        bsd = findViewById(R.id.bsd);
        des = user_first_view.findViewById(R.id.description);
        job = findViewById(R.id.job);

        game_finish_des = game_finish_view.findViewById(R.id.game_finish_des);
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
        shequ_goto_sleep = user_shequ_scene.findViewById(R.id.shequ_goto_sleep);
        shaxianXiaochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayer.getCaichan()<10){
                    Toast.makeText(MainActivity.this,"穷比滚",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this,"吃了一份鸭腿饭",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(10);
                currentPlayer.addBsd(1);
                currentPlayer.addJszt(1);
                currentPlayer.addFdl(1);
                currentPlayer.loseRp(1);
                initGenView();
            }
        });
        wangba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayer.getCaichan()<10){
                    Toast.makeText(MainActivity.this,"穷比滚",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this,"超神啦超神啦",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(10);
                currentPlayer.loseBsd(1);
                currentPlayer.loseRp(3);
                currentPlayer.addJszt(2);
                initGenView();
            }
        });
        huoguodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayer.getCaichan()<80){
                    Toast.makeText(MainActivity.this,"穷比滚",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this,"怎么感觉菊花有点疼",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(80);
                currentPlayer.addJszt(3);
                currentPlayer.addBsd(3);
                currentPlayer.addRp(2);
                initGenView();
            }
        });
        tianshangrenjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayer.getCaichan()<1000){
                    Toast.makeText(MainActivity.this,"穷比滚",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this,"三分钟花了10000块",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(1000);
                currentPlayer.addJszt(5);
                currentPlayer.loseBsd(1);
                currentPlayer.addRp(6);
                initGenView();
            }
        });
        anmodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayer.getCaichan()<100){
                    Toast.makeText(MainActivity.this,"穷比滚",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this,"小丽又长胖了",Toast.LENGTH_SHORT).show();
                currentPlayer.loseMoney(100);
                currentPlayer.addJszt(2);
                currentPlayer.loseBsd(1);
                currentPlayer.loseRp(3);
                initGenView();
            }
        });
        fangchanzhongjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayer.getCaichan()<10000) {
                    Toast.makeText(MainActivity.this, "被房产中介打了一顿给赶了出来:这里不是你们这些穷逼能来的地方", Toast.LENGTH_SHORT).show();
                    currentPlayer.loseMoney(100);
                    currentPlayer.loseJszt(10);
                    currentPlayer.loseBsd(1);
                    currentPlayer.loseRp(1);
                    initGenView();
                }
            }
        });

        shequ_goto_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMachine.onFinished(EventState.day_finish_event);
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
                        currentPlayer.setRp(jo.getInt("rp"));
                        currentPlayer.setBsd(jo.getInt("bsd"));
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
            case day_finish_event:
                if(timeMachine.timeGo()) {

                    desEvent();
                };
                break;

        }
    }

    public void initGenView(){
        name.setText("名字： "+currentPlayer.getName());
        age.setText("年龄： "+currentPlayer.getAge()+"岁");
        shouru.setText("月收入： "+currentPlayer.getShouru());
        caichan.setText("财产： "+currentPlayer.getCaichan());
        job.setText("职业： "+"美发师");
        bsd.setText("bsd: " +currentPlayer.getBsd());
        fdl.setText("fdl: "+currentPlayer.getFdl());
        rp.setText("rp: "+currentPlayer.getRp());
        jszt.setText("jszt: "+currentPlayer.getJszt());
    }

    private boolean initGenEvent(int time){
        if(currentPlayer.getCaichan()>=3000){
            gameFinishEvent("厉害厉害，只用了"+time+"天");
            return false;

        }
        if(currentPlayer.getBsd()<-3){
            gameFinishEvent("好饿啊好饿啊，饿死啦");
            return false;


        }
        if(currentPlayer.getBsd()<-3){
            gameFinishEvent("好撑啊好撑啊，撑死了");
            return false;


        }
        if(currentPlayer.getJszt()<-10){
            //Toast.makeText(this,"生活好空虚呀，自杀啦",Toast.LENGTH_LONG);
            gameFinishEvent("生活好空虚呀，自杀啦");
            return false;


        }
        if(time>30&&currentPlayer.getCaichan()<800000){
            gameFinishEvent("首付还没凑齐，你这个loser！！");
            return false;


        }
        return true;

    }
    //---------------------------------------文字事件场景
    private void desEvent(){
        game_view.removeAllViews();
        game_view.addView(user_event_view);
        String tag = "job_random_event" + (currentPlayer.getRp()>80?"_good":"_normal");
        //eventDes.setText("hello");
        AppNetWork.getUserData(tag,new DataReceiveResponseHandler(){
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
        String tag = "_choice_result" + (currentPlayer.getRp()>80?"_good":"_normal");
        AppNetWork.getUserData(job+tag+"/"+id,new DataReceiveResponseHandler(){
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

    private void gameFinishEvent(String tip){
        setCurrentGameView(game_finish_view);
        game_finish_des.setText(tip);
    }
    class ChoiceInfo{
        String title = "";
        String id = "";
    }

    @Override
    public boolean onTimeGo(int time) {
        currentPlayer.loseBsd(1);
        return initGenEvent(time);


    }
}
