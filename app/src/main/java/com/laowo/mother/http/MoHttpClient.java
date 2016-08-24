package com.laowo.mother.http;

import com.alibaba.fastjson.JSONObject;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * 网络交互类
 * Created by xsh on 2016/8/15.
 */
public class MoHttpClient {

    private static final String STATE = "state";
    private static final String REMARK = "remark";

    private static final int STATE_402 = 402;//账号在其他设备登录


    /**
     * 获取固定请求参数<br>
     * 已包含参数：user_id、token、family_id、area
     *
     * @return
     */
    public static RequestParams getRequestParams() {
        RequestParams params = new RequestParams();

        return params;
    }

    /**
     * 获取 JSON 请求参数
     *
     * @return
     */
    private static JSONObject getJsonRequestParams() {
        JSONObject jsonObj = new JSONObject();

        return jsonObj;
    }

    /**
     * 获取请求响应Handler
     *
     * @param callback
     * @return
     */
    private static BaseHttpRequestCallback getResponseHandler(final HttpClientCallback callback) {
        BaseHttpRequestCallback response = new BaseHttpRequestCallback<JSONObject>() {

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                //无网络或者后台出错
                callback.onFailure(errorCode, msg);
            }

            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                int state = jsonObject.getIntValue(STATE);
                if (state % 100 == 0) {
                    //请求成功
                    callback.onSuccess(jsonObject);
                } else {
                    //请求失败,参数错误或者后台错误等原因
                    if (state == STATE_402) {

                    } else {
                        callback.onFailure(state, jsonObject.getString(REMARK));
                    }
                }
            }

            @Override
            public void onResponse(Response httpResponse, String response, Headers headers) {
                super.onResponse(httpResponse, response, headers);
            }
        };
        return response;
    }

}
