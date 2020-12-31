package in.co.sunrays.proj3.controller;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.MarksheetModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Get Marksheet functionality Controller. Performs operation for Get Marksheet
 *
 * @author SunilOS
 * @version 1.0
 
 */
@ WebServlet(name="GetMarksheetCtl",urlPatterns={"/ctl/GetMarksheetCtl"})

public class GetMarksheetCtl extends BaseCtl
{

    private static Logger log = Logger.getLogger(GetMarksheetCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request)
    {

        log.debug("GetMarksheetCTL Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("rollNo")))
        {
            request.setAttribute("rollNo",PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        }
        else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.roll", "Roll Number"));
			pass = false;
		}
		


        log.debug("GetMarksheetCTL Method validate Ended");

        return pass;
    }

   
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) 
    {

        log.debug("GetMarksheetCtl Method populatedto Started");

        MarksheetDTO dto = new MarksheetDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));

        dto.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

        dto.setName(DataUtility.getString(request.getParameter("name")));

        dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));

        dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

        dto.setMaths(DataUtility.getInt(request.getParameter("maths")));

        log.debug("GetMarksheetCtl Method populatedto Ended");

        return dto;
    }

 
    /**
     * Concept of Display method
     *
     */
 
    
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Concept of Submit Method
     *
     */
    
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        log.debug("GetMarksheetCtl Method doGet Started");
        String op = DataUtility.getString(request.getParameter("operation"));

      
        MarksheetModel model = new MarksheetModel();

        MarksheetDTO dto = (MarksheetDTO) populateDTO(request);

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_GO.equalsIgnoreCase(op)) 
        {

            try {
                dto = model.findByRollNo(dto.getRollNo());
                if (dto != null)
                {
                    ServletUtility.setDTO(dto, request);
                } else 
                {
                    ServletUtility.setErrorMessage("RollNo Does Not exists",request);
                }
            } catch (ApplicationException e) 
            {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        }
        ServletUtility.forward(getView(), request, response);
        log.debug("MarksheetCtl Method doGet Ended");
    }

    @Override
    protected String getView() 
    {
        return ORSView.GET_MARKSHEET_VIEW;
    }

}
