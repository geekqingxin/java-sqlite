package com.taione.pojo;

import javax.validation.constraints.NotNull;

/**
 * Created by td on 2017/4/25.
 */
public class DataEntity {
    @NotNull
    private String dataCount;
    @NotNull
    private String one;

    public String getDataCount() {
        return dataCount;
    }

    public void setDataCount(String dataCount) {
        this.dataCount = dataCount;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }
    @NotNull
    private String two;
    @NotNull
    private String three;
    @NotNull
    private String dayTime;
}
