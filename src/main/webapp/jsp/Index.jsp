<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="Header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1 align="Center">
        <img src="..img/.jpg" width="300" height="300" border="0">
    </h1>

    <h1 align="Center">
        <font size="10px" color="red">
        <a href="">Home</a>&nbsp;&nbsp;
        <a href="<%=ORSView.LOGIN_CTL%>"></a>&nbsp;&nbsp;
        
        <form action="">
        <h1 align="center">
        <font size="10px" color="red">Welcome to ORS</font>
        </h1>
        <br><br>
        
        <a href="<%=ORSView.MARKSHEET_LIST_VIEW%>">
        <b>Click Here</b></a>
              
        
       <%--  <a href="<%=ORSView.WELCOME_CTL%>">Online Result System</font>
             <br> <br> --%>
    </h1>
<%@ include file="Footer.jsp" %>
  </form>


</body>
</html>