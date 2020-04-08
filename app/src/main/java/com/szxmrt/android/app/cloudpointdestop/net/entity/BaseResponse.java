package com.szxmrt.android.app.cloudpointdestop.net.entity;


import android.support.annotation.NonNull;

public abstract class BaseResponse<T>{

    protected int mRet;

    protected T mData;

    protected String mMsg;

    protected String mName;

    public int getRet() {
        return mRet;
    }

    public void setRet(int ret) {
        mRet = ret;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "BaseResponse{" +
            "mRet=" + mRet +
            ", mData=" + mData +
            ", mMsg='" + mMsg + '\'' +
            ", mName='" + mName + '\'' +
            '}';
    }
}
