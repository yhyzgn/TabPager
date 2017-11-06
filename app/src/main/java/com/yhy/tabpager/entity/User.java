package com.yhy.tabpager.entity;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-11-06 10:05
 * version: 1.0.0
 * desc   :
 */
public class User {

    public String name;
    public String avatar;
    public int age;
    public String introduction;

    public User(String name, String avatar, int age, String introduction) {
        this.name = name;
        this.avatar = avatar;
        this.age = age;
        this.introduction = introduction;
    }
}
