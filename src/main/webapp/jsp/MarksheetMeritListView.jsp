<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj3.dto.MarksheetDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<body>




    <%@include file="Header.jsp"%>
    <center>
        <h1>Marksheet Merit List</h1>
        
         <tr>
                    <td colspan="8">
                    <font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>

        <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
            <br>
            <table border="1"  width="80%" bordercolor="blue" >
                <tr>

                    <th>S.No.</th>
                    <th>Roll No</th>
                    <th>Name</th>
                    <th>Physics</th>
                    <th>Chemistry</th>
                    <th>Maths</th>
                    <th>Total</th>
                    <th>Percentage(%)</th>
                    

                </tr>
               
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<MarksheetDTO> it = list.iterator();

                    while (it.hasNext()) {

                        MarksheetDTO dto = it.next();
                %>
                <tr>

                    <td><%=index++%></td>
                    <td><%=dto.getRollNo()%></td>
                    <td><%=dto.getName()%></td>
                    <td><%=dto.getPhysics()%></td>
                    <td><%=dto.getChemistry()%></td>
                    <td><%=dto.getMaths()%></td>


  <td><% 
                    int marksTotal=(DataUtility.getIntegerData(dto.getPhysics())+
                    		DataUtility.getIntegerData(dto.getChemistry())+
                    				DataUtility.getIntegerData(dto.getMaths()));
                                        
                    %>
                    <%=marksTotal %></td>
                    
                    <td><%=marksTotal/3 %>%</td>

                </tr>

                <%
                    }
                %>
            </table>
          <table width=80% >
                <tr>
                <td align="center">  
               <input  type="submit" name="operation" value="<%=MarksheetMeritListCtl.OP_BACK%>">
                    </td >
                                  </tr>
         </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>">
             <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
    </center>
    <%@include file="Footer.jsp"%>
</body>
</html>
