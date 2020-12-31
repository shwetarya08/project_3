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
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.CollegeModel;
import in.co.sunrays.proj3.model.CourseModel;
import in.co.sunrays.proj3.model.SubjectModel;
import in.co.sunrays.proj3.model.TimeTableModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;


/**
 * TimeTable List functionality Controller. Performs operation for list, search
 * and delete operations of TimeTable
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebServlet(name="TimeTableListCtl",urlPatterns={"/ctl/TimeTableListCtl"})
public class TimeTableListCtl extends BaseCtl {
	
	 private static Logger log = Logger.getLogger(TimeTableListCtl.class);

	
	 @Override
	    protected void preload(HttpServletRequest request) 
	  {
		 CourseModel cmodel = new CourseModel();
	        try 
	        {
	            List l = cmodel.list();
	            request.setAttribute("courseList", l);
	        }
	        catch (ApplicationException e) {
	            log.error(e);
	        }
	        
	        
   SubjectModel smodel = new SubjectModel();
	        
	        try 
	        {
	            List slist = smodel.list();
	            request.setAttribute("subjectList",slist);
	        } 
	        catch (ApplicationException e) {
	            log.error(e);
	        }
	        
	        
	        TimeTableModel tmodel=new TimeTableModel();
	        
	        try{
	        	
	        	List exam=tmodel.list();
	        	
	        	request.setAttribute("exam", exam);
	        }

	        catch(ApplicationException e){
	        	log.error(e);
	        }
	  }
	 
	 
	 
	 protected BaseDTO populatedto(HttpServletRequest request){
		
		TimeTableDTO dto=new TimeTableDTO();
		
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		//dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		dto.setSemester(DataUtility.getString(request.getParameter("semester")));
		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		dto.setTime(DataUtility.getString(request.getParameter("time")));
		
		return dto;
		
	}
	 /**
	     * Contains display logic
	     */
	    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
		log.debug("TimeTableListCtl doGet Start");
	        
		  List list = new ArrayList();
	        
	        int pageNo = 1;
	        
	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
	        
	        TimeTableDTO dto = (TimeTableDTO) populatedto(request);
	        
	        String op = DataUtility.getString(request.getParameter("operation"));
	        
	        TimeTableModel model = new TimeTableModel();
	        
	        try {
	           // list = model.list(pageNo, pageSize);//cantn not ne null
	            
	        	list=model.search(dto, pageNo, pageSize);
	        	//ServletUtility.setList(list, request);
	           
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
		        }
	        log.debug("TimeTableListCtl doGet End");
	    }
	        
	/**
     * Contains Submit logic
     */
       
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      
		 log.debug("TimeTableListCtl doPost Start");
	        
	        List list = null;
	        
	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	        
	        pageNo = (pageNo == 0) ? 1 : pageNo;
	       
	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
	                .getValue("page.size")) : pageSize;
	      
	        TimeTableDTO dto = (TimeTableDTO) populatedto(request);
	        
	        String op = DataUtility.getString(request.getParameter("operation"));

	        String[] ids = request.getParameterValues("ids");
	        
	        TimeTableModel model = new TimeTableModel();

	        try {

	          /*  if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
	                    || "Previous".equalsIgnoreCase(op)) 
	            
	            {
*/
	                if (OP_SEARCH.equalsIgnoreCase(op)) 
		                {
		                    pageNo = 1;
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
	                    ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
	                    return;
	                }
	                
	                else if (OP_DELETE.equalsIgnoreCase(op))
	                {
	                    pageNo = 1;
	                    if (ids != null && ids.length > 0)
	                    {
	                        TimeTableDTO deletedto = new TimeTableDTO();
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
	                ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);	
	                	return;
	                }


	             

	            //}
	            
	            list = model.search(dto, pageNo, pageSize);
	            
	           // request.setAttribute("courseList",model.search(dto));
	            
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
		        }
		        log.debug("RoleListCtl doPost End");
	    }

	
	
	@Override
	protected String getView() {


		return ORSView.TIMETABLE_LIST_VIEW;
	}

}
