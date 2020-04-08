package com.szxmrt.android.app.cloudpointdestop.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.szxmrt.android.app.cloudpointdestop.net.entity.Crypt;
import com.szxmrt.android.app.cloudpointdestop.net.entity.PublicKey;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class NetUtil {

    private static String TAG = NetUtil.class.getSimpleName();

    public static String createUrl(String opr) {
        return "?token=" + NetConfigure.getToken() + "&opr=" + opr;
    }


    public static Map<String, Object> createResponseMap(Map<String, Object> map, EncodeMode mode) {
        String dataStr = NetUtil.mapToUrl(map);
        final String opr = (String) map.get("opr");
        Map<String, Object> dataMap = new HashMap<>();
        switch (mode) {
            case AES:
                dataStr = Crypt.AES.Encrypt(NetConfigure.getDataKey(), dataStr);
                if (null == dataStr || dataStr.equals(""))
                    return null;
                if (null == opr || opr.equals(""))
                    return null;
                dataMap.put("sign", Crypt.Md5(dataStr + NetConfigure.getDataKey()));
                dataMap.put("data", dataStr);
                dataMap.put("encmode", "aes");
                break;
            case NONE:
                //dataStr = Crypt.AES.Encrypt(NetConfigure.DATA_KEY, dataStr);
                if (null == dataStr || dataStr.equals(""))
                    return null;
                if (null == opr || opr.equals(""))
                    return null;
                dataMap.put("sign", Crypt.Md5(dataStr + NetConfigure.getDataKey()));
                dataMap.put("data", dataStr);
                //dataMap.put("encmode", "aes");
                break;
            default:
                break;
        }
        dataMap.put("reqid", NetConfigure.incrementAndGet());
        dataMap.put("token", NetConfigure.getToken());
        return dataMap;
    }

    public static String mapToUrl(Map<String, Object> map) {
        if (map == null) return null;
        try {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                sb.append("&");
                sb.append(entry.getKey())
                        .append("=");
                if (entry.getValue() instanceof String)
                    sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                else
                    sb.append(URLEncoder.encode(JSON.toJSONString(entry.getValue()), "UTF-8"));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG,"unSupport encoding",e);
            return null;
        }
    }

    public static Map<String, Object> checkRSAKey(PublicKey pubKey) {
        String publicKey = pubKey.getData().getPublicKey();
        if (null == publicKey || publicKey.equals("")) {
	        Log.e(TAG, "data error, public key null");
            return null;
        }
        StringBuilder keyString = new StringBuilder();
        for (String s : publicKey.trim().split("[\r\n]+")) {
            if (s.startsWith("-----")) {
                continue;
            }
            keyString.append(s.trim());
        }
        String keyEnc = Crypt.RSA.Encrypt(keyString.toString(), NetConfigure.getDataKey());
        if (null == keyEnc || keyEnc.equals("")) {
	        Log.e(TAG, "ras encrypt err");
            return null;
        }
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("opr", "save_data_key");
        map.put("is_plain", "1");
        map.put("token", NetConfigure.getToken());
        map.put("key_enc", keyEnc);
        return map;
    }

}
