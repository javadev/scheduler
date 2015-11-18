package com.github.scheduler.model.scheduleprocessors.simplerealization;

import com.github.scheduler.model.outinterfaces.Point;

/**
 * This is implementation of Point interface.
 * Point means one indivisible cell on
 * schedule, but it keeps start and end date
 * for whole Task.
 *
 * It necessary for View, this objects will be pass
 * to View to draw schedule
 * 
 * Object which counts schedule fills all the
 * fields of this class while it counting schedule.
 * It takes data from task objects.
 */
class PointImpl implements Point{
    private String title;
    private String description;
    private String startDay;
    private String endDay;
    
    public PointImpl(String title, String description, String startDay,
            String endDay) {
        this.title = title;
        this.description = description;
        this.startDay = startDay;
        this.endDay = endDay;
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getStartDay() {
        return startDay;
    }
    
    @Override
    public String getEndDay() {
        return endDay;
    }
}
