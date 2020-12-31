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
import in.co.sunrays.proj3.model.MarksheetModel;
import in.co.sunrays.proj3.model.StudentModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Marksheet List functionality Controller. Performs operation for list, search
 * and delete operations of Marksheet
 *
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */

/**
 * Servlet implementation class MarksheetlistCtl
 */
@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })

public class MarksheetListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(MarksheetListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		MarksheetModel model = new MarksheetModel();
		try {

			List marksheetl = model.list();
			request.setAttribute("marksheetList", marksheetl);
		} catch (ApplicationException e) {
			log.error(e);
		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		MarksheetDTO dto = new MarksheetDTO();

		dto.setId(DataUtility.getLong(request.getParameter("rollNo")));
		
		dto.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		dto.setMaths(DataUtility.getInt(request.getParameter("maths")));

		return dto;
	}
	 /**
     * Contains Display logics
     */
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		// System.out.println("run1");

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		MarksheetDTO dto = (MarksheetDTO) populateDTO(request);

		List list = null;
		MarksheetModel model = new MarksheetModel();

		try {
			list = model.search(dto, pageNo, pageSize);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetListCtl doGet End");

	}
	 /**
     * Contains Submit logics
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetListCtl doPost Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		MarksheetDTO dto = (MarksheetDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		MarksheetModel model = new MarksheetModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					MarksheetDTO deletedto = new MarksheetDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getInt(id));
						model.delete(deletedto);
						
						ServletUtility.setSuccessMessage("Data successfully delete", request);

					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			

			 else if(OP_RESET.equalsIgnoreCase(op))
            {
            ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);	
            	return;
            }


			try {
				list = model.search(dto, pageNo, pageSize);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("MarksheetListCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
