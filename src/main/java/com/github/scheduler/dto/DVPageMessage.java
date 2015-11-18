package com.github.scheduler.dto;

import javax.validation.constraints.Pattern;

/*
 * This is a object, which jsp page passes to
 * controller wit filled fields
 */
public class DVPageMessage {
    
    @Pattern(regexp="[2][0][0-9]{2}-[0-1][0-9]-[0-3][0-9]",
            message="incorrect from date")
    private String startDay;
    
    @Pattern(regexp="[2][0][0-9]{2}-[0-1][0-9]-[0-3][0-9]",
            message="incorrect to date")
    private String endDay;
    
    /*
     * getter and setter necessary to retrieve data
     */
    
    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
}
