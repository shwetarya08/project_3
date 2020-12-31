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
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.CourseModel;
import in.co.sunrays.proj3.model.SubjectModel;
import in.co.sunrays.proj3.model.TimeTableModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;
/**
 * Subject List functionality Controller. Performs operation for list, search
 * and delete operations of Subject
 *
 * @author SunilOS
 * @version 1.0

 */

@WebServlet(name="SubjectListCtl",urlPatterns={"/ctl/SubjectListCtl"})
public class SubjectListCtl extends BaseCtl {

	
	 private static Logger log = Logger.getLogger(SubjectListCtl.class);

	  protected void preload(HttpServletRequest request) {
	       
    	  CourseModel cmodel = new CourseModel();
	        try {
	            List coursel = cmodel.list();
	            request.setAttribute("courseList",coursel);
	        } catch (ApplicationException e) {
	            log.error(e);
	        }}
	 
	 
	 
	 
	 
	 protected BaseDTO populatedto(HttpServletRequest request){
			
			SubjectDTO dto=new SubjectDTO();
			
			dto.setName(DataUtility.getString(request.getParameter("name")));
			
			dto.setDescription(DataUtility.getString(request.getParameter("description")));
			
			dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
			///dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));
			
	
	
	
	return dto;
	
	
	
	 }
	 
	 

	    /**
	     * Contains display logic
	     */
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 System.out.println("doget/subject");
			log.debug("SubjectListCtl doGet Start");
		        
			  List list =new ArrayList();
		        
		        int pageNo = 1;
		        
		        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		        
		        System.out.println("doget pagenation"+ pageSize+pageNo);
		        SubjectDTO dto = (SubjectDTO) populatedto(request);
		      //  String[] ids=request.getParameterValues("ids");
		        String op = DataUtility.getString(request.getParameter("operation"));
		        
		        SubjectModel model = new SubjectModel();
		        
		        try {
		        	list=model.search(dto, pageNo, pageSize);
		            //list = model.list(pageNo, pageSize);//list
		          //  ServletUtility.setList(list, request);
		           
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
		        log.debug("SubjectListCtl doGet End");
		    }
	 
	 

	    /**
	     * Contains Submit logic
	     */
	 
	 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      
		 System.out.println("dopost/subject");
		 log.debug("SubjectListCtl doPost Start");
	        
	        List list = null;
	        
	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	        
	        pageNo = (pageNo == 0) ? 1 : pageNo;
	       
	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
	      
	      SubjectDTO dto = (SubjectDTO) populatedto(request);
	        
	        String op = DataUtility.getString(request.getParameter("operation"));
	        String[] ids = request.getParameterValues("ids");
	        SubjectModel model = new SubjectModel();

	        System.out.println("page no=" + pageNo +"pagesize"+pageSize);
	        try {

	        /*    if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) 
	            
	            {*/

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
	                    ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
	                    return;
	                }
	                else if (OP_DELETE.equalsIgnoreCase(op))
	                {
	                   pageNo = 1;
	                    if (ids != null && ids.length > 0)
	                    {
	                        SubjectDTO deletedto = new SubjectDTO();
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
	                ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);	
	                	return;
	                }

	           // }
	            
	            list = model.search(dto, pageNo, pageSize);
	            
	            //request.setAttribute("courseList",model.search(dto));
	            
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
		        log.debug("SubjectListCtl doPost End");
	    }


		        
		
	 
	 
	
	@Override
	protected String getView() {
		
		return ORSView.SUBJECT_LIST_VIEW;
	}

}
