package com.github.scheduler.model.scheduleprocessors.simplerealization;

import com.github.scheduler.model.outinterfaces.Point;

import org.joda.time.LocalDate;

/**
 * Objects of this class keeps schedule on 
 * concrete day. They are hidden in the Schedule
 * object and returns Points on requested time
 * interval.
 */
class CountedDay{
    private Point[] points = new PointImpl[24];
    private LocalDate date;
    
    public CountedDay(LocalDate date) {
        this.date = date;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public Point getPointAt(int timeInterval) {
        return  points[timeInterval];
    }

    /**
     * @return int free time in day
     */
    public int getFreeTime() {
        int freeTime = 0;
        
        for(Point p: points){
            if(p == null){
                freeTime++;
            }
        }
        
        return freeTime;
    }

    /**
     * @Return boolean if is there free time in 
     * specified hour
     */
    public boolean isHourFree(int hourOfDay){
        
        if(hourOfDay >= 0 && hourOfDay <= 23){
            return points[hourOfDay] == null;
        }
        
        return true;
    }
    
    //adds Task on specific hour
    public void addPoint(Point point, int hourOfDay) {
        if(points[hourOfDay] == null){
            points[hourOfDay] = point;
        }
    }
}
