package com.github.scheduler.model.domain.task;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.github.scheduler.model.domain.User;

/**
 * Tasks keep title, description, start and and dates
 * and other information about their selves.
 * Tasks can be three types: Routine, FlexibleTerm,
 * LimitedTerm. Routine task repeats all the time without
 * start and end period. This is a sleep, cooking food and etc.
 * Limited term point has start and and date and must be 
 * completed until end date, so for examle to study subject to 
 * exam date, complete project on work and etc. Flexible task
 * means task, which mustn't be finished to certain date.
 */

@Entity
@Table(name = "task")
public class Task{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="task_id", updatable = false)
    private long id;
    
    /*
     * I added this field to have opportunity to save new Task for user
     * wit TaskDao, without loading his Lazy.Loaded set<Task> from
     * UserDao
     */
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    private String title;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private TypeOfTask type;
    
    //is task active
    private boolean isActive;
    
    //general for flexible and Limited terms task
    private int necessaryTime;
    
    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;

    
    @Columns(columns={
            @Column(name="start_interval"),
            @Column(name="end_interval")})
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentInterval")
    //for Limited term task
    private Interval interval;

    /*
     * Task active days, from 1 = Monday to 7 = Sunday
     */
    @ElementCollection
    @OrderColumn(name="active_days_order")//enables order in db
    @CollectionTable(name="task_active_days",
        joinColumns=@JoinColumn(name="task_id"))
    @Column(name="active_days")
    private List<Boolean> activeDays;
    
    /*
     * Task active hours, 0-23
     */
    @ElementCollection
    @OrderColumn(name="active_hours_order")//enables order in db
    @CollectionTable(name="task_active_hours",
        joinColumns=@JoinColumn(name="task_id"))
    @Column(name="active_hours")
    private List<Boolean> activeHours;

    
    public Task(){

        activeDays = new ArrayList<Boolean>(7);
        activeHours = new ArrayList<Boolean>(24);
        
        //fills arrays with false
        for(int i = 0; i < 7; i++){
            activeDays.add(false);
        }
        
        for(int i = 0; i < 24; i++){
            activeHours.add(false);
        }
    }
    
    /*
     * invokes before entityManager.remove(this object)
     */
    @PreRemove
    public void preRemove(){
        user = null;
    }
    
    
    //getters end setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeOfTask getType() {
        return type;
    }

    public void setType(TypeOfTask type) {
        this.type = type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getNecessaryTime() {
        return necessaryTime;
    }

    public void setNecessaryTime(int necessaryTime) {
        this.necessaryTime = necessaryTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    /**
     * 
     * @param dayOfWeek requested day 
     * from 1 = Monday to 7 = Sunday
     * 
     * @return boolean is day active at specified day
     */
    public boolean isActiveDayAt(int dayOfWeek) {
        if(dayOfWeek < 8 && dayOfWeek > 0){
            return activeDays.get(dayOfWeek - 1);
        }else{
            return false;
        }
    }

    /**
     * set task active in specified day of week
     * from 1 = Monday to 7 = Sunday.
     */
    public void setActiveDayAt(int dayOfWeek, boolean isActive) {
        if(dayOfWeek < 8 && dayOfWeek > 0){
            activeDays.set(dayOfWeek - 1, isActive);
        }
    }

    /**
     * @param hour int represents requested hour from 0 to 23
     * @return boolean is task active this time
     */
    public boolean isActiveHourAt(int hour) {
        if(hour < 24 && hour >= 0){
            return activeHours.get(hour);
        }else{
            return false;
        }
    }
    
    /**
     * set task active in specified hour
     * from 0 to 23
     */
    public void setActiveHourAt(int hour, boolean isActive) {
        if(hour < 24 && hour >= 0){
            activeHours.set(hour, isActive);
        }
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setId(long id){
        this.id = id;
    }
}


