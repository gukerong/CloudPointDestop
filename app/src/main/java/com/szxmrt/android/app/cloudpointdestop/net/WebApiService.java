package com.szxmrt.android.app.cloudpointdestop.net;

import com.szxmrt.android.app.cloudpointdestop.net.entity.Output;
import com.szxmrt.android.app.cloudpointdestop.net.entity.PublicKey;
import com.szxmrt.android.app.cloudpointdestop.net.entity.SubmitKey;
import com.szxmrt.android.app.cloudpointdestop.net.entity.VendorInfo;
import com.szxmrt.android.app.cloudpointdestop.net.entity.VendorLogin;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface WebApiService {

	@POST
	@FormUrlEncoded
	Observable<PublicKey> getPublicKey(@Url String url, @FieldMap Map<String, Object> map);

	@POST()
	@FormUrlEncoded
	Observable<SubmitKey> submitKey(@Url String url, @FieldMap Map<String, Object> map);

	@POST()
	@FormUrlEncoded
	Observable<VendorLogin> connect(@Url String url, @FieldMap Map<String, Object> map);

	@POST
	@FormUrlEncoded
	Observable<VendorInfo> getVendorInfo(@Url String url, @FieldMap Map<String, Object> map);

	@GET("https://gitee.com/gukerong/CloudPointDesktop/raw/master/app/release/output.json")
	Observable<Output> getOutput();
}
