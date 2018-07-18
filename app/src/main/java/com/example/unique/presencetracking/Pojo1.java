package com.example.unique.presencetracking;

/**
 * Created by salma on 10-03-2018.
 */


class Pojo1
{
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    String userid;
    String result;

//generate setter and getters
}

/*
* {"result":"success","uid":"150"}
*
* {"result":"fail","uid":"0"}
*
* */