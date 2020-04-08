package com.szxmrt.android.app.cloudpointdestop.net.entity;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxmrt.android.app.cloudpointdestop.net.HttpCode;

public class VendorLogin extends BaseResponse<VendorLogin.Data> {

    @Override
    public String getName() {
        return HttpCode.VENDOR_LOGIN;
    }

    public class Data {

        private String mVendorId;

        public String getVendorId() {
            return mVendorId;
        }

        @JSONField(name = "vendor_id")
        public void setVendorId(String vendorId) {
            mVendorId = vendorId;
        }

        @NonNull
        @Override
        public String toString() {
            return "VendorLogin{" +
                    "mVendorId='" + mVendorId + '\'' +
                    '}';
        }
    }
}
