package com.github.scheduler.service.Interfaces;


import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.github.scheduler.model.domain.task.TypeOfTask;


/**
 * This Interface represents Task for View layer.
 */
public interface TaskRepresentation {
    
    public String getUserName();
    public long getId();
    public String getTitle();
    public String getDescription();
    public TypeOfTask getType();
    public boolean getIsActive();
    public int getNecessaryTime();
    public LocalDate getStartDate();
    public Interval getInterval();
    
    public void setTitle(String title);

    public void setUserName(String username);
    public void setDescription(String description);
    public void setType(TypeOfTask type);
    public void setActive(boolean isActive);
    public void setNecessaryTime(int necessaryTime);
    public void setId(long id);
    public void setStartDate(LocalDate startDate);
    public void setInterval(Interval interval);
    
    /**
     * set task active in specified hour
     * from 0 to 23
     */
    public void setActiveHourAt(int hour, boolean isActive);
    
    /**
     * set task active in specified day of week
     * from 1 = Monday to 7 = Sunday.
     */
    public void setActiveDayAt(int dayOfWeek, boolean isActive);
    
    /**
     * 
     * @param dayOfWeek requested day 
     * from 1 = Monday to 7 = Sunday
     * 
     * @return boolean is day active at specified day
     */
    public boolean getActiveDayAt(int dayOfWeek);

    /**
     * @param hour int represents requested hour from 0 to 23
     * @return boolean is task active this time
     */
    public boolean getActiveHourAt(int hour);
}
