package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CourseModel;
import in.co.sunrays.proj3.model.SubjectModel;
import in.co.sunrays.proj3.model.TimeTableModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * TimeTable functionality Controller. Performs operation for add, update, delete
 * and get TimeTable
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebServlet(name="TimeTableCtl",urlPatterns={"/ctl/TimeTableCtl"})
public class TimeTableCtl extends BaseCtl 
{
	
private static final long serialVersionUID=1L;
	
	private static Logger log=Logger.getLogger(TimeTableCtl.class);
	
	 @Override
	    protected void preload(HttpServletRequest request) {
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

	        
	    }
		
		protected boolean validate(HttpServletRequest request) 
		{

            log.debug("TimetableCtl Method validate Started");

            boolean pass = true;
            
            if(DataValidator.isNull(request.getParameter("courseId")))
            	{
            	request.setAttribute("courseId", PropertyReader.getValue("error.require","Course Name"));
            	
            	   pass = false;
            	}
                      if(DataValidator.isNull(request.getParameter("subjectId")))
            	
            	{
            	request.setAttribute("subjectId", PropertyReader.getValue("error.require","Subject Name"));
            	
            	   pass = false;
            	}
        
        
            if(DataValidator.isNull(request.getParameter("semester")))
            	
            	{
            	request.setAttribute("semester", PropertyReader.getValue("error.require","Semester"));
            	
            	   pass = false;
            	}
            
            if(DataValidator.isNull(request.getParameter("examDate")))
            	
            	{
            	request.setAttribute("examDate", PropertyReader.getValue("error.require","Exam Date"));
            	
            	   pass = false;
            	}
            
            if(DataValidator.isNull(request.getParameter("time")))
            	
            	{
            	request.setAttribute("time", PropertyReader.getValue("error.require","Time"));
            	
            	   pass = false;
            	}
            
            log.debug("TimeTableCtl Method validate Ended");

            return pass;
        }
            
	


		protected BaseDTO populatedto(HttpServletRequest request) {
		
		    log.debug("TimeTableCtl Method populatedto Started");
		
		    TimeTableDTO dto = new TimeTableDTO();
		
		    dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		
		   /* dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));*/
		
		    dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		
		    /*dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));*/
		
		    dto.setSemester(DataUtility.getString(request.getParameter("semester")));
		
		   dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		
		    dto.setTime(DataUtility.getString(request.getParameter("time")));
		
		    dto.setId(DataUtility.getLong(request.getParameter("id")));
		 
		   // populateDTO(dto, request);
		
		    log.debug("TimeTableCtl Method populatedto Ended");
		
		    return dto;
		}
		
	
		 /**
	     * Contains display logic
	     */
	   
		protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	           
	        	log.debug("TimeTableCtl Method doGet Started");
	            
	        	String op = DataUtility.getString(request.getParameter("operation"));
	            // get model
	            
	        	TimeTableModel model = new TimeTableModel();
	            
	        	long id = DataUtility.getLong(request.getParameter("id"));
	        		        	
	        	if (id > 0 || op != null)
	        	{
	               
	        		//System.out.println("in id > 0  condition");
	                
	        		TimeTableDTO dto;
	        		// System.out.println("do get 1");

	        		try {
	                    dto = model.findByPK(id);
	                    ServletUtility.setDTO(dto, request);
	                    } 
	        		
	        		catch (ApplicationException e) 
		        		{
	        			 

		                    log.error(e);
		                    ServletUtility.handleException(e, request, response);
		                    return;
		                }
	            	}
	            
	        	 		            ServletUtility.forward(getView(), request, response);
		            log.debug("TimeTableCtl Method doGet Ended");
		        }
	        
		 /**
	     * Contains Submit logic
	     */
	     
		
		  protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	           
	        	log.debug("TimeTableCtl Method doPost Started");
	            
	        	String op = DataUtility.getString(request.getParameter("operation"));
	            // get model
	            
	        	long id = DataUtility.getLong(request.getParameter("id"));

	        	TimeTableModel model = new TimeTableModel();
	            
	        		            
	        	if (OP_SAVE.equalsIgnoreCase(op) ||OP_UPDATE.equalsIgnoreCase(op)) 
	        	{
	               
	        		TimeTableDTO dto = (TimeTableDTO) populatedto(request);

	        		
	                try {
	                    if (id > 0) 
		                    {
	                    	
		                        //model.update(dto);
		                        ServletUtility.setDTO(dto, request);
			                    ServletUtility.setErrorMessage("TimeTable already exist",request);
			                   
		                    }
	                    
	                    else 
		                    {
		                        long pk = model.add(dto);
		                       // dto.setId(pk);
		                        ServletUtility.setDTO(dto, request);
			                    ServletUtility.setSuccessMessage("Data is successfully saved",request);
			                   
		                    }
			                    
	                	} 
	                
	                catch (ApplicationException e) 
		                {
		                    log.error(e);
		                    ServletUtility.handleException(e, request, response);
		                    return;
		                } 
	                
	                catch (DuplicateRecordException e) 
		                {
		                    ServletUtility.setDTO(dto, request);
		                    ServletUtility.setErrorMessage("TimeTable already exists",request);
		                } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		} 
	        
	        	else if (OP_DELETE.equalsIgnoreCase(op))
		        	{
	
		                TimeTableDTO dto = (TimeTableDTO) populatedto(request);
		                try 
			                {
			                    model.delete(dto);
			                    ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request,response);
			                    return;
			                } 
			                
		                catch (ApplicationException e) 
		                
			                {
		                    log.error(e);
		                    ServletUtility.handleException(e, request, response);
		                    return;
			                }

		        	}
	        	
	        			else if (OP_CANCEL.equalsIgnoreCase(op)) 
		        			{
			
				                ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				                return;
		        			}
					            ServletUtility.forward(getView(), request, response);
				
					            log.debug("TimeTableCtl Method doPostEnded");
					        }


		@Override
		protected String getView() {
			
			return ORSView.TIMETABLE_VIEW;
		}


	
	

}
