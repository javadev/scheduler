<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/task_edit_create_page.css">
        
        <!-- css for both panels and body -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/upper_panel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/side_panel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/body_common.css">
    </head>
    <body class="Body_body">
    
        <%@ include file="upper_panel.jsp" %>
        <%@ include file="side_panel.jsp" %>
        
        <div class="TS_main">
            <sf:form method="post" commandName="tSPageMessage">
                <div class="TS_main_label">
                    ${TS_page_header}
                </div>
                <div class="TS_input_errors">
                            <sf:errors path="*"/><br>
                </div>
                <div class = "TS_big_Container">
                    <div class="TS_labels">
                        task name:
                    </div>
                    <div class="TS_inputContainer_1">
                        <sf:input path="taskName" size="28"/>
                    </div>
                    <div class="TS_labels">
                        choose type of task:
                    </div>
                    <div class="TS_inputContainer_1">
                        <sf:radiobuttons path="typeOfTask" items="${typeOftaskArary}" style="width: 90px"/>
                    </div>
                    <div class="TS_labels">
                        enter date like YYYY-MM-DD:
                    </div>
                    <div class= "TS_inputContainer_1">
                        <div class="TS_inputContainer_2">
                            <div class="TS_label3">
                                start date:
                            </div>
                            <div class="TS_inputContainer_3">
                                <sf:input path="startDay" size="10"/>
                            </div>
                        </div>
                        <div class="TS_inputContainer_2">
                            <div class="TS_label3">
                                end date
                            </div>
                            <div class="TS_inputContainer_3">
                                <sf:input path="endDay" size="10"/>
                            </div>
                        </div>
                    </div>
                    <div class="TS_labels">
                        choose active days for task:
                    </div>
                    <div class= "TS_inputContainer_1">
                        <sf:checkbox path="monday" value="true" />Monday<Br>
                        <sf:checkbox path="tuesday" value="true" />Tuesday<Br>
                        <sf:checkbox path="wednesday" value="true" />Wednesday<Br>
                        <sf:checkbox path="thursday" value="true" />Thursday<Br>
                        <sf:checkbox path="friday" value="true" />Friday<Br>
                        <sf:checkbox path="saturday" value="true" />Saturday<Br>
                        <sf:checkbox path="sunday" value="true" />Sunday<Br>
                    </div>
                </div>
                <div class = "TS_big_Container">
                    <div class="TS_labels">
                        is task active:
                    </div>
                    <div class= "TS_inputContainer_1">
                        <sf:checkbox path="isActive" value="flexible" />is active<Br>
                    </div>
                    <div class="TS_labels">
                        necessary hours for task:
                    </div>
                    <div class= "TS_inputContainer_1">
                        <sf:input path="necessaryHours" size="28"/>
                    </div>
                    <div class="TS_labels">
                        choose active hours for task:
                    </div>
                    <div class= "TS_inputContainer_1">
                        <div class="TS_inputContainer_2">
                            <sf:checkbox path="hour_0" value="true"/>00:00-01:00<Br>
                            <sf:checkbox path="hour_1" value="true"/>01:00-02:00<Br>
                            <sf:checkbox path="hour_2" value="true"/>02:00-03:00<Br>
                            <sf:checkbox path="hour_3" value="true"/>03:00-04:00<Br>
                            <sf:checkbox path="hour_4" value="true"/>04:00-05:00<Br>
                            <sf:checkbox path="hour_5" value="true"/>05:00-06:00<Br>
                            <sf:checkbox path="hour_6" value="true"/>06:00-07:00<Br>
                            <sf:checkbox path="hour_7" value="true"/>07:00-08:00<Br>
                            <sf:checkbox path="hour_8" value="true"/>08:00-09:00<Br>
                            <sf:checkbox path="hour_9" value="true"/>09:00-10:00<Br>
                            <sf:checkbox path="hour_10" value="true"/>10:00-11:00<Br>
                            <sf:checkbox path="hour_11" value="true"/>11:00-12:00<Br>
                        </div>
                        <div class="TS_inputContainer_2">
                            <sf:checkbox path="hour_12" value="true"/>12:00-13:00<Br>
                            <sf:checkbox path="hour_13" value="true"/>13:00-14:00<Br>
                            <sf:checkbox path="hour_14" value="true"/>14:00-15:00<Br>
                            <sf:checkbox path="hour_15" value="true"/>15:00-16:00<Br>
                            <sf:checkbox path="hour_16" value="true"/>16:00-17:00<Br>
                            <sf:checkbox path="hour_17" value="true"/>17:00-18:00<Br>
                            <sf:checkbox path="hour_18" value="true"/>18:00-19:00<Br>
                            <sf:checkbox path="hour_19" value="true"/>19:00-20:00<Br>
                            <sf:checkbox path="hour_20" value="true"/>20:00-21:00<Br>
                            <sf:checkbox path="hour_21" value="true"/>21:00-22:00<Br>
                            <sf:checkbox path="hour_22" value="true"/>22:00-23:00<Br>
                            <sf:checkbox path="hour_23" value="true"/>23:00-00:00<Br>
                        </div>
                    </div>
                </div>
                <div class = "TS_big_Container">
                    <div class="TS_labels">
                        descriptions:
                    </div>
                    <div class= "TS_inputContainer_1">
                        <sf:textarea path="description" class ="TS_textarea"/>
                    </div>
                </div>
                <div class="TS_buttons">
                        <input type="submit" value="save">
                </div>
            </sf:form>
        </div><!-- end main class div -->
    </body>
</html>
                