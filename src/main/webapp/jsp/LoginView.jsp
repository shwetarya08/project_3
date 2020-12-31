<%@page import="in.co.sunrays.proj3.controller.LoginCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<html>
<body>
    <form action="<%=ORSView.LOGIN_CTL%>" method="post">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO" scope="request">
        </jsp:useBean>

        <center>
        <div style="height:90px!important">
            <h1>Login</h1>
            
               <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2> 
 <%-- <H2>
                <font color="red"> <%=DataUtility.getStringData(request.getAttribute("message"))%>
                </font>
            </H2> 
               --%>
 
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            </div>
         
              
              <input type="hidden" name="id" value="<%=dto.getId()%>">
              <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
              <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
              <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
              <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<%String uri=(String)request.getAttribute("uri"); %>
<input type="hidden" name="uri" value="<%=uri%>">
            <table align="center" style="margin-left: 40%">
            
                <tr>
                    <th>LoginId<font color="red">*</font></th>
                    <td>
                    <input type="text" name="login" placeholder="Enter Login Id" size=30 value="<%=DataUtility.getStringData(dto.getLogin())%>">
      <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%>
      </font></td>
                </tr>
                <tr>
                    <th>Password<font color="red">*</font></th>
                    <td>
                    <input type="password" name="password" placeholder="Enter password" size=30 value="<%=DataUtility.getStringData(dto.getPassword())%>">
                    <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%>
                    </font>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td colspan="2">
                    <input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN %>"> &nbsp;
                     <input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP %>" > &nbsp;</td>
                </tr>
                <tr>
                <th></th>
                <td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b><font color="blue">Forget my password</b></a>&nbsp;</td>
            </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>

