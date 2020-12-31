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
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.RoleModel;
import in.co.sunrays.proj3.model.UserModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * User List functionality Controller. Performs operation for list, search and
 * delete operations of User
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })

public class UserListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(UserListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel model = new RoleModel();
		try {
			List l = model.list();

			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
		
		UserModel umodel=new UserModel();
		try{
			List dobl=umodel.list();
			
			request.setAttribute("doblist",dobl);
			
		}catch(ApplicationException e){
			log.error(e);
		}

	}

	
	
	
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		
		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		
		dto.setPassword(DataUtility.getString(request.getParameter("password")));

		dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		
		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		System.out.println(dto.getDob()+"nviuofdhb bfrcvdf");
				return dto;
	}
	 /**
     * Contains Display logics
     */
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doget method of user list");
		log.debug("UserListCtl doGet Start");

		List list = new ArrayList();

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		// System.out.println("page no--------------> "+pageNo);

		UserDTO dto = (UserDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		
		UserModel model = new UserModel();

		try {
			list = model.search(dto, pageNo, pageSize);// page size

			System.out.println("list size is" + list.size());
			
			
			// ServletUtility.setList(list, request);

			
			
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

			

		}

		catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("UserListCtl doGet End");
	}
		
	 /**
     * Contains Submit logics
     */
 
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		log.debug("UserListCtl doPost Start");
	
		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		//System.out.println("page no=" + pageNo +"pagesize"+pageSize);
		UserDTO dto =(UserDTO)populateDTO(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));

		//System.out.println("page no  in ctl" + pageNo);

		//System.out.println("operation is" + op);

		String[] ids = request.getParameterValues("ids");
		
		UserModel model = new UserModel();

		try {

			/*
			 * if (OP_SEARCH.equalsIgnoreCase(op) ||
			 * OP_NEXT.equalsIgnoreCase(op)|| OP_PREVIOUS.equalsIgnoreCase(op))
			 * {
			 */
			if (OP_SEARCH.equalsIgnoreCase(op)) 
			{
				pageNo = 1;
			} 
			else if (OP_NEXT.equalsIgnoreCase(op))
			{
				// System.out.println("inside next");
				pageNo++;
				// System.out.println(pageNo);
			}

			else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				// System.out.println("inside previous");
				pageNo--;
			}

			else if (OP_NEW.equalsIgnoreCase(op))
			{
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;
			}
			else if (OP_DELETE.equalsIgnoreCase(op))
			{
				pageNo = 1;
				
				if (ids != null && ids.length > 0)
				{
					UserDTO deletedto = new UserDTO();
					
					for (String id : ids) 
					{
						deletedto.setId(DataUtility.getInt(id));
						model.delete(deletedto);
					
						ServletUtility.setSuccessMessage("Data is successfully delete", request);
					}
				} 
				else
				{
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}

			 else if(OP_RESET.equalsIgnoreCase(op))
             {
             ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);	
             	return;
             }


			
			
			list = model.search(dto, pageNo, pageSize);
			// ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) 
			{
				ServletUtility.setErrorMessage("No record found ", request);
			}
			
			
			ServletUtility.setDTO(dto, request);

			
			ServletUtility.setList(list, request);
			
			ServletUtility.setPageNo(pageNo, request);
			
			ServletUtility.setPageSize(pageSize, request);
			
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e)
		{
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("UserListCtl doGet End");
	}

	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}

}
