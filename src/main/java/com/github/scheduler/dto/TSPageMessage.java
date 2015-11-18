package com.github.scheduler.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
 * This is a object, which jsp page passes to
 * controller wit filled fields
 */
public class TSPageMessage {
    
    /*
     * this field marks not new object and keeps his id
     */
    private long id;
    
    @NotNull(message="select type of task")
    private String typeOfTask;
    
    @Size(min=1, max=30, message="task name must be between 1 and 30 symbols")
    private String taskName;

    @Pattern(regexp="[2][0][0-9]{2}-[0-1][0-9]-[0-3][0-9]",
            message="incorrect start date")
    private String startDay;
    
    @Pattern(regexp="[2][0][0-9]{2}-[0-1][0-9]-[0-3][0-9]",
            message="incorrect end date")
    private String endDay;
    
    private String description;
    
    @Min(value = 1, message="necessary time must be more then zero")
    private int necessaryHours;
    
    private boolean Monday;
    private boolean Tuesday;
    private boolean Wednesday;
    private boolean Thursday;
    private boolean Friday;
    private boolean Saturday;
    private boolean Sunday;
    
    private boolean isActive;

    private boolean hour_0;
    private boolean hour_1;
    private boolean hour_2;
    private boolean hour_3;
    private boolean hour_4;
    private boolean hour_5;
    private boolean hour_6;
    private boolean hour_7;
    private boolean hour_8;
    private boolean hour_9;
    private boolean hour_10;
    private boolean hour_11;
    private boolean hour_12;
    private boolean hour_13;
    private boolean hour_14;
    private boolean hour_15;
    private boolean hour_16;
    private boolean hour_17;
    private boolean hour_18;
    private boolean hour_19;
    private boolean hour_20;
    private boolean hour_21;
    private boolean hour_22;
    private boolean hour_23;
    
    
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getStartDay() {
        return startDay;
    }
    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
    public String getEndDay() {
        return endDay;
    }
    public void setEndDay(String endtDay) {
        this.endDay = endtDay;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getNecessaryHours() {
        return necessaryHours;
    }
    public void setNecessaryHours(int necessaryHours) {
        this.necessaryHours = necessaryHours;
    }
    public boolean getMonday() {
        return Monday;
    }
    public void setMonday(boolean monday) {
        Monday = monday;
    }
    public boolean getTuesday() {
        return Tuesday;
    }
    public void setTuesday(boolean tuesday) {
        Tuesday = tuesday;
    }
    public boolean getWednesday() {
        return Wednesday;
    }
    public void setWednesday(boolean wednesday) {
        Wednesday = wednesday;
    }
    public boolean getThursday() {
        return Thursday;
    }
    public void setThursday(boolean thursday) {
        Thursday = thursday;
    }
    public boolean getFriday() {
        return Friday;
    }
    public void setFriday(boolean friday) {
        Friday = friday;
    }
    public boolean getSaturday() {
        return Saturday;
    }
    public void setSaturday(boolean saturday) {
        Saturday = saturday;
    }
    public boolean getSunday() {
        return Sunday;
    }
    public void setSunday(boolean sunday) {
        Sunday = sunday;
    }
    public boolean getHour_0() {
        return hour_0;
    }
    public void setHour_0(boolean hour_0) {
        this.hour_0 = hour_0;
    }
    public boolean getHour_1() {
        return hour_1;
    }
    public void setHour_1(boolean hour_1) {
        this.hour_1 = hour_1;
    }
    public boolean getHour_2() {
        return hour_2;
    }
    public void setHour_2(boolean hour_2) {
        this.hour_2 = hour_2;
    }
    public boolean getHour_3() {
        return hour_3;
    }
    public void setHour_3(boolean hour_3) {
        this.hour_3 = hour_3;
    }
    public boolean getHour_4() {
        return hour_4;
    }
    public void setHour_4(boolean hour_4) {
        this.hour_4 = hour_4;
    }
    public boolean getHour_5() {
        return hour_5;
    }
    public void setHour_5(boolean hour_5) {
        this.hour_5 = hour_5;
    }
    public boolean getHour_6() {
        return hour_6;
    }
    public void setHour_6(boolean hour_6) {
        this.hour_6 = hour_6;
    }
    public boolean getHour_7() {
        return hour_7;
    }
    public void setHour_7(boolean hour_7) {
        this.hour_7 = hour_7;
    }
    public boolean getHour_8() {
        return hour_8;
    }
    public void setHour_8(boolean hour_8) {
        this.hour_8 = hour_8;
    }
    public boolean getHour_9() {
        return hour_9;
    }
    public void setHour_9(boolean hour_9) {
        this.hour_9 = hour_9;
    }
    public boolean getHour_10() {
        return hour_10;
    }
    public void setHour_10(boolean hour_10) {
        this.hour_10 = hour_10;
    }
    public boolean getHour_11() {
        return hour_11;
    }
    public void setHour_11(boolean hour_11) {
        this.hour_11 = hour_11;
    }
    public boolean getHour_12() {
        return hour_12;
    }
    public void setHour_12(boolean hour_12) {
        this.hour_12 = hour_12;
    }
    public boolean getHour_13() {
        return hour_13;
    }
    public void setHour_13(boolean hour_13) {
        this.hour_13 = hour_13;
    }
    public boolean getHour_14() {
        return hour_14;
    }
    public void setHour_14(boolean hour_14) {
        this.hour_14 = hour_14;
    }
    public boolean getHour_15() {
        return hour_15;
    }
    public void setHour_15(boolean hour_15) {
        this.hour_15 = hour_15;
    }
    public boolean getHour_16() {
        return hour_16;
    }
    public void setHour_16(boolean hour_16) {
        this.hour_16 = hour_16;
    }
    public boolean getHour_17() {
        return hour_17;
    }
    public void setHour_17(boolean hour_17) {
        this.hour_17 = hour_17;
    }
    public boolean getHour_18() {
        return hour_18;
    }
    public void setHour_18(boolean hour_18) {
        this.hour_18 = hour_18;
    }
    public boolean getHour_19() {
        return hour_19;
    }
    public void setHour_19(boolean hour_19) {
        this.hour_19 = hour_19;
    }
    public boolean getHour_20() {
        return hour_20;
    }
    public void setHour_20(boolean hour_20) {
        this.hour_20 = hour_20;
    }
    public boolean getHour_21() {
        return hour_21;
    }
    public void setHour_21(boolean hour_21) {
        this.hour_21 = hour_21;
    }
    public boolean getHour_22() {
        return hour_22;
    }
    public void setHour_22(boolean hour_22) {
        this.hour_22 = hour_22;
    }
    public boolean getHour_23() {
        return hour_23;
    }
    public void setHour_23(boolean hour_23) {
        this.hour_23 = hour_23;
    }
    public String getTypeOfTask() {
        return typeOfTask;
    }
    public void setTypeOfTask(String typeOfTask) {
        this.typeOfTask = typeOfTask;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
}