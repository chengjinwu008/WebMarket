package com.lanhaijiye.WebMarket.entities;

/**
 * Created by android on 2015/6/11.
 */
public class RegisterRequestEntity {

    String code;
    Data data;

    public RegisterRequestEntity(String code, Data data) {
        this.code = code;
        this.data = data;
    }

    public RegisterRequestEntity() {
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
        String smsCode;
        String nickname;

        public Data(String imei, String phoneNumber, String password, String smsCode, String nickname) {
            this.imei = imei;
            this.phoneNumber = phoneNumber;
            this.password = password;
            this.smsCode = smsCode;
            this.nickname = nickname;
        }

        public String getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(String smsCode) {
            this.smsCode = smsCode;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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
