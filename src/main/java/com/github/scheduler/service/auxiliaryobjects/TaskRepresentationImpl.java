package com.github.scheduler.service.auxiliaryobjects;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.github.scheduler.model.domain.task.Task;
import com.github.scheduler.model.domain.task.TypeOfTask;
import com.github.scheduler.service.Interfaces.TaskRepresentation;

/**
 * This class represents Task object, to avoid
 * changes in original Task object
 */
//TODO this object unmodificable
public class TaskRepresentationImpl implements TaskRepresentation{
    
    private String username;
    private String title;
    private String description;
    private TypeOfTask type;
    private boolean isActive;
    private int necessaryTime;
    private long id;
    private LocalDate startDate;
    private Interval interval;
    private List<Boolean> activeDays;
    private List<Boolean> activeHours;
    
    //Constructor copies necessary fields from task
    public TaskRepresentationImpl(Task task){
        username = task.getUser().getName();
        id = task.getId();
        title = task.getTitle();
        description = task.getDescription();
        type = task.getType();
        isActive = task.isActive();
        necessaryTime = task.getNecessaryTime();
        
        //it's safety because a LocalData is immutable
        startDate = task.getStartDate();
        
        //it's safety because an Interval is immutable
        interval = task.getInterval();
        
        activeDays = new ArrayList<Boolean>(7);
        activeHours = new ArrayList<Boolean>(24);
        
        // + 1 because in Task day begins from 1
        for(int i = 0; i < 7; i++){
            activeDays.add(task.isActiveDayAt(i + 1));
        }
        
        for(int i = 0; i < 24; i++){
            activeHours.add(task.isActiveHourAt(i));
        }
    }
    
    //Constructor by default
    public TaskRepresentationImpl() {
        activeDays = new ArrayList<Boolean>(7);
        activeHours = new ArrayList<Boolean>(24);
        
        for(int i = 0; i < 7; i++){
            activeDays.add(false);
        }
        
        for(int i = 0; i < 24; i++){
            activeHours.add(false);
        }
    }
    
    //getters
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public TypeOfTask getType() {
        return type;
    }
    
    @Override
    public boolean getIsActive() {
        return isActive;
    }
    
    @Override
    public int getNecessaryTime() {
        return necessaryTime;
    }
    
    @Override
    public LocalDate getStartDate() {
        return startDate;
    }
    
    @Override
    public Interval getInterval() {
        return interval;
    }
    
    /**
     * 
     * @param dayOfWeek requested day 
     * from 1 = Monday to 7 = Sunday
     * 
     * @return boolean is day active at specified day
     */
    @Override
    public boolean getActiveDayAt(int dayOfWeek) {
        if(dayOfWeek < 8 && dayOfWeek > 0){
            return activeDays.get(dayOfWeek - 1);
        }else{
            return false;
        }
    }

    /**
     * @param hour int represents requested hour from 0 to 23
     * @return boolean is task active this time
     */
    @Override
    public boolean getActiveHourAt(int hour) {
        if(hour < 24 && hour >= 0){
            return activeHours.get(hour);
        }else{
            return false;
        }
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setType(TypeOfTask type) {
        this.type = type;
    }

    @Override
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void setNecessaryTime(int necessaryTime) {
        this.necessaryTime = necessaryTime;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setInterval(Interval interval) {
        this.interval = interval;
    }
    
    /**
     * set task active in specified hour
     * from 0 to 23
     */
    @Override
    public void setActiveHourAt(int hour, boolean isActive) {
        if(hour < 24 && hour >= 0){
            activeHours.set(hour, isActive);
        }
    }
    
    /**
     * set task active in specified day of week
     * from 1 = Monday to 7 = Sunday.
     */
    @Override
    public void setActiveDayAt(int dayOfWeek, boolean isActive) {
        if(dayOfWeek < 8 && dayOfWeek > 0){
            activeDays.set(dayOfWeek - 1, isActive);
        }
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public void setUserName(String username) {
        this.username = username;
    }
}
