package com.github.scheduler.model.outinterfaces;

import org.joda.time.Interval;

import com.github.scheduler.model.domain.User;

/**
 * This interface for classes which will be 
 * calculate schedules.
 */

public interface Model{
    
    /**
     * Returns a calculated Schedule
     */
    public Schedule calculateSchedule(User user,Interval interval);
}
