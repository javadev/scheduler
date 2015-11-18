package com.github.scheduler.model.scheduleprocessors.simplerealization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import com.github.scheduler.model.domain.User;
import com.github.scheduler.model.domain.task.Task;
import com.github.scheduler.model.domain.task.TypeOfTask;
import com.github.scheduler.model.outinterfaces.Model;
import com.github.scheduler.model.outinterfaces.Point;
import com.github.scheduler.model.outinterfaces.Schedule;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

/**
 * This is an implementation of model interface.
 * Object of this class calculates and returns schedule.
 */

@Component
public class ScheduleProcessorSimpleImpl implements Model{

    //injects by calculateScheduleMethod
    private Interval requestedInterval;
    private User user;

    private ArrayList<String> tasksErrors = new ArrayList<String>();
    private int totalFreeTime;//total free time in requested interval
    
    //I added it to simplify view rendering 
    private ArrayList<String> tasksNamesInRequesteInterval = 
            new ArrayList<String>();
    
    //result of calculations
    private HashMap<LocalDate, CountedDay> countedDays;
    
    //considering limited terms tasks
    private Interval intervalToCount;//resets by method
    
    /* 
     * It synchronized because object of this class has state
     * To think: is it a reason to makes it prototype (but it will
     * stop all other request while one task will be calculating)
     * or create new instance for every session (but in one 
     * session can be few requsts at the same time)
     */
    @Override
    public synchronized Schedule calculateSchedule(User user,
            Interval interval){
    
        //sets variables
        this.user = user;
        requestedInterval = interval;
        
        /*System.out.println("requested interval: " + interval);
        
        System.out.println("\ntask size: " + user.getTasks().size());
        
        for(Task t: user.getTasks()){
            System.out.println("\ntask \": " + t.getTitle() 
                + "\" active: " + t.isActive());
            System.out.println("is active at 1day and 1hour: " + 
                    (t.isActiveDayAt(1)&t.isActiveHourAt(1)));
        }*/
        
        //resets this object state from prevision calculation
        resetState();
        
        //Calculate necessary interval to further calculations
        calculateIntervalToCount();
        //System.out.println("interval calculated");
        
        //makes empty days for calculating schedule
        makeEmptyCountedDays();
        //System.out.println("empty counted days made");
        
        //fills with limited terms tasks
        fillAllFreeTimeWithTasks(true);
        //System.out.println("days filled with limited tasks");
        
        //fills counted days with tasks
        fillsCountedDaysWithRoutine();
        //System.out.println("days filled with routine");

        //fills with flexible term points
        fillAllFreeTimeWithTasks(false);
        //System.out.println("days filled with limited tasks");
        
        //reduces counted interval to requested
        reduceToRequestedInterval();
        //System.out.println("interval reduced");
        
        //counts free time for requested period
        fillFreeTime();
        //System.out.println("Model: OK" + "\n");
        
        //fills names of tasks in requested interval
        getTasksNames();
        
        return new ScheduleImpl(countedDays,tasksErrors,
                totalFreeTime, tasksNamesInRequesteInterval);
    }
    
    
    /*
     * Calculates interval to count considers limited
     * term tasks. The interval with them must be
     * calculated to understand is it real to do them
     * in limited period.
     * 
     * In this implementation start date will be selected from
     * two dates: the earliest requested interval or the erliest
     * not routine task start date. The end day will be the
     * latest date from two: last requested day or the latest
     * end day in the LimitedTermTask from user
     */
    private void calculateIntervalToCount(){
        
        LocalDate startCountDate = requestedInterval.getStart().toLocalDate();
        LocalDate endCountDate = requestedInterval.getEnd().toLocalDate();
        
        //for every task
        for(Task task: user.getTasks()){
            if(task.isActive() && task.getType() == TypeOfTask.LimitedTerm){
                LocalDate startTaskDate = task.getInterval().
                        getStart().toLocalDate();
                LocalDate endTaskDate = task.getInterval().
                        getEnd().toLocalDate();
                
                //sets start date
                if(startCountDate.isAfter(startTaskDate)){
                    startCountDate = startTaskDate;
                }
                
                //sets end date
                if(endCountDate.isBefore(endTaskDate)){
                    endCountDate = endTaskDate;
                }
            }
        }
        
        intervalToCount = new Interval(startCountDate.toDate().getTime(),
                endCountDate.toDate().getTime());
    }
    
    /*
     * Fills Counted days with routine according to
     * found days
     */
    private void fillsCountedDaysWithRoutine(){
        
        //for every Routine point
        ArrayList<Task> routineTasks = new ArrayList<Task>();
                
        //adds all active Routine in intervalToCount to proceccing
        for(Task task: user.getTasks()){
            if(task.isActive() && task.getType() == TypeOfTask.Routine &&
                    task.getInterval().overlaps(intervalToCount)){
                routineTasks.add(task);
            }
        }
        
        //System.out.println("routine tasks size:" + routineTasks.size());
        
        LocalDate pointer = intervalToCount.getStart().toLocalDate();
        
        //while in interval
        while(intervalToCount.contains(pointer.toInterval())){
            CountedDay toFill = countedDays.get(pointer);
            
            ArrayList<Task> activeThisDayTasks = new ArrayList<Task>();
            
            //if task is active end schedulled on this day
            for(Task t: routineTasks){
                if(t.getInterval().overlaps(pointer.toInterval()) 
                        && t.isActiveDayAt(pointer.getDayOfWeek())){
                    activeThisDayTasks.add(t);
                }
            }
            
            
            for(int i=0; i<24; i++){//for all hours in this day
                if(toFill.isHourFree(i)){//if hour free
                    for(Task t: activeThisDayTasks){//for every task
                        if(t.isActiveHourAt(i)){//if task active this time
                                    
                            PointImpl p = new PointImpl(t.getTitle(),
                                    t.getDescription(), t.getInterval().
                                    getStart().toLocalDate().toString(),
                                    t.getInterval().getEnd().toLocalDate().
                                    toString());
                            
                            //adds task to schedule
                            toFill.addPoint(p, i);
                        }
                    }
                }
                
            }
            
            pointer = pointer.plusDays(1);
        }
    }
    
    
    /*
     * Reduces counted interval to requested
     */
    private void reduceToRequestedInterval(){
        LocalDate pointer = intervalToCount.getStart().toLocalDate();
        
        //while in interval
        while(intervalToCount.contains(pointer.toInterval())){
            if(!requestedInterval.contains(pointer.toInterval())){
                countedDays.remove(pointer);
            }
            
            pointer = pointer.plusDays(1);
        }
    }
    
    /*
     * Fills CountedDays with points with limited terms
     */
    private void fillAllFreeTimeWithTasks(boolean isLimited){
        
        //for every limited point
        ArrayList<Task> tasksToFill = new ArrayList<Task>();
        
        //for LimitedTerm or Flexible term task
        TypeOfTask type = isLimited ? TypeOfTask.LimitedTerm :
                TypeOfTask.FlexibleTerm;
        
        //retrieves limited term tasks
        for(Task task: user.getTasks()){
            if(task.isActive() && task.getType() == type){
                tasksToFill.add(task);
                //System.out.println("type of task: " + task.getType());
            }
        }
    
        
        for(Task task: tasksToFill){//for every task
            //if there is null instead task
            if(task == null){
                continue;
            }
            
            PointImpl p = null;
            
            if(isLimited){//makes Point with interval
                p = new PointImpl(task.getTitle(), task.getDescription(),
                        task.getInterval().getStart().toLocalDate().toString(),
                        task.getInterval().getEnd().toLocalDate().toString());
            }else{//makes point with start date
                p = new PointImpl(task.getTitle(), task.getDescription(),
                        task.getStartDate().toString(),
                        "");
            }
            
            /*
             * real interval for LimitedTerm task and artificial for 
             * FlexibleTerm tasks
             */
            Interval taskInterval = isLimited ? task.getInterval() :
                new Interval(task.getStartDate().toDate().getTime(),
                        intervalToCount.getEndMillis());
            
            LocalDate pointer = null;

            int unallocatedTaskTime = task.getNecessaryTime();
            
            //fills free time
            while(unallocatedTaskTime != 0){//while all the point time isn't located
                
                boolean change = false;
                
                //System.out.println("here 1");
                
                //start date for LimitedTerm or Flexible term task
                pointer = isLimited ? taskInterval.getStart().toLocalDate() :
                    task.getStartDate();
                
                //trying to fill time evenly for all period of task
                while(taskInterval.contains(pointer.toInterval())){
                    
                    //System.out.println("here 2");
                    
                    //if task inactive this day (day of week)
                    int currentDayOfWeek = pointer.getDayOfWeek();
                    if(!task.isActiveDayAt(currentDayOfWeek)){
                        pointer = pointer.plusDays(1);
                        continue;
                    }
                    
                    //break the loop if point is located
                    if(unallocatedTaskTime == 0){
                        break;
                    }
                    
                    int freeTimeOnDay = countedDays.get(pointer).getFreeTime();
                    
                    if(freeTimeOnDay == 0){
                        pointer = pointer.plusDays(1);//
                        continue;//skips busy day 
                    }
                    
                    //fills all free time in this day
                    breakPoint: for(int i=0; i < freeTimeOnDay; i++){
                        for(int j = 0; j < 24; j++){
                            if(countedDays.get(pointer).isHourFree(j) &&
                                    task.isActiveHourAt(j)){
                                countedDays.get(pointer).addPoint(p, j);
                                unallocatedTaskTime--;
                                
                                //task was pasted at least in one hour
                                change = true;
                                
                                if(unallocatedTaskTime == 0){
                                    break breakPoint;
                                }
                            }
                        }
                    }
                                        
                    
                    pointer = pointer.plusDays(1);
                }
                
                //there is no free time in days in task interval
                //that means that it's impossible locate point to exist time
                if(!change){
                    if(isLimited){//adds error meaasge if it is limited term point task
                        String error = "There isn't enought time to complete " + 
                            task.getTitle() +
                            ". It's necessary " + unallocatedTaskTime  + 
                            " hour(s) to complete it.";
                        
                            tasksErrors.add(error);
                    }
                    
                    //if there isn't any places - method must be break or 
                    //it will be endless loop
                    //break;
                    
                    //to show schedule
                    unallocatedTaskTime = 0;
                }
            }
        }
    }
    
    /*
     * prepares new empty counted days
     */
    private void makeEmptyCountedDays(){
        countedDays = new HashMap<LocalDate, CountedDay>();
        
        LocalDate pointer = intervalToCount.getStart().toLocalDate();

        //while in interval
        while(intervalToCount.contains(pointer.toInterval())){
            countedDays.put(pointer, new CountedDay(new LocalDate(pointer)));
            pointer = pointer.plusDays(1);
        }
    }
    
    /*
     * Feels all free time with free time points
     */
    private void fillFreeTime(){
        totalFreeTime = 0;//total free time in requested interval
        
        PointImpl p = new PointImpl("Free time ", 
                "you can plan something for this time", "", "");
        
        for(LocalDate date: countedDays.keySet()){
            CountedDay day = countedDays.get(date);
            int freeTime = day.getFreeTime();
            
            if(freeTime > 0){
                for(int i = 0; i < 24; i++){
                    if(day.isHourFree(i)){
                        day.addPoint(p, i);
                        totalFreeTime++;
                    }
                }
            }
        }
    }
    
    //clears state of object from prevision invokation
    private void resetState(){
        //clears ArrayLists with errors and tasks
        tasksErrors.clear();
        tasksNamesInRequesteInterval.clear();    
    }
    
    /*
     * returns all the tasks titles which will be on 
     * requested interval.
     */
    private void getTasksNames(){
        
        Set<LocalDate> allDays = countedDays.keySet();
        TreeSet<String> tasksNames = new TreeSet<String>();
        
        //puts all tasks name to set
        for(LocalDate d: allDays){
            CountedDay curDay = countedDays.get(d);
            for(int i=0; i<24; i++){
                
                Point p = curDay.getPointAt(i);
                
                tasksNames.add(p.getTitle());
            }
        }
        
        for(String s: tasksNames){
            tasksNamesInRequesteInterval.add(s);
        }
    }
}
