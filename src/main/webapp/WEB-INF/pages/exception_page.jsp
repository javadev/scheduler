<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <!-- ${pageContext.request.contextPath} returns "/Scheduller" -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/exception_page.css">
    </head>
    <body class="EP_main">
        <div class="EP_main_meaasge">
            Something wrong with application
        </div>
        
        If you entered a data, please verify is it correct.<br><br>
        
        <a href="${pageContext.request.contextPath}">return to application</a>
    
    </body>
</html>