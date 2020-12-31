package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.CollegeModel;
import in.co.sunrays.proj3.model.StudentModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebServlet(name="StudentListCtl",urlPatterns={"/ctl/StudentListCtl"})
public class StudentListCtl extends BaseCtl
{

    private static Logger log = Logger.getLogger(StudentListCtl.class);

    @Override
  	protected void preload(HttpServletRequest request) {
    	 CollegeModel model = new CollegeModel();
	        try
	        {
	            List l = model.list();
	            request.setAttribute("collegeList", l);
	        } catch (ApplicationException e) {
	            log.error(e);
	        }
  	}

    
    
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) 
    {

        StudentDTO dto = new StudentDTO();

        dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
      
       dto.setDob(DataUtility.getDate(request.getParameter("dob")));
        dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        dto.setEmail(DataUtility.getString(request.getParameter("email")));
        dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
        dto.setCollegeName(DataUtility.getString(request.getParameter("collegeName")));
       
        
        return dto;
    }

    
    
    
    /**
     * Contains Display logics
     */

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	
        log.debug("StudentListCtl doGet Start");
        //List list = null;
        List list=new ArrayList();
        int pageNo = 1;
        

        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        StudentDTO dto = (StudentDTO) populateDTO(request);

       
        String op = DataUtility.getString(request.getParameter("operation"));
        StudentModel model = new StudentModel();
        try
        {
            list = model.search(dto, pageNo, pageSize);
         ServletUtility.setList(list, request);
            if (list == null || list.size() == 0) 
            {
                ServletUtility.setErrorMessage("No record found ", request);
            }
           
            ServletUtility.setList(list, request);

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        }
        catch (ApplicationException e) 
        {
     
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log.debug("StudentListCtl doGet End");
    }

	  /**
     * Contains Submit logics
     */
    
    
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        
    	log.debug("StudentListCtl doPost Start");
        List list = null;
        
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        StudentDTO dto = (StudentDTO) populateDTO(request);
        String op = DataUtility.getString(request.getParameter("operation"));
       
        String[] ids = request.getParameterValues("ids");
        
        StudentModel model = new StudentModel();

        try {

           /* if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)|| "Previous".equalsIgnoreCase(op)||)
            {*/

                if (OP_SEARCH.equalsIgnoreCase(op)) 
                {
                    pageNo = 1;
                    System.out.println("student search");
                }
                else if (OP_NEXT.equalsIgnoreCase(op))
                {
                    pageNo++;
                }
                else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1)
                {
                    pageNo--;
                }
                
                else if (OP_NEW.equalsIgnoreCase(op)) 
                {
                    ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
                    return;
                }
                else if (OP_DELETE.equalsIgnoreCase(op))
                {
                    pageNo = 1;
                    if (ids != null && ids.length > 0)
                    {
                        StudentDTO deletedto = new StudentDTO();
                        for (String id : ids) 
                        {
                            deletedto.setId(DataUtility.getInt(id));
                            model.delete(deletedto);
                            
                            ServletUtility.setSuccessMessage("Data successfully delete", request);

                        }
                    } 
                    else 
                    {
                        ServletUtility.setErrorMessage("Select at least one record", request);
                    }
                }

                else if(OP_RESET.equalsIgnoreCase(op))
                {
                ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);	
                	return;
                }


                
           // }
            list = model.search(dto, pageNo, pageSize);
            ServletUtility.setList(list, request);
            
            if (list == null || list.size() == 0) 
            {
                ServletUtility.setErrorMessage("No record found ", request);
            }
          
          //---
    		ServletUtility.setDTO(dto, request);

            
            ServletUtility.setList(list, request);

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } 
        catch (ApplicationException e)
        {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log.debug("StudentListCtl doGet End");
    }

    
    
    @Override
    protected String getView()
    {
        return ORSView.STUDENT_LIST_VIEW;
    }
}