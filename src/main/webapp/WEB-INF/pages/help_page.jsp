<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/help_page.css">
        
        <!-- css for both panels and body -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/upper_panel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/side_panel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/body_common.css">
    </head>
    <body class="Body_body">
        <%@ include file="upper_panel.jsp" %>
        <%@ include file="side_panel.jsp" %>
        
        <div class="HL_main">
            <div class="HL_page_header">Help</div>
            
        
            <P class="HL_text">This application calculates schedule. For this it require
                from you to enter tasks. Tasks divides on three types:</P>
                
            <ul class="HL_items">
                  <li>Routine. Executes all the time from start to end dates
                      in specified period. Limited term task has priority on Routine
                      task, so if you set Limited term task over Routine task at
                      the same time it will be Limited term task in schedule.
                  </li>
                  <br>
                   <li>Limited term task. It has limited terms from start date
                       (include day) to end date (exclude day). If there will not be
                       enough time to complete task than error message will appear on Days
                       review page.
                   </li>
                   <br>
                   <li>Flexible term task. It fills all the free time, which
                       doesn't occupy Routine or Limited term tasks from start date.
                   </li>
              </ul>
              
              <p class="HL_text">It's necessary to choose dates, days, hours and  a state (active or 
                  not) for every task on task edit or creation page.
              </p>
              <p class="HL_text">For navigation you should use left panel. There are three links to
                  days review, tasks review and create new task.
              </p>
              <p class="HL_text">Days review show schedule. Tasks review shows all the tasks with their
                  parameters. And create new task page allows you to create new task.
              </p>
              
              <br>
              <div class="HL_warning">Warning! If you will not be following these items error page will
                  appear:
              </div>
                  
              <ul class="HL_items">
                  <li>The start date of task must be before end date.
                  </li>
                  <br>
                   <li>Dates must be entered like YYYY-MM-DD (2015-07-01) and dates must
                       be correct (not 2015-02-31).
                   </li>
                   <br>
                   <li>Both dates must be entered with Flexible term point, but only
                       start date will be counted.
                   </li>
              </ul>
        
        </div>
    </body>
</html>
                