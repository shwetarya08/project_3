<%@page import="in.co.sunrays.proj3.controller.MyProfileCtl"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<html>
<body>

<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">

        <%@ include file="Header.jsp"%>
       <!--  <script type="text/javascript" src="../js/calendar.js"></script> -->
        <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO"
            scope="request"></jsp:useBean>
        <center>
            <h1>My Profile</h1>
            
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
            <input type="hidden" name="id" value="<%=dto.getId()%>">
            <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
            

            <table align="center" style="margin-left: 40%">
                <tr>
                    <th>LoginId<font color="red">*</font></th>
                    <td><input type="text" name="login"
                        value="<%=DataUtility.getStringData(dto.getLogin())%>"readonly="readonly"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>

                <tr>
                    <th>First Name<font color="red">*</font></th>
                    <td><input type="text" name="firstName"
                        value="<%=DataUtility.getStringData(dto.getFirstName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                    <th>Last Name<font color="red">*</font></th>
                    <td><input type="text" name="lastName"
                        value="<%=DataUtility.getStringData(dto.getLastName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                    <th>Gender<font color="red">*</font></th>
                    <td>
                        <%
                            HashMap map = new HashMap();
                        map.put("","------------Select-------------") ;   
                        map.put("Male", "Male");
                            map.put("Female", "Female");

                            String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
                        %> <%=htmlList%>
                    </td>
                </tr>
                <tr>
                    <th>Mobile No<font color="red">*</font></th>
                    <td><input type="text" name="mobileNo" placeholder="Mobile No"
                        value="<%=DataUtility.getStringData(dto.getMobileNo())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>

                <tr>
                    <th>Date Of Birth<font color="red">*</font></th>
                    <td><input type="text" name="dob" id="datepicker"
                        value="<%=DataUtility.getDateString(dto.getDob())%>">
                    <!-- <a href="javascript:getCalendar(document.forms[0].dob);">
                            <img src="../img/cal.jpg" width="16" height="15" border="0"
                            alt="Calender"> -->
                    </a><font
                        color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                </tr>
                
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
                
                <tr>
                    <th></th>
                    <td colspan="2"><input type="submit" name="operation"
                        value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD %>"> &nbsp; <input type="submit"
                        name="operation" value="<%=MyProfileCtl.OP_SAVE %>"> &nbsp;</td>
                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>
