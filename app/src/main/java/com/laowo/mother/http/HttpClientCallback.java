package com.laowo.mother.http;

import com.alibaba.fastjson.JSONObject;

public interface HttpClientCallback {

    void onSuccess(JSONObject resultJSON);

    void onFailure(int code,String message);

}
