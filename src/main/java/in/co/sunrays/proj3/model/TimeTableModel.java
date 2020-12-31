package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;


/**
 * JDBC Implementation of TimeTable Model
 *
 * @author SunilOS
 * @version 1.0
 *
 */

public class TimeTableModel {

	private static Logger log = Logger.getLogger(TimeTableModel.class);

	  /**
     * Find next PK of TimeTable
     *
     * @throws DatabaseException
     */
	public Integer nextPK() throws DatabaseException {
		log.debug("Timetable Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable Model nextPK End");
		return pk + 1;
	}

	  /**
     * Add a TimeTable
     *
     * @param dto
     * @throws Exception
     *
     */
	public long add(TimeTableDTO dto) throws Exception {

		log.debug("timetable Model add Started");

		Connection conn = null;
		int pk = 0;

		CourseModel coModel = new CourseModel();
		CourseDTO coursedto = coModel.findByPK(dto.getCourseId());
		dto.setCourseName(coursedto.getName());

		SubjectModel sModel = new SubjectModel();
		SubjectDTO subjectdto = sModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getName());

		TimeTableModel tmodel=new TimeTableModel();
		TimeTableDTO tdto=new TimeTableDTO();
		dto.setSemester(tdto.getSemester());
		

		  TimeTableDTO dton=findBySubCourseName(dto.getSubjectName(),dto.getCourseName(),dto.getSemester());
		 
		  if( dton !=null) {
		  
		  throw new DuplicateRecordException(" TimeTable already exist");
		  
		  }
		 

		try {

			conn = JDBCDataSource.getConnection();

			pk = nextPK();

			System.out.println(pk + "in ModelJDBC");

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setLong(2, dto.getCourseId());
			pstmt.setString(3, dto.getCourseName());

			pstmt.setLong(4, dto.getSubjectId());
			pstmt.setString(5, dto.getSubjectName());
			pstmt.setString(6, dto.getSemester());
			pstmt.setDate(7, new java.sql.Date(dto.getExamDate().getTime()));
			pstmt.setString(8, dto.getTime());
			pstmt.setString(9, dto.getCreatedBy());
			pstmt.setString(10, dto.getModifiedBy());
			pstmt.setTimestamp(11, dto.getCreatedDatetime());
			pstmt.setTimestamp(12, dto.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();

			log.error("Database Exception.." + e);

			try {
				conn.rollback();

			} catch (Exception ex) {

				ex.printStackTrace();

				throw new ApplicationException("Exception: add rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception : Exception in add TimeTable");
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("TimeTable Model add End");
		System.out.println("yippi..3");
		return pk;
	}

	 /**
     * Delete a TimeTable
     *
     * @param dto
     * @throws ApplicationException
     */
	public void delete(TimeTableDTO dto) throws ApplicationException {

		log.debug("TimeTable Model delete Started");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// begin transaction

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");

			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			conn.commit();// end transaction
			pstmt.close();

		}

		catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);

			try {
				conn.rollback();

			}

			catch (Exception ex) {

				throw new ApplicationException("Execption: Delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception: Exception in delete TimeTable");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Timetable Model delete Started");

	}

	/*
	 * public TimeTableDTO findByLogin(String login)throws
	 * ApplicationException{
	 * 
	 * //log.debug("Model findByLogin Started");
	 * 
	 * StringBuffer sql=new
	 * StringBuffer("SELECT * FROM ST_TIMETABLE WHERE LOGIN=?");
	 * 
	 * TimeTableDTO dto=null; Connection conn=null;
	 * System.out.println("sql"+sql);
	 * 
	 * try { conn=JDBCDataSource.getConnection(); PreparedStatement
	 * pstmt=conn.prepareStatement(sql.toString()); pstmt.setString(1, login);
	 * 
	 * ResultSet rs=pstmt.executeQuery();
	 * 
	 * while(rs.next()) { dto=new TimeTableDTO();
	 * dto.setCourseId(rs.getLong(1)); dto.setCourseName(rs.getString(3));
	 * dto.setSubjectId(rs.getLong(4)); dto.setSubjectName(rs.getString(5));
	 * dto.setSemester(rs.getString(6)); dto.setExamDate(rs.getDate(7));
	 * dto.setTime(rs.getString(8));
	 * 
	 * 
	 * 
	 * } rs.close();
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * //log.error("Database Exception.." e); throw new
	 * ApplicationException("Exception in getting User by pk");
	 * 
	 * }finally{ JDBCDataSource.closeConnection(conn);
	 * 
	 * } log.debug("Model findByPK End");
	 * 
	 * return dto; }
	 */
	
	 /**
     * Find TimeTable by PK
     *
     * @param pk
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */

	public TimeTableDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		TimeTableDTO dto = null;

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setSubjectId(rs.getLong(4));
				dto.setSubjectName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return dto;
	}

	
	public TimeTableDTO findBySubCourseName(String sname ,String cname,String sem) throws ApplicationException
	{
		log.debug("Model findBySubName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE SUBJECT_NAME=? AND COURSE_NAME=? AND SEMESTER=?");
		TimeTableDTO dto = null;

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, sname);
			pstmt.setString(2,cname);
			pstmt.setString(3,sem);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setSubjectId(rs.getLong(4));
				dto.setSubjectName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by sname");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBySubName End");
		return dto;
	}


	
	/*public TimeTableDTO findByCourseName(long cname) throws ApplicationException {
		log.debug("Model findByCourseName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=?");
		TimeTableDTO dto = null;

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1,cname);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setSubjectId(rs.getLong(4));
				dto.setSubjectName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by cname");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByCourseName End");
		return dto;
	}

*/
	
	
	
	 /**
     * Update a TimeTable
     *
     * @param dto
     * @throws ApplicationException DuplicateRecordException
     */


	public void update(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("TimeTable Model Updated Started");

		Connection conn = null;

		
		  TimeTableDTO dtoExist=findByPK(dto.getId());
		 
		 /*if(dtoExist!=null && !(dtoExist.getId()==dto.getId()))
		  { throw new DuplicateRecordException("LoginId is already exist");
		  
		  }*/
		 
			CourseModel coModel = new CourseModel();
			CourseDTO coursedto = coModel.findByPK(dto.getCourseId());
			dto.setCourseName(coursedto.getName());

			SubjectModel sModel = new SubjectModel();
			SubjectDTO subjectdto = sModel.findByPK(dto.getSubjectId());
			dto.setSubjectName(subjectdto.getName());


		 
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction

			PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_TIMETABLE SET  COURSE_ID=?, COURSE_NAME=?, SUBJECT_ID=?, SUBJECT_NAME=?, SEMESTER=?,EXAM_DATE=?,TIME=?, CREATED_BY=?, MODIFIED_BY=?, CREATED_DATETIME=?, MODIFIED_DATETIME=? WHERE ID=?");
			
		
			pstmt.setLong(1, dto.getCourseId());
			pstmt.setString(2, dto.getCourseName());
			pstmt.setLong(3, dto.getSubjectId());
			pstmt.setString(4, dto.getSubjectName());
			pstmt.setString(5, dto.getSemester());
			pstmt.setDate(6, new java.sql.Date(dto.getExamDate().getTime()));
			pstmt.setString(7, dto.getTime());
			pstmt.setString(8, dto.getCreatedBy());
			pstmt.setString(9, dto.getModifiedBy());
			pstmt.setTimestamp(10, dto.getCreatedDatetime());
			pstmt.setTimestamp(11, dto.getModifiedDatetime());
			pstmt.setLong(12, dto.getId());

			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		}

		catch (Exception e) {
			e.printStackTrace();

			log.error("Database Exception..", e);

			try {
				conn.rollback();
			}

			catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating User ");
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable Model update End");

	}


	   /**
	     * Search TimeTable
	     *
	     * @param dto: Search Parameters
	     * @throws ApplicationException
	     */

	public List search(TimeTableDTO dto) throws ApplicationException {

		return search(dto, 0, 0);

	}

	 /**
     * Search TimeTable with pagination
     *
     * @return list : List of TimeTables
     * @param dto : Search Parameters
     * @param pageNo: Current Page No.
     * @param pageSize: Size of Page
     *
     * @throws ApplicationException
     */
	public List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.debug("TimeTable Model search Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");

		/*
		 * if (dto != null) { if (dto.getId() > 0) { sql.append(" AND id = " +
		 * dto.getId()); }
		 */

		if (dto.getCourseId() > 0) {
			sql.append(" AND COURSE_ID = " + dto.getCourseId());
		}

		if (dto.getSubjectId() > 0) {
			sql.append(" AND SUBJECT_ID = " + dto.getSubjectId());
		}

		/*
		 * if (dto.getCourseName() != null && dto.getCourseName().length() >
		 * 0) { sql.append(" AND COURSE_NAME like '" + dto.getCourseName() +
		 * "%'"); }
		 */
		/*
		 * if (dto.getSubjectName() != null && dto.getSubjectName().length() >
		 * 0) { sql.append(" AND SUBJECT_NAME like '" + dto.getSubjectName() +
		 * "%'");
		 * 
		 * }
		 */
		if (dto.getSemester() != null && dto.getSemester().length() > 0) {
			sql.append(" AND SEMESTER like '" + dto.getSemester() + "%'");
		}

		if (dto.getExamDate() != null && dto.getExamDate().getDate() > 0) {
			sql.append(" AND EXAM_DATE like '" + dto.getExamDate() + "%'");
		}

		if (dto.getTime() != null && dto.getTime().length() > 0) {
			sql.append(" AND TIME like '" + dto.getTime() + "%'");
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		System.out.println(sql);
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new TimeTableDTO();

				dto.setId(rs.getLong(1));
				dto.setCourseId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setSubjectId(rs.getLong(4));
				dto.setSubjectName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Timetable Model search End");
		return list;
	}

	 
	  /**
	     * Get List of TimeTable
	     *
	     * @return list : List of TimeTable
	     * @throws ApplicationException
	     */



	public List list() throws ApplicationException {
		return list(0, 0);
	}

	 /**
     * Get List of TimeTable with pagination
     *
     * @return list : List of TimeTable
     * @param pageNo : Current Page No.
     * @param pageSize : Size of Page
     * @throws ApplicationException
     */


	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list Started");

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_timetable");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);

		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				TimeTableDTO dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setSubjectId(rs.getLong(4));
				dto.setSubjectName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));

				list.add(dto);
			}
			rs.close();
		}

		catch (Exception e) {

			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

}
