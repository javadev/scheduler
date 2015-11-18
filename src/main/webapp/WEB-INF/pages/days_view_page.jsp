<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%@ page import="org.joda.time.LocalDate, org.joda.time.Period,
                 org.joda.time.PeriodType,  org.joda.time.Interval,
                 org.joda.time.Days,
                java.util.Map, java.util.LinkedHashMap, java.util.HashMap,
                java.util.Locale,
                com.github.scheduler.model.outinterfaces.Point,
                com.github.scheduler.model.outinterfaces.Schedule,
                java.util.ArrayList, java.util.Collections,
                java.lang.String, java.lang.Integer, java.util.Locale"
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    /*
     * this code parses schedule to mark months boundaries
     * for nice representing
     */

    //these two values must be equal to values in "days_view_page.css"
    int sqSize = 18;//size of every square element
    int space = 2;//space between days
    
            int totalMonths;//total monthes in requested interval
    int startYear;//year of start interval
    int startMonths;//months in beginning of interval
    int firstMonthStartDay;//start day in first months
    int daysInFirstMonth;//number of days in first months
    int lastDayOfLastMonth;//last days in last months
    int totalDays;//total days, necessary to count min general month div width
    
    String firstMonthName;//name of the firts month
    String lastMonthName;//name of last months
    
    Interval interval = (Interval) request.getAttribute("interval");
    //System.out.println(interval);
    
    //days in certain months in interval
    //keeps insertion order
    Map<String, Integer> daysInMonthes = null;
    
    //TODO think how to do it more correctly
    //This map necessary to retrieve Point object from schedule
    Map<String, Point> points;
    
    Schedule schedule = (Schedule)request.getAttribute("schedule");//schedule
    
    
    
    //how days in period
    totalDays = Days.daysBetween(interval.getStart(), interval.getEnd())
            .getDays();
    
    //how months in period
    Period p = new Period(interval.getStartMillis(),
            interval.getEndMillis(), PeriodType.months().
                withDaysRemoved());
    totalMonths = p.getMonths() + 1;
    
    /*
     * parsed start position for pointer (pointer increase 
     * on 1 month in loop)
     */
    daysInMonthes = new LinkedHashMap<String, Integer>();
    startYear = new LocalDate(interval.getStartMillis()).
            getYear();
    startMonths = new LocalDate(interval.getStartMillis()).
            getMonthOfYear();
    
    //days in first months
    firstMonthStartDay = new LocalDate(interval.getStartMillis()).
            getDayOfMonth();
    daysInFirstMonth = new LocalDate(interval.getStartMillis()).
            dayOfMonth().getMaximumValue();
    firstMonthName = new LocalDate(interval.getStartMillis()).
            monthOfYear().getAsText(new Locale("en")) + " " +
            new LocalDate(interval.getStartMillis()).getYear();
    daysInMonthes.put(firstMonthName, daysInFirstMonth);
    
    //days in other months except last
    for(int i = 0; i < totalMonths - 2; i++){
        //begining of the start months
        LocalDate date = new LocalDate(startYear, startMonths, 1);
        //retrieving necessary months
        date = date.plusMonths(i+1);
        //retrieving last day in months
        int daysQuantity = date.dayOfMonth().getMaximumValue();
        //retrieving months name
        String monthName = date.monthOfYear().getAsText(new Locale("en")) +
                " " + date.getYear();
        //saves data in the Map
        daysInMonthes.put(monthName, daysQuantity);
    }
    
    //necessary days in last months
    //-1 because interval excludes end date
    lastDayOfLastMonth = new LocalDate(interval.getEndMillis()).
            getDayOfMonth() - 1;
    lastMonthName = new LocalDate(interval.getEndMillis()).
            monthOfYear().getAsText(new Locale("en")) + " " +
            new LocalDate(interval.getEndMillis()).getYear();
    daysInMonthes.put(lastMonthName, lastDayOfLastMonth);
    
    
    
    //fills the points Map with point with certain date and hour
    points = new HashMap<String, Point>();
    
    LocalDate pointer = interval.getStart().toLocalDate();
        
    //while in interval
    while(interval.contains(pointer.toInterval())){
        //for each hour in day
        for(int i = 0; i < 24; i++){
            Point point = schedule.getPointAt(pointer, i);
        
            String fullDate = pointer.monthOfYear().
                    getAsText(new Locale("en")) + " " +
                    pointer.getYear() + " " + pointer.getDayOfMonth() + 
                    " " + new Integer(i);
            
            //points.put(fullDate, point);
            points.put(fullDate, point);
        }
        
        pointer = pointer.plusDays(1);
    }
    
    
    //makes variable avaible with JSP EL and JSTL 
    request.setAttribute("sqSize", sqSize);
    request.setAttribute("space", space);
    request.setAttribute("totalMonths", totalMonths);
    request.setAttribute("startYear", startYear);
    request.setAttribute("startMonths", startMonths);
    request.setAttribute("firstMonthStartDay", firstMonthStartDay);
    request.setAttribute("daysInFirstMonth", daysInFirstMonth);
    request.setAttribute("lastDayOfLastMonth", lastDayOfLastMonth);
    request.setAttribute("firstMonthName", firstMonthName);
    request.setAttribute("lastMonthName", lastMonthName);
    request.setAttribute("daysInMonthes", daysInMonthes);
    request.setAttribute("totalDays", totalDays);
    request.setAttribute("points", points);
    
    
    
   /*
    * This code generates different colors for different task
    */
    Map<String, String> taskColorMapping = new HashMap<String, String>();
    ArrayList<String> colors = new ArrayList<String>();
            
    int totalColors = 0;
    int counter = 0;
        
    //adds collors to map
    colors.add("C0C0C0");
    colors.add("800080");
    //colors.add("00FF00");
    colors.add("F08080");
    colors.add("BD7F34");
    colors.add("BDB76B");
    colors.add("FFE4C4");
    colors.add("F4A460");
    colors.add("DAA520");
    //colors.add("A52A2A");
    colors.add("0000FF");
    colors.add("1E90FF");
    colors.add("008B8B");
    colors.add("228B22");
                
    totalColors = colors.size();
            
    for(String s: schedule.getTasksNames()){
        //if there are tasks more than generated colors
        if(counter == totalColors){
            counter = 0;
        }
            
        //assigns green color for free time
        if(s.equals("Free time ")){
            taskColorMapping.put(s, "6BB247");
        }else{
            taskColorMapping.put(s, colors.get(counter));
        }
            
        counter++;
    }
        
    session.setAttribute("taskColor", taskColorMapping);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/days_view_page.css">
        
        <!-- css for both panels and body -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/upper_panel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/side_panel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/body_common.css">
    </head>
    <body class="Body_body">
        <%@ include file="upper_panel.jsp" %>
        <%@ include file="side_panel.jsp" %>
        <!-- general div of this jsp page --> 
        <div style="float: left; max-width: 1000px">
            <span class="DVP_page_header">Days review</span>
            
            <!-- schedule counting errors div -->
            <div class="DVP_input_errors">
                <c:if test="${fn:length(schedule.tasksErrors) gt 0}">
                       <c:forEach var="err" items="${schedule.tasksErrors}">
                           ${err}
                       </c:forEach>
                           <br>May be some other tasks can't be completed too.
                </c:if>
            </div>
            
            <!-- general div for month and buttons -->
            <div  style="float: left">
                <!-- to make all month stay in one line -->
                <!-- math: (day + both day spaces(margins)) * number of days + total months * months margin + 
                        + column with time + constant (experementaly 20 px)>
                <!-- CONTAINER FOR ALL MONTHS -->
                <div style="min-width: ${totalDays * (sqSize + 2 * space) + totalMonths * 8 + 20 + sqSize * 5.3}px">
                    <c:set var="counter" value="1"/>
                    
                    <!-- column with time -->
                    <div style="width: ${sqSize * 5.3}px; float: left; margin: 2px">
                        <c:forEach var="i" begin="1" end="26">
                            <c:choose>
                                <c:when test="${i < 3}">
                                    <div class="DVP_hour_empty_label"></div>
                                </c:when>
                                <c:otherwise>
                                    <div class="DVP_hour_label">
                                        <c:choose>
                                            <c:when test="${i > 2 and i < 12}">
                                                0${i - 3}:00-0${i-2}:00
                                            </c:when>
                                            <c:when test="${i == 12}">
                                                09:00-10:00
                                            </c:when>
                                            <c:when test="${i > 12}">
                                                ${i - 3}:00-${i-2}:00
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div><!--end column with time -->
                
                
                    <!-- this div keeps in scroll bar div with all months -->
                    <div style="max-width: 1000px; float: left; overflow: auto">
                
                        <!-- div for months wit their natural sizes -->
                        <div style="min-width: ${totalDays * (sqSize + 2 * space) + totalMonths * 8}px; float: left">
                    
                            <!-- MAIN MOTHS LOOP -->
                            <c:forEach var="monthsMapElement" items="${daysInMonthes}">                
                            
                                <!-- ternary if -->
                                <c:set var="containerWidth" value="${ counter == 1 ? 
                                        ((monthsMapElement.value-firstMonthStartDay + 1) *     sqSize + 
                                            (monthsMapElement.value-firstMonthStartDay + 1) * 2 * space + 1)
                                    :    
                                            (monthsMapElement.value * sqSize + monthsMapElement.value * 2 * space + 1)}"/>
                                
                                <c:set var="labelWidth" value = "${containerWidth - 
                                        2 * space}"/> 
                                 
                                <!-- FROM THIS POINT GO MONTHS WITH LABELS -->
                                <div style="width: ${containerWidth}px; float: left; margin-right: 5px">
                                    
                                    
                                    <!-- months name -->
                                    <div style="width: ${labelWidth}px; text-align: center; background-color: #ADD8E6;
                                             margin: ${space}px; border-radius: 7px">
                                        ${monthsMapElement.key}
                                    </div>
                                    
                                    <!-- month's schedule representation div -->
                                    <div style="width: ${containerWidth}px">
                                        
                                        <!-- days string (top row under months name) -->
                                        <c:forEach var="i" begin="${counter == 1 ? firstMonthStartDay : 1}" 
                                                end="${monthsMapElement.value + day}">
                                            <div class="DVP_number_square">
                                                ${i}
                                            </div>
                                        </c:forEach>
                                        
                                        <!-- for every 24 hours in day -->
                                        <c:forEach var="hour" begin="1" end="24">
                                            
                                            <!-- for every hour in day sequence -->
                                            <c:forEach var="day" begin="${counter == 1 ? firstMonthStartDay : 1}" 
                                                    end="${monthsMapElement.value}">
                                                
                                                <!-- prepares key for map -->
                                                <c:set var="fullDate" value="${monthsMapElement.key} ${day} ${hour-1}"/>
                                                
                                                <!-- point parameters -->
                                                <c:set var="pointTitle" value="${points[fullDate].title}"/>
                                            
                                                <c:set var="pointStartDate" value="${points[fullDate].startDay}"/>
                                                <c:set var="pointEndtDate" value="${points[fullDate].endDay}"/>
                                                <c:set var="pointDescription" value="${points[fullDate].description}"/>
                                                
                                                <c:set var="toDivTitle" value=
                                                    "title: ${pointTitle},  start: ${pointStartDate},  end: ${pointEndtDate},  description: ${pointDescription}"/>
                                                
                                                <div class="DVP_square" title="${toDivTitle}"
                                                        style="background-color: #${taskColor[pointTitle]}">
                                                    <!-- temporary just unsigned square 
                                                        here must be code which retrieves
                                                        data for every code and changes 
                                                        style for this div-->
                                                </div>
                                            </c:forEach>
                                        </c:forEach><!-- end for every 24 hours in day -->
                                    </div><!-- end month's schedule representation div -->
                                </div><!--END FROM THIS POINT GO MONTHS WITH LABELS -->
                                
                                <c:set var="counter" value="${counter + 1}"/>
                            </c:forEach><!-- END MAIN MOTHS LOOP -->
                        </div><!-- end div for months wit their natural sizes -->
                    </div><!-- end this div keeps in scroll bar div with all months -->
                    
                    <!-- legend container div -->
                    <div class="DVP_legend_container">
                        <div class="DVP_legend_header">Legend</div>
                        <c:forEach var="taskName" items="${schedule.tasksNames}">
                            <div class="DVP_legend_items" style="background-color: #${taskColor[taskName]}">${taskName}</div>
                        </c:forEach>
                    </div>
                    
                    
                    
                </div><!--END CONTAINER FOR ALL MONTHS -->
            
                <!-- buttons panel -->
                <div class= "DVP_dataSelect">
                    <!-- clear adress for form action tag -->
                    <sf:form method="post" commandName="message">
                        
                        <div class="DVP_dataSelect_header">
                            enter date like YYYY-MM-DD
                        </div>
                        <div class="DVP_input_errors">
                            <sf:errors path="startDay"/><br>
                            <sf:errors path="endDay"/>
                        </div>
                        <div class="DVP_inputContainer1">
                            from date<br>
                            <sf:input path="startDay" size="10" value=""/>
                        </div>
                        <div class="DVP_inputContainer1">
                            to date<br>
                            <sf:input path="endDay" size="10" value=""/>
                            
                        </div>
                        <div class="DVP_inputContainer1">
                            mode<br>
                            <select>
                                   <option disabled>days</option>
                                   <option disabled>months</option>
                              </select>
                        </div>
                        <div class="DVP_inputContainer1">
                            <br>
                            <input type="submit" value="show"/>
                            <!-- input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/-->
                        </div>
                    </sf:form>
                </div><!-- buttons panel -->
            </div><!-- end general div for month and buttons -->        
        </div><!-- end general div of this jsp page --> 
    </body>
</html>
                