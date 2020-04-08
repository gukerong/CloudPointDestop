package com.szxmrt.android.app.cloudpointdestop.net.entity;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxmrt.android.app.cloudpointdestop.net.HttpCode;

public class PublicKey extends BaseResponse<PublicKey.Data> {


    @Override
    public String getName() {
        return HttpCode.GET_RSA_PUBKEY;
    }

    public class Data {
        private String mPublicKey;

        public String getPublicKey() {
            return mPublicKey;
        }

        @JSONField(name = "publickey")
        public void setPublicKey(String publicKey) {
            mPublicKey = publicKey;
        }

        @NonNull
        @Override
        public String toString() {
            return "PublicKey{" +
                    "mPublicKey='" + mPublicKey + '\'' +
                    '}';
        }
    }
}
