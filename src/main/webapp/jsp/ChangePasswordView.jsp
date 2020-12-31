<%@page import="in.co.sunrays.proj3.controller.ChangePasswordCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<html>
<body>
    <form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">
        
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO"
            scope="request"></jsp:useBean>

        <center>
            <h1>Change Password</h1>


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
                    <th>Old Password<font color="red">*</font></th>
                    <td><input type="password" name="oldPassword" placeholder="old password"
                        value=<%=DataUtility.getString(request.getParameter("oldPassword") == null ? ""
                            : DataUtility.getString(request.getParameter("oldPassword")))%>><font
                        color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
                </tr>

                <tr>
                    <th>New Password<font color="red">*</font></th>
                    <td><input type="password" name="newPassword" placeholder="new password"
                        value=<%=DataUtility
                    .getString(request.getParameter("newPassword") == null ? ""
                            : DataUtility.getString(request
                                    .getParameter("newPassword")))%>><font
                        color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
                </tr>

                <tr>
                    <th>Confirm Password<font color="red">*</font></th>
                    <td><input type="password" name="confirmPassword" placeholder="confirm password"
                        value=<%=DataUtility.getString(request
                    .getParameter("confirmPassword") == null ? "" : DataUtility
                    .getString(request.getParameter("confirmPassword")))%>><font
                        color="red"> <%=ServletUtility
                    .getErrorMessage("confirmPassword", request)%></font></td>
                </tr>

                <tr>
                    <th></th>
                    <td colspan="2"><input type="submit" name="operation"
                        value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE %>"><input type="submit"
                        name="operation" value="<%= ChangePasswordCtl.OP_SAVE%>"> &nbsp;</td>
                </tr>

            </table>
            <H3>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H3>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>