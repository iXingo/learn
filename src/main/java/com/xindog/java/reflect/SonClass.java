package com.xindog.java.reflect;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    5/16/19
 * Time:    11:01 AM
 * Project: learn
 */
public class SonClass extends FatherClass {
    public String mSonBirthday;
    protected int mSonAge;
    private String mSonName;

    public void printSonMsg() {
        System.out.println("Son Msg - name : "
                + mSonName + "; age : " + mSonAge);
    }

    private int getSonAge() {
        return mSonAge;
    }

    private void setSonAge(int age) {
        mSonAge = age;
    }

    private String getSonName() {
        return mSonName;
    }

    private void setSonName(String name) {
        mSonName = name;
    }
}
