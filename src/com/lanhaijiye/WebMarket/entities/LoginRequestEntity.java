package com.lanhaijiye.WebMarket.entities;

/**
 * Created by android on 2015/6/11.
 */
public class LoginRequestEntity {

    String code;
    Data data;

    public LoginRequestEntity(String code, Data data) {
        this.code = code;
        this.data = data;
    }

    public LoginRequestEntity() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        String imei;
        String phoneNumber;
        String password;
        String userName="";

        public Data(String imei, String userName, String password) {
            this.imei = imei;
            this.phoneNumber = userName;
            this.password = password;
        }

        public Data() {
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
