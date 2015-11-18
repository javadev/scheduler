package com.github.scheduler.model.scheduleprocessor.simplerealization;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import com.github.scheduler.model.domain.task.Task;
import com.github.scheduler.model.domain.task.TypeOfTask;
import com.github.scheduler.model.outinterfaces.Model;
import com.github.scheduler.model.outinterfaces.Schedule;
import com.github.scheduler.model.scheduleprocessors.simplerealization.ScheduleProcessorSimpleImpl;
import com.github.scheduler.repository.TaskDao;
import com.github.scheduler.repository.UserDao;
import com.github.scheduler.configuration.JpaTestConfig;

/*
 * Tests for ScheduleProcessorSimpleImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaTestConfig.class})
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@Transactional
public class ScheduleProcessorSimpleImplTest {
    
    private Model model = new ScheduleProcessorSimpleImpl();;
    private Schedule schedule;
    
    @Autowired
    private UserDao userdao;
    
    @Autowired
    private TaskDao taskDao;
    
    private Interval requestedInterval;
    
    /*
     * simple test for ScheduleProcessorSimpleImpl
     */
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void testScheduleProcessor(){

        LocalDate countingPoint = new LocalDate(2015, 03, 10);
        
        requestedInterval = new 
                Interval(countingPoint.minusDays(2)
                        .toDate().getTime(), 
                        countingPoint.plusDays(3)
                        .toDate().getTime());
        
        Interval LimitedTaskInterval = new 
                Interval(countingPoint.minusDays(1)
                        .toDate().getTime(), 
                        countingPoint.plusDays(2)
                        .toDate().getTime());
        
        LocalDate flexibleTermstartDate = countingPoint;
        
        Task sleep = new Task();
        
        sleep.setTitle("sleeping");
        sleep.setInterval(requestedInterval);
        sleep.setType(TypeOfTask.Routine);
        sleep.setNecessaryTime(1);
        
        //makes days in task (sleeping) active
        for(int i = 1; i < 8; i++){
            sleep.setActiveDayAt(i, true);
        }
        
        //makes hours in task (sleeping) active
        for(int j = 22; j < 24; j++){
            sleep.setActiveHourAt(j, true);
        }
        
        //makes hours in task (sleeping) active
        for(int j = 0; j < 6; j++){
            sleep.setActiveHourAt(j, true);
        }
        
        //adds task to created user
        Task flexibleTerms = new Task();
        Task limitedTerms = new Task();
        
        flexibleTerms.setTitle("flexible");
        flexibleTerms.setStartDate(flexibleTermstartDate);
        flexibleTerms.setType(TypeOfTask.FlexibleTerm);
        flexibleTerms.setNecessaryTime(18);
        
        limitedTerms.setTitle("limited");
        limitedTerms.setInterval(LimitedTaskInterval);
        limitedTerms.setType(TypeOfTask.LimitedTerm);
        limitedTerms.setNecessaryTime(10);
        
        flexibleTerms.setActive(true);
        limitedTerms.setActive(true);
        
        for(int i = 1; i < 8; i++){
            if(i != 4){//makes one day unactive
                flexibleTerms.setActiveDayAt(i, true);
            }
            limitedTerms.setActiveDayAt(i, true);
        }
        
        for(int j = 0; j < 24; j++){
            if(j > 10 && j < 20){
                limitedTerms.setActiveHourAt(j, true);
            }
            if(j > 14){
                flexibleTerms.setActiveHourAt(j, true);
            }
        }
        
        limitedTerms.setActive(true);
        flexibleTerms.setActive(true);
        sleep.setActive(true);
    
        //sets user for each task
        sleep.setUser(userdao.getUserByUserName("test_user_1"));
        flexibleTerms.setUser(userdao.getUserByUserName("test_user_1"));
        limitedTerms.setUser(userdao.getUserByUserName("test_user_1"));
            
        taskDao.saveOrUpdateTask(sleep);
        taskDao.saveOrUpdateTask(flexibleTerms);
        taskDao.saveOrUpdateTask(limitedTerms);
        
        //few times because it was bug with state of 
        //scheduler implementation
        for(int i=0; i<5; i++){
                        
            schedule = model.calculateSchedule(userdao.getUserByUserName("test_user_1"),
                        requestedInterval);
            
            assertEquals(schedule.getTasksErrors().size(), 0);
            assertEquals(56, schedule.getTotalFreeTime());
        }
    }
}
