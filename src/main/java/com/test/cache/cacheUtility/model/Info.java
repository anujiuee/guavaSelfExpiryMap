package com.test.cache.cacheUtility.model;

public class Info {
    private String randomNum;

    public Info(String randomNum) {
        this.randomNum = randomNum;
    }

    public String getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    @Override
    public String toString() {
        return "Info{" +
                "randomNum='" + randomNum + '\'' +
                '}';
    }
}
