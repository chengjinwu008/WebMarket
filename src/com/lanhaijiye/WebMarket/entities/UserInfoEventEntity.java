package com.lanhaijiye.WebMarket.entities;

/**
 * Created by android on 2015/6/14.
 */
public class UserInfoEventEntity {
    public String nickname;
    public String user_money;
    public String pay_points;

    public UserInfoEventEntity(String nickname, String user_money, String pay_points) {
        this.nickname = nickname;
        this.user_money = user_money;
        this.pay_points = pay_points;
    }
}
