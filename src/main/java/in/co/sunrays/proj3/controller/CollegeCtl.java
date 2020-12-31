package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CollegeModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebServlet(name="CollegeCtl",urlPatterns={"/ctl/CollegeCtl"})
public class CollegeCtl extends BaseCtl 
{

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(CollegeCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) 
    {

    	System.out.println("colgCtl/validate");
        log.debug("CollegeCtl Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) 
        {
            request.setAttribute("name",PropertyReader.getValue("error.require", "College Name"));
            pass = false;
        }
        
        else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.valid", "College Name "));
			pass = false;
		}
		

        if (DataValidator.isNull(request.getParameter("address"))) 
        {
            request.setAttribute("address",PropertyReader.getValue("error.require", "Address"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("state"))) 
        {
            request.setAttribute("state",PropertyReader.getValue("error.require", "State"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("city"))) 
        {
            request.setAttribute("city",PropertyReader.getValue("error.require", "City"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("phoneNo"))) 
        {
            request.setAttribute("phoneNo",PropertyReader.getValue("error.require", "Phone No"));
            pass = false;
        }

        log.debug("CollegeCtl Method validate Ended");

        return pass;
    }

    
    
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) 
    {
         System.out.println("clgCtl/populate");
        log.debug("CollegeCtl Method populatedto Started");

        CollegeDTO dto = new CollegeDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));

        dto.setName(DataUtility.getString(request.getParameter("name")));

        dto.setAddress(DataUtility.getString(request.getParameter("address")));

        dto.setState(DataUtility.getString(request.getParameter("state")));

        dto.setCity(DataUtility.getString(request.getParameter("city")));

        dto.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

        //populateDTO(dto, request);

        log.debug("CollegeCtl Method populatedto Ended");

        return dto;
    }

    /**
     * Contains display logic
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    
    	System.out.println("clgCtl/doGet");
        String op = DataUtility.getString(request.getParameter("operation"));

        
        CollegeModel model = new CollegeModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0) 
        {
            CollegeDTO dto;
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
    }

    /**
     * Contains Submit logics
     */
 
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {

    	
    	System.out.println("clgCtl/dopost");
        log.debug("CollegeCtl Method doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));

        CollegeModel model = new CollegeModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op))
        {
        	CollegeDTO dto = (CollegeDTO) populateDTO(request);

            try {
                	if (id > 0)
                	{
                    model.update(dto);
                    ServletUtility.setDTO(dto, request);
                    ServletUtility.setSuccessMessage("Data is successfully update",request);
 
                } else
                {
                    long pk = model.add(dto);
                   // dto.setId(pk);
                    ServletUtility.setDTO(dto, request);
                    ServletUtility.setSuccessMessage("Data is successfully saved",request);

                }
               
            } 
            catch (ApplicationException e)
            {
                e.printStackTrace();
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
            catch (DuplicateRecordException e)
            {
                ServletUtility.setDTO(dto, request);
                ServletUtility.setErrorMessage("College already exists",request);
            }

        } 
        else if (OP_DELETE.equalsIgnoreCase(op)) 
        {

            CollegeDTO dto = (CollegeDTO) populateDTO(request);
            try 
            {
                model.delete(dto);
                ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request,response);
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

            ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
            return;

        }

        ServletUtility.forward(getView(), request, response);

        log.debug("CollegeCtl Method doGet Ended");
    }

    @Override
    protected String getView()
    {    
    	
    	
    	System.out.println("clgCtl/getView");
        return ORSView.COLLEGE_VIEW;
    }

}