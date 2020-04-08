package com.szxmrt.android.app.cloudpointdestop.net.entity;


import android.support.annotation.NonNull;

import com.alibaba.fastjson.annotation.JSONField;

public class VendorInfo extends BaseResponse<VendorInfo.Data> {

    public class Data {
        private Info mInfo;

        public Info getInfo() {
            return mInfo;
        }

        public void setInfo(Info info) {
            mInfo = info;
        }

        @NonNull
        @Override
        public String toString() {
            return "Data{" +
                "mInfo=" + mInfo +
                '}';
        }

        public class Info{
            private String mVendorId;       // 设备id
            private String mVendorNikeName; // 设备昵称
            private String mVendorNum; // 设备编号

            private String shop_id;
            private String city;
            private int vendor_type;
            private String province;
            private String vendor_person;
            private int is_not_cart;
            private String vendor_model;
            private int aisle_num;
            private int aisle_capacity;
            private String point_id;
            private String address;
            private String production_num;
            private int lastmodtime;
            private double longitude;
            private String vendor_name;
            private int sell_status;
            private String person_phone;
            private String area;
            private int vendor_status;
            private double latitude;
            private String creat_time;
            private String point_name;
            private String detail_address;
            private String sell_goods_type;

            @JSONField(name = "vendor_id")
            public String getVendorId() {
                return mVendorId;
            }

            @JSONField(name = "vendor_id")
            public void setVendorId(String vendorId) {
                mVendorId = vendorId;
            }

            @JSONField(name = "vendor_nikename")
            public String getVendorNikeName() {
                return mVendorNikeName;
            }

            @JSONField(name = "vendor_nikename")
            public void setVendorNikeName(String vendorNikeName) {
                mVendorNikeName = vendorNikeName;
            }

            @JSONField(name = "vendor_num")
            public String getVendorNum() {
                return mVendorNum;
            }

            @JSONField(name = "vendor_num")
            public void setVendorNum(String vendorNum) {
                mVendorNum = vendorNum;
            }

	        public String getShop_id() {
		        return shop_id;
	        }

	        public void setShop_id(String shop_id) {
		        this.shop_id = shop_id;
	        }

	        public String getCity() {
		        return city;
	        }

	        public void setCity(String city) {
		        this.city = city;
	        }

	        public int getVendor_type() {
		        return vendor_type;
	        }

	        public void setVendor_type(int vendor_type) {
		        this.vendor_type = vendor_type;
	        }

	        public String getProvince() {
		        return province;
	        }

	        public void setProvince(String province) {
		        this.province = province;
	        }

	        public String getVendor_person() {
		        return vendor_person;
	        }

	        public void setVendor_person(String vendor_person) {
		        this.vendor_person = vendor_person;
	        }

	        public int getIs_not_cart() {
		        return is_not_cart;
	        }

	        public void setIs_not_cart(int is_not_cart) {
		        this.is_not_cart = is_not_cart;
	        }

	        public String getVendor_model() {
		        return vendor_model;
	        }

	        public void setVendor_model(String vendor_model) {
		        this.vendor_model = vendor_model;
	        }

	        public int getAisle_num() {
		        return aisle_num;
	        }

	        public void setAisle_num(int aisle_num) {
		        this.aisle_num = aisle_num;
	        }

	        public int getAisle_capacity() {
		        return aisle_capacity;
	        }

	        public void setAisle_capacity(int aisle_capacity) {
		        this.aisle_capacity = aisle_capacity;
	        }

	        public String getPoint_id() {
		        return point_id;
	        }

	        public void setPoint_id(String point_id) {
		        this.point_id = point_id;
	        }

	        public String getAddress() {
		        return address;
	        }

	        public void setAddress(String address) {
		        this.address = address;
	        }

	        public String getProduction_num() {
		        return production_num;
	        }

	        public void setProduction_num(String production_num) {
		        this.production_num = production_num;
	        }

	        public int getLastmodtime() {
		        return lastmodtime;
	        }

	        public void setLastmodtime(int lastmodtime) {
		        this.lastmodtime = lastmodtime;
	        }

	        public double getLongitude() {
		        return longitude;
	        }

	        public void setLongitude(double longitude) {
		        this.longitude = longitude;
	        }

	        public String getVendor_name() {
		        return vendor_name;
	        }

	        public void setVendor_name(String vendor_name) {
		        this.vendor_name = vendor_name;
	        }

	        public int getSell_status() {
		        return sell_status;
	        }

	        public void setSell_status(int sell_status) {
		        this.sell_status = sell_status;
	        }

	        public String getPerson_phone() {
		        return person_phone;
	        }

	        public void setPerson_phone(String person_phone) {
		        this.person_phone = person_phone;
	        }

	        public String getArea() {
		        return area;
	        }

	        public void setArea(String area) {
		        this.area = area;
	        }

	        public int getVendor_status() {
		        return vendor_status;
	        }

	        public void setVendor_status(int vendor_status) {
		        this.vendor_status = vendor_status;
	        }

	        public double getLatitude() {
		        return latitude;
	        }

	        public void setLatitude(double latitude) {
		        this.latitude = latitude;
	        }

	        public String getCreat_time() {
		        return creat_time;
	        }

	        public void setCreat_time(String creat_time) {
		        this.creat_time = creat_time;
	        }

	        public String getPoint_name() {
		        return point_name;
	        }

	        public void setPoint_name(String point_name) {
		        this.point_name = point_name;
	        }

	        public String getDetail_address() {
		        return detail_address;
	        }

	        public void setDetail_address(String detail_address) {
		        this.detail_address = detail_address;
	        }

	        public String getSell_goods_type() {
		        return sell_goods_type;
	        }

	        public void setSell_goods_type(String sell_goods_type) {
		        this.sell_goods_type = sell_goods_type;
	        }

	        @Override
	        public String toString() {
		        return "Info{" +
				        "mVendorId='" + mVendorId + '\'' +
				        ", mVendorNikeName='" + mVendorNikeName + '\'' +
				        ", mVendorNum='" + mVendorNum + '\'' +
				        ", shop_id='" + shop_id + '\'' +
				        ", city='" + city + '\'' +
				        ", vendor_type=" + vendor_type +
				        ", province='" + province + '\'' +
				        ", vendor_person='" + vendor_person + '\'' +
				        ", is_not_cart=" + is_not_cart +
				        ", vendor_model='" + vendor_model + '\'' +
				        ", aisle_num=" + aisle_num +
				        ", aisle_capacity=" + aisle_capacity +
				        ", point_id='" + point_id + '\'' +
				        ", address='" + address + '\'' +
				        ", production_num='" + production_num + '\'' +
				        ", lastmodtime=" + lastmodtime +
				        ", longitude=" + longitude +
				        ", vendor_name='" + vendor_name + '\'' +
				        ", sell_status=" + sell_status +
				        ", person_phone='" + person_phone + '\'' +
				        ", area='" + area + '\'' +
				        ", vendor_status=" + vendor_status +
				        ", latitude=" + latitude +
				        ", creat_time='" + creat_time + '\'' +
				        ", point_name='" + point_name + '\'' +
				        ", detail_address='" + detail_address + '\'' +
				        ", sell_goods_type='" + sell_goods_type + '\'' +
				        '}';
	        }
        }
    }
}
