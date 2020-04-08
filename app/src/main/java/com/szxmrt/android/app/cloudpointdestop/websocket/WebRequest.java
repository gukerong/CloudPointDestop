package com.szxmrt.android.app.cloudpointdestop.websocket;


import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class WebRequest {
    private String mToken;
    private String mSign;
    private String mEncmode;
    private int mIsPlain;
    private String mId;
    private String mName;
    private String mParam;
    private boolean mOnce;      // 是否只回调一次
    private boolean mRegonly;   // 只注册回调，不发送请求

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        mSign = sign;
    }

    public String getEncmode() {
        return mEncmode;
    }

    public void setEncmode(String encmode) {
        mEncmode = encmode;
    }

    public int getIsPlain() {
        return mIsPlain;
    }

    @JSONField(name = "is_plain")
    public void setIsPlain(int isPlain) {
        mIsPlain = isPlain;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getParam() {
        return mParam;
    }

    public void setParam(String param) {
        mParam = param;
    }

    public boolean getOnce() {
        return mOnce;
    }

    public void setOnce(boolean once) {
        mOnce = once;
    }

    public boolean getRegonly() {
        return mRegonly;
    }

    public void setRegonly(boolean regonly) {
        mRegonly = regonly;
    }

    @NonNull
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
