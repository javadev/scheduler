<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page import="org.joda.time.LocalDate" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tasks_view_page.css">
        
        <!-- css for both panels and body -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/upper_panel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/side_panel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/body_common.css">
    </head>
    <body class="Body_body">
        <%@ include file="upper_panel.jsp" %>
        <%@ include file="side_panel.jsp" %>
    
        <div class="TR_main">
            <div class="TR_main_label">
                tasks review
            </div>
            <div class="TR_big_Container">
                <div class="TR_input_container">
                    sort by: <select name="by1"></select>
                </div>
                <div class="TR_label_1">
                    list of existing tasks
                </div>
                <div class="TR_table_container">
                    <table border="0" cellspacing="1" bgcolor="#000000" cellpadding="2">
                        <tr bgcolor="#FFFFFF">
                            <th width="130px">title</th>
                            <th width="100px">description</th>
                            <th width="50px">type</th>
                            <th width="20px">active</th>
                            <th width="12px">necessary hour(s)</th>
                            <th width="80px">start date</th>
                            <th width="80px">end date</th>
                            <th width="175px">active days</th>
                            <th width="260px">active hours</th>
                            <th>edit</th>
                            <th>delete</th>
                        </tr>
                        <c:forEach var="repr" items="${taskRepr}">
                            <tr bgcolor="#FFFFFF">
                                <td>${repr.title}</td>
                                <td title="${repr.description}">${fn:substring(repr.description, 1, 14)}...</td>
                                <td>${repr.type}</td>
                                <td>${repr.isActive}</td>
                                <td>${repr.necessaryTime}</td>
                                <td>${type == 'FlexibleTerm' ? repr.startDate : LocalDate(repr.interval.startMillis)}</td>
                                <td>${LocalDate(repr.interval.endMillis)}</td>
                                <td>
                                    <%-- If it isn't first active day in string and if next day is active there will be comma --%>
                                
                                    <c:set var="first" value ="true"/>

                                    <c:if test="${repr.getActiveDayAt(1)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveDayAt(1) == true ? 'Mo' : ''}<c:if test="${!first and repr.getActiveDayAt(2)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveDayAt(2)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveDayAt(2) == true ? 'Tu' : ''}<c:if test="${!first and repr.getActiveDayAt(3)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveDayAt(3)}"><c:set var="first" value ="false"/></c:if>
                                    ${repr.getActiveDayAt(3) == true ? 'We' : ''}<c:if test="${!first and repr.getActiveDayAt(4)}">,</c:if>
                                     
                                    <c:if test="${repr.getActiveDayAt(4)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveDayAt(4) == true ? 'Th' : ''}<c:if test="${!first and repr.getActiveDayAt(5)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveDayAt(5)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveDayAt(5) == true ? 'Fr' : ''}<c:if test="${!first and repr.getActiveDayAt(6)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveDayAt(6)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveDayAt(6) == true ? 'Sa' : ''}<c:if test="${!first and repr.getActiveDayAt(7)}">,</c:if>
                                    
                                    ${repr.getActiveDayAt(7) == true ? 'Su' : ''}                        
                                </td>
                                <td>
                                    <%-- If it isn't firts active hour in string and if next hour is active there will be comma --%>
                                    <c:set var="first" value ="true"/>
                                    
                                    <c:if test="${repr.getActiveHourAt(0)}"><c:set var="first" value ="false"/></c:if>     
                                    ${repr.getActiveHourAt(0) == true ? '0' : ''}<c:if test="${!first and repr.getActiveHourAt(1)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(1)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(1) == true ? '1' : ''}<c:if test="${!first and repr.getActiveHourAt(2)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(2)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(2) == true ? '2' : ''}<c:if test="${!first and repr.getActiveHourAt(3)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(3)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(3) == true ? '3' : ''}<c:if test="${!first and repr.getActiveHourAt(4)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(4)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(4) == true ? '4' : ''}<c:if test="${!first and repr.getActiveHourAt(5)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(5)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(5) == true ? '5' : ''}<c:if test="${!first and repr.getActiveHourAt(6)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(6)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(6) == true ? '6' : ''}<c:if test="${!first and repr.getActiveHourAt(7)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(7)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(7) == true ? '7' : ''}<c:if test="${!first and repr.getActiveHourAt(8)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(8)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(8) == true ? '8' : ''}<c:if test="${!first and repr.getActiveHourAt(9)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(9)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(9) == true ? '9' : ''}<c:if test="${!first and repr.getActiveHourAt(10)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(10)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(10) == true ? '10' : ''}<c:if test="${!first and repr.getActiveHourAt(11)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(11)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(11) == true ? '11' : ''}<c:if test="${!first and repr.getActiveHourAt(12)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(12)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(12) == true ? '12' : ''}<c:if test="${!first and repr.getActiveHourAt(13)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(13)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(13) == true ? '13' : ''}<c:if test="${!first and repr.getActiveHourAt(14)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(14)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(14) == true ? '14' : ''}<c:if test="${!first and repr.getActiveHourAt(15)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(15)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(15) == true ? '15' : ''}<c:if test="${!first and repr.getActiveHourAt(16)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(16)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(16) == true ? '16' : ''}<c:if test="${!first and repr.getActiveHourAt(17)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(17)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(17) == true ? '17' : ''}<c:if test="${!first and repr.getActiveHourAt(18)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(18)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(18) == true ? '18' : ''}<c:if test="${!first and repr.getActiveHourAt(19)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(19)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(19) == true ? '19' : ''}<c:if test="${!first and repr.getActiveHourAt(20)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(20)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(20) == true ? '20' : ''}<c:if test="${!first and repr.getActiveHourAt(21)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(21)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(21) == true ? '21' : ''}<c:if test="${!first and repr.getActiveHourAt(22)}">,</c:if>
                                    
                                    <c:if test="${repr.getActiveHourAt(22)}"><c:set var="first" value ="false"/></c:if> 
                                    ${repr.getActiveHourAt(22) == true ? '22' : ''}<c:if test="${!first and repr.getActiveHourAt(23)}">,</c:if>
                                    
                                    ${repr.getActiveHourAt(23) == true ? '23' : ''}
                                </td>
                                <td><a href="${pageContext.request.contextPath}/app/task/edit/?task_id=${repr.id}">edit</a></td>
                                <td><a href="${pageContext.request.contextPath}/app/task/delete/?task_id=${repr.id}">delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div><!-- div table -->
            </div><!-- big container -->
            <div class = "buttons">
                <form action="${pageContext.request.contextPath}/app/task/edit" method="GET">
                    <input type="submit" value="add new task">
                    <!-- input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/-->
                </form>
            </div>
        </div>    
    </body>
</html>
                