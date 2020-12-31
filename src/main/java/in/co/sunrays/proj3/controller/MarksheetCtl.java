package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.MarksheetModel;
import in.co.sunrays.proj3.model.StudentModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;


/**
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet
 *
 * @author SunilOS
 * @version 1.0

 */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		StudentModel model = new StudentModel();
		try {

			List studentl = model.list();
			request.setAttribute("studentList", studentl);
		}

		catch (ApplicationException e) {
			log.error(e);
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("MarksheetCtl Method validate Started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;

		}
		
		if (DataValidator.isNotNull(request.getParameter("rollNo"))
				&& !DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.roll", "Roll No"));
			pass = false;
		}
		
		
		
		  if(DataValidator.isNull(request.getParameter("physics"))){
	        	request.setAttribute("physics", PropertyReader.getValue("error.require","Physics Marks"));
	        	 pass=false;
	         }  
			
		  if (DataValidator.isNotNull(request.getParameter("physics"))
					&& !DataValidator.isInteger(request.getParameter("physics")))
			{
				request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			}

			if (DataUtility.getInt(request.getParameter("physics")) > 100) 
			{
				request.setAttribute("physics", "Marks can not be greater than 100");
				pass = false;
			}
	         
			if(DataValidator.isNull(request.getParameter("chemistry"))){
	        	  request.setAttribute("chemistry", PropertyReader.getValue("error.require","Chemistry Marks"));
	        	  pass=false;
	          }
			
			if (DataValidator.isNotNull(request.getParameter("chemistry"))
					&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
				request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			}

			if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
				request.setAttribute("chemistry", "Marks can not be greater than 100");
				pass = false;
			}
			
			 if(DataValidator.isNull(request.getParameter("maths"))){
	       	  request.setAttribute("maths", PropertyReader.getValue("error.require","Maths Marks"));
	       	  pass=false;
	         }
			if (DataValidator.isNotNull(request.getParameter("maths"))
					&& !DataValidator.isInteger(request.getParameter("maths"))) {
				request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
				pass = false;
			}

			if (DataUtility.getInt(request.getParameter("maths")) > 100) {
				request.setAttribute("maths", "Marks can not be greater than 100");
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("studentId"))) {
				request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
				pass = false;
			}


		log.debug("MarksheetCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("MarksheetCtl Method populateDTO Started");

		MarksheetDTO dto = new MarksheetDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		
		if(DataValidator.isNotNull(request.getParameter("physics"))&&DataValidator.isInteger(request.getParameter("physics")))
		{
		dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		}
		else
		{
			dto.setPhysicsS(DataUtility.getString(request.getParameter("physics")));
		}	
		
		if(DataValidator.isNotNull(request.getParameter("chemistry"))&&DataValidator.isInteger(request.getParameter("chemistry")))
		{
		dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		}
		else
		{
			dto.setChemistryS(DataUtility.getString(request.getParameter("chemistry")));
		}
		if(DataValidator.isNotNull(request.getParameter("maths"))&&DataValidator.isInteger(request.getParameter("maths")))
		{
		dto.setMaths(DataUtility.getInt(request.getParameter("maths")));
		}
		else
		{
			dto.setMathsS(DataUtility.getString(request.getParameter("maths")));
		}
		dto.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

		// populateDTO(dto, request);

		System.out.println("Population done");

		log.debug("MarksheetCtl Method populateDTO Ended");

		return dto;
	}
	
	
	   /**
     * Contains Display logics
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MarksheetCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			MarksheetDTO dto;

			try {

				dto = model.findByPK(id);
				ServletUtility.setDTO(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("MarksheetCtl Method doGet Ended");

	}

	 /**
     * Contains Submit logics
     */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);

				}
				
				
				else {
					long pk = model.add(dto);
					//dto.setId(pk);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);

				}
				
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
			System.out.println("in try");
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				System.out.println("in try");
				return;
			} catch (ApplicationException e) {
				System.out.println("in catch");
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("MarksheetCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.MARKSHEET_VIEW;
	}

}
