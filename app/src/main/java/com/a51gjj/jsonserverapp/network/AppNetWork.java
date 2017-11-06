package com.a51gjj.jsonserverapp.network;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.x;


/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class AppNetWork {
    interface RequestHandler{
        void onResult(String error,Object content);
    }

    public static void appGet(String name, final RequestHandler handler){

        RequestParams params = new RequestParams(originUrl+name);
//        params.addQueryStringParameter("username","abc");
//        params.addQueryStringParameter("password","123");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject content = null;
                result = result.replace("\n","");
                //解析result
                try {
                    content = new JSONObject(result);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(content!=null) {
                    handler.onResult(null, content);
                }else{
                    handler.onResult("解析失败",null);
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                handler.onResult("error:"+ex.getMessage(),null);
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
  final static String originUrl = "http://67.209.191.20:3000";//192.168.31.130//92.168.100.142//http://67.209.191.20/
    public static void getUserData(String player1,final DataReceiveResponseHandler handler ){
        appGet("/"+player1, new RequestHandler() {
            @Override
            public void onResult(String error, Object content) {
                    handler.onResult(error,content);
            }
        });
    }
}
