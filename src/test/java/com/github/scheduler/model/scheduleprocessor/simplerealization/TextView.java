package com.github.scheduler.model.scheduleprocessor.simplerealization;

import com.github.scheduler.model.outinterfaces.Schedule;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

/*
 * This is the text view for Schedule objects. It helps to count
 * by "hands" given period.
 */
public class TextView {
    
    public void printView(Schedule sch, Interval interval){

        LocalDate pointer = interval.getStart().toLocalDate();
        
        System.out.println("Summary errors: " + (
                sch.getTasksErrors().size()) + "\n");
        
        if(sch.getTasksErrors().size() != 0){
            System.out.println("TasksErrors:");
            
            for(String s: sch.getTasksErrors()){
                System.out.println("tsk: " + s);
            }
        }
        
        
        //through all day in interval
        while(interval.contains(pointer.toInterval())){
            System.out.println("view pointer: " + pointer + ", day: "
                    + pointer.getDayOfWeek()+"\n");
            
            for(int i=0; i<24; i++){
                System.out.println(pointer + ":  for " + i + " to " + (i+1) + 
                         ": " + sch.getPointAt(pointer, i).getTitle());
            }
            
            System.out.println("-------------------------------------\n");
            
            pointer = pointer.plusDays(1);
            
            /*try{//to see memory consumption
                Thread.sleep(20000);
            }catch(Exception e){
                
            }*/
        }
        
        System.out.println("total free time: " + sch.getTotalFreeTime());
    }
}
