package com.a51gjj.jsonserverapp.player;

/**
 * Created by Administrator on 2017/11/2 0002.
 */

public class Player {
    private String name = "";
    private int age = 0;
    private int rp = 0;
    private int shouru = 0;
    private int currentGjj = 0;
    private int caichan = 0;
    private String job = "";

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getCaichan() {
        return caichan;
    }

    public void setCaichan(int caichan) {
        this.caichan = caichan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public int getShouru() {
        return shouru;
    }

    public void setShouru(int totalMoney) {
        this.shouru = totalMoney;
    }

    public int getCurrentGjj() {
        return currentGjj;
    }

    public void setCurrentGjj(int currentGjj) {
        this.currentGjj = currentGjj;
    }
}
