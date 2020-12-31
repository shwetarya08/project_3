package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.RoleModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Role functionality Controller. Performs operation for add, update and get
 * Role
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebServlet(name="RoleCtl",urlPatterns={"/ctl/RoleCtl"})
public class RoleCtl extends BaseCtl
{
	//private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(RoleCtl.class);


	@Override
	protected boolean validate(HttpServletRequest request)
	{
		log.debug("RoelCtl Method validate Started");
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("name")))
		{
			request.setAttribute("name", PropertyReader.getValue("error.require","Name"));
			pass=false;
		}
		
		else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.valid", " Name "));
			pass = false;
		}
		
	
		 if (DataValidator.isNull(request.getParameter("description"))) {
	            request.setAttribute("description",PropertyReader.getValue("error.require", "Description"));
	            pass = false;
	        }

	        log.debug("RoleCtl Method validate Ended");

	        return pass;
	    }
	

	    @Override
	    protected BaseDTO populateDTO(HttpServletRequest request) {

	        log.debug("RoleCtl Method populatedto Started");

	        RoleDTO dto = new RoleDTO();

	        dto.setId(DataUtility.getLong(request.getParameter("id")));
	        dto.setName(DataUtility.getString(request.getParameter("name")));
	        dto.setDescription(DataUtility.getString(request.getParameter("description")));

	       // populateDTO(dto, request);

	        log.debug("RoleCtl Method populatedto Ended");

	        return dto;
	    }

	 
	    /**
	     * Contains Display logics
	     */
	 
	    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	    {
	        log.debug("RoleCtl Method doGet Started");

	        System.out.println("In do get");

	        String op = DataUtility.getString(request.getParameter("operation"));

	       
	        RoleModel model = new RoleModel();

	        long id = DataUtility.getLong(request.getParameter("id"));
	        if (id > 0 || op != null)
	        {
	            RoleDTO dto;
	            try 
	            {
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
	        log.debug("RoleCtl Method doGetEnded");
	    }

	  
	    /**
	     * Contains Submit logics
	     */
	 
	    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	    {
	        
	    	log.debug("RoleCtl Method doGet Started");

	        System.out.println("In do get role");

	        String op = DataUtility.getString(request.getParameter("operation"));

	    
	        RoleModel model = new RoleModel();

	        long id = DataUtility.getLong(request.getParameter("id"));

	        if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op))
	        {

	            RoleDTO dto = (RoleDTO) populateDTO(request);

	            try {
	                if (id > 0)
	                {
	                    model.update(dto);
	                    ServletUtility.setDTO(dto, request);
		                ServletUtility.setSuccessMessage("Data is successfully updated",request);

	                } 
	                else 
	                {
	                    long pk = model.add(dto);
	                  //  dto.setId(pk);
	                    ServletUtility.setDTO(dto, request);
		                ServletUtility.setSuccessMessage("Data is successfully saved",request);

	                }

	               
	            } catch (ApplicationException e) 
	            {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            } 
	            catch (DuplicateRecordException e) 
	            {
	                ServletUtility.setDTO(dto, request);
	                ServletUtility.setErrorMessage("Role already exists", request);
	            }

	        }
	        else if (OP_DELETE.equalsIgnoreCase(op)) 
	        {

	            RoleDTO dto = (RoleDTO) populateDTO(request);
	            try
	            {
	                model.delete(dto);
	                ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request,response);
	                return;
	                
	            } catch (ApplicationException e) 
	            {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            }

	        } 
	        else if (OP_CANCEL.equalsIgnoreCase(op))
	        {

	            ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
	            return;

	        }

	        ServletUtility.forward(getView(), request, response);

	        log.debug("RoleCtl Method doPOst Ended");
	    }

	 
	
	@Override
	protected String getView() {
		
		return ORSView.ROLE_VIEW;
	}

}
