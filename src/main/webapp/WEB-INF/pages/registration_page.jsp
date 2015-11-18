<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="resources/css/registration_page.css">
        <title>Scheduler registration page</title>
    </head>
    <body class="RG_body">
        <sf:form method="post" commandName="message">
            <div class="RG_center_element">
                <div class="RG_input_errors">
                    ${error_string}
                    <sf:errors path="*"/>
                </div>
                <div class="RG_main_element">
                    <div class="RG_labels_3">new user registration</div>
                    <div class="RG_input_panel">
                        <div class="RG_labels_1">
                            user:
                        </div>
                        <div class="RG_input_field">
                            <sf:input path="username" size="15"/>
                        </div>
                        <div class="RG_labels__2">
                            must be 4-20 characters
                        </div>
                        <div class="RG_labels_1">
                            password:
                        </div>
                        <div class="RG_input_field">
                                <sf:password path="password" size="15"/>
                        </div>
                        <div class="RG_labels__2">
                            must be 4-20 characters
                        </div>
                    </div>
                    <div class="RG_button">
                        <input type="submit" value="Register"/>
                        <!-- input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/-->
                        <a href="${pageContext.request.contextPath}/login">back to login page</a>
                    </div>
                </div>
            </div>
        </sf:form>
    </body>
</html>
                