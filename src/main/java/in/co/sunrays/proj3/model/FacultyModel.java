package in.co.sunrays.proj3.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of Faculty Model
 *
 * @author SunilOS
 * @version 1.0
 * 
 */

public class FacultyModel {
	
	private static Logger log = Logger.getLogger(FacultyModel.class);
	
	
	 /**
     * Find next PK of Faculty
     *
     * @throws DatabaseException
     */
 

	 public Integer nextPK() throws DatabaseException {
	        log.debug("Model nextPK Started");
	        Connection conn = null;
	        int pk = 0;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) 
		            {
		                pk = rs.getInt(1);
		            }
		            rs.close();

	        } 
	        catch (Exception e) 
		        {
		            log.error("Database Exception..", e);
		            throw new DatabaseException("Exception : Exception in getting PK");
		        } 
		    finally 
		        {
		            JDBCDataSource.closeConnection(conn);
		        }
			        log.debug("Model nextPK End");
			        return pk + 1;
	    }


	  /**
	     * Add a Faculty
	     *
	     * @param dto
	     * @throws ApplicationException DuplicateRecordException
	     *
	     */

	 public long add(FacultyDTO dto) throws ApplicationException,DuplicateRecordException
		
	 {
			log.debug("Model add started");
			Connection conn=null;
			
			 int pk = 0;


			CollegeModel cModel= new CollegeModel();
			CollegeDTO collegedto=cModel.findByPK(dto.getCollegeId());
			dto.setCollegeName(collegedto.getName());
			
			CourseModel coModel= new CourseModel();
			CourseDTO coursedto=coModel.findByPK(dto.getCourseId());
			dto.setCourseName(coursedto.getName());
			
			SubjectModel sModel= new SubjectModel();
			SubjectDTO subjectdto=sModel.findByPK(dto.getSubjectId());
			dto.setSubjectName(subjectdto.getName());
			

			
			FacultyDTO duplicateName=findByEmailId(dto.getEmailId());
					        if (duplicateName != null) {
		            throw new DuplicateRecordException("Email already exists");
		        }

		        try 
		        {
		            conn = JDBCDataSource.getConnection();
		            pk = nextPK();
		            
		            System.out.println(pk + " in ModelJDBC");
		            conn.setAutoCommit(false); 
		       
		          PreparedStatement psmt = conn.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		            
		          psmt.setLong(1,pk);
		      	  psmt.setLong(2, dto.getCollegeId());
		      	 psmt.setString(3, dto.getFirstName());
		      	  psmt.setString(4, dto.getLastName());
		      	 
		      	  psmt.setLong(5, dto.getCourseId());
		      	  psmt.setString(6,dto.getCourseName());
		      	  psmt.setLong(7, dto.getSubjectId());
		      	  psmt.setString(8, dto.getSubjectName());
		      	  psmt.setString(9, dto.getCollegeName());
		      	  psmt.setString(10, dto.getQualification());
		      	  psmt.setString(11, dto.getEmailId());
		      	  psmt.setDate(12, (new java.sql.Date(dto.getDob().getTime())));
		      	  psmt.setString(13, dto.getMobNo());
		      	  psmt.setString(14, dto.getCreatedBy());
		          psmt.setString(15, dto.getModifiedBy());
		          psmt.setTimestamp(16, dto.getCreatedDatetime());
		          psmt.setTimestamp(17, dto.getModifiedDatetime());
		         
		          psmt.executeUpdate();
		          conn.commit(); 
		          psmt.close();
		            
		        } catch (Exception e) {
		        	e.printStackTrace();
		            log.error("Database Exception..", e);
		            try {
		                conn.rollback();
		            } 
		            catch (Exception ex)
		            {
		                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
		            }
		            throw new ApplicationException("Exception : Exception in add Faculty");
		        } finally
		     
		        {
		            JDBCDataSource.closeConnection(conn);
		        }
		        log.debug("Model add End");
		        return pk;
		    
		}
		

	 
	 
	 
	 /**
	     * Delete a Faculty
	     *
	     * @param dto
	     * @throws ApplicationException
	     */
	 
	 
	 public void delete(FacultyDTO dto)throws ApplicationException{
			
		 	log.debug("Model delete Started");
			
			Connection conn=null;
			try {
				
				conn=JDBCDataSource.getConnection();
				conn.setAutoCommit(false);//begin transaction
				
				PreparedStatement pstmt=conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
				
				pstmt.setLong(1, dto.getId());
				pstmt.executeUpdate();
				conn.commit();//end transaction
				pstmt.close();
				
			} catch (Exception e) {
				
				log.error("Database Exception..",e);
				try {
						conn.rollback();
						
					} 
				catch (Exception ex) 
					{
						throw new ApplicationException("Execption: Delete rollback exception"+ex.getMessage());
						
					}
						throw new ApplicationException("Exception: Exception in delete User");
				
				
				
			}
				finally
				{
					JDBCDataSource.closeConnection(conn);
				}
	
			log.debug("Model delete Started");
			
		}
	 
	 
	 /**
	     * Update a Faculty
	     *
	     * @param dto
	     * @throws Exception
	     */

	 
	 public void update(FacultyDTO dto)throws Exception
	 {
			
			log.debug("Model Updated Started");
			
			Connection conn=null;
			
			FacultyDTO dtoExist=findByPK(dto.getId());
			
			if(dtoExist!=null && !(dtoExist.getId()==dto.getId()))
				{
					throw new DuplicateRecordException("LoginId is already exist");
					
				}
		/*	
			FacultyModel fmodel=new FacultyModel();
			FacultyDTO fdto=fmodel.findByPK(dto.getId());
			
			
			*/
			//System.out.println("date "+dto.getDob());
			CollegeModel cModel= new CollegeModel();
			CollegeDTO collegedto=cModel.findByPK(dto.getCollegeId());
			dto.setCollegeName(collegedto.getName());
			
			CourseModel coModel= new CourseModel();
			CourseDTO coursedto=coModel.findByPK(dto.getCourseId());
			dto.setCourseName(coursedto.getName());
			
			SubjectModel sModel= new SubjectModel();
			SubjectDTO subjectdto=sModel.findByPK(dto.getSubjectId());
			dto.setSubjectName(subjectdto.getName());
			

			
			try {
				conn = JDBCDataSource.getConnection();
	            conn.setAutoCommit(false); // Begin transaction
	           
	            PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_FACULTY SET COLLEGE_ID=?,FIRST_NAME=?,LAST_NAME=?,COURSE_ID=?,COURSE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,COLLEGE_NAME=?,QUALIFICATION=?,EMAIL_ID=?,DOB=?,MOB_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
	           
	            
	            pstmt.setLong(1,dto.getCollegeId());
	            pstmt.setString(2, dto.getFirstName());
	           pstmt.setString(3,dto.getLastName());
	           pstmt.setLong(4, dto.getCourseId());
	           pstmt.setString(5,dto.getCourseName());
	           pstmt.setLong(6,dto.getSubjectId());
	           pstmt.setString(7,dto.getSubjectName());
	           pstmt.setString(8,dto.getCollegeName());
	           pstmt.setString(9, dto.getQualification());
	           pstmt.setString(10, dto.getEmailId());
	           pstmt.setDate(11, new java.sql.Date(dto.getDob().getTime()));
	           pstmt.setString(12,dto.getMobNo());
	           pstmt.setString(13, dto.getCreatedBy());
	            pstmt.setString(14, dto.getModifiedBy());
	            pstmt.setTimestamp(15, dto.getCreatedDatetime());
	            pstmt.setTimestamp(16, dto.getModifiedDatetime());
	            pstmt.setLong(17, dto.getId());
	           
	            pstmt.executeUpdate();
	           
	            conn.commit(); // End transaction
	            pstmt.close();
	        } 
			
			catch (Exception e)
				{
	            e.printStackTrace();
	            
	            log.error("Database Exception..", e);
	            
	            
	            try {
	                conn.rollback();
	            	} 
	            catch (Exception ex)
		            {
		               
		            	throw new ApplicationException("Exception : Delete rollback exception "+ ex.getMessage());
		            }
	            	
	            		throw new ApplicationException("Exception in updating User ");
	        } 
				finally 
					{
		            JDBCDataSource.closeConnection(conn);
		        	}
		        		log.debug("Model update End");
		  
	}


	 
	 /**
	     * Search Faculty
	     *
	     * @param dto : Search Parameters
	     * @throws ApplicationException
	     */


	
	 
	 public List search(FacultyDTO dto) throws ApplicationException {
	     System.out.println("search...1/faculty"); 
		 return search(dto, 0, 0);
	    }
	 
	 
	  /**
	     * Search Faculty with pagination
	     *
	     * @return list : List of Faculty
	     * @param dto : Search Parameters
	     * @param pageNo: Current Page No.
	     * @param pageSize: Size of Page
	     *
	     * @throws ApplicationException
	     */
	 
	 
	 public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException 
	 {
	       
		 System.out.println("search...2/faculty");
		 log.debug("Model search Started");
	        
		 StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");

	        if (dto != null) {
	          
	        	if (dto.getId() > 0) 
	            {
	                sql.append(" AND id = " + dto.getId());
	            }

	        	
	 
	        	
	        	  if (dto.getFirstName() != null && dto.getFirstName().length() > 0) 
		            {
		                sql.append(" AND FIRST_NAME like '" + dto.getFirstName() + "%'");
		            }

	        	  if (dto.getLastName() != null && dto.getLastName().length() > 0)
		            {
		                sql.append(" AND LAST_NAME like '" + dto.getLastName() + "%'");
		            }
	            
	        	
	            if (dto.getCourseId() > 0) 
	            {
	                sql.append(" AND COURSE_ID = " + dto.getCourseId());
	            }
	            
	            
	            if (dto.getCollegeId() > 0) 
	            {
	                sql.append(" AND COLLEGE_ID = " + dto.getCollegeId());
	            }
	            

	            

	            if (dto.getCourseName() != null && dto.getCourseName().length() > 0)
		            {
		                sql.append(" AND COURSE_NAME like '" + dto.getCourseName() + "%'");
		            }
	            
	            
	      
	          		            
	           

	            if (dto.getSubjectId() > 0) 
		            {
		                sql.append(" AND SUBJECT_ID = " + dto.getSubjectId());
		            }  
	            
	          	           
	          	            
	            
	            
	            if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0)
		            {
		                sql.append(" AND SUBJECT_NAME like '" + dto.getSubjectName() + "%'");
		            }
	           
	            
	            if (dto.getCollegeName() != null&& dto.getCollegeName().length() > 0)
	            {
	                sql.append(" AND COLLEGE_NAME = " + dto.getCollegeName());
	            }
    
	            
	            
	            
	            
	            if (dto.getQualification() != null && dto.getQualification().length() > 0) 
		            {
		                sql.append(" AND QUALIFICATION like '" + dto.getQualification() + "%'");
		            }
		            
	            if (dto.getEmailId() != null && dto.getEmailId().length() > 0) 
		            {
		                sql.append(" AND EMAIL_ID like '" + dto.getEmailId() + "%'");
		            }
		            
	           if (dto.getDob() != null && dto.getDob().getDate() > 0) 
		           {
		                sql.append(" AND DOB = " + dto.getDob());
		           }
	            if (dto.getMobNo() != null && dto.getMobNo().length() > 0) 
		            {
		                sql.append(" AND MOB_NO = " + dto.getMobNo());
		            }
	            
	           
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
	            while (rs.next()) 
	            {
	                dto = new FacultyDTO();
	        
	                dto.setId(rs.getLong(1));
	                
	                dto.setCollegeId(rs.getLong(2));
	                dto.setFirstName(rs.getString(3));
	                dto.setLastName(rs.getString(4));
	                dto.setCourseId(rs.getLong(5));
	                dto.setCourseName(rs.getString(6));
	                
	                dto.setSubjectId(rs.getLong(7));
	                dto.setSubjectName(rs.getString(8));
	               
	                dto.setCollegeName(rs.getString(9));
	                dto.setQualification(rs.getString(10));
	                dto.setEmailId(rs.getString(11));
	                dto.setDob(rs.getDate(12));
	                dto.setMobNo(rs.getString(13));
	                dto.setCreatedBy(rs.getString(14));
	                dto.setModifiedBy(rs.getString(15));
	                dto.setCreatedDatetime(rs.getTimestamp(16));
	                dto.setModifiedDatetime(rs.getTimestamp(17));

	                list.add(dto);
	            }
	           
	            rs.close();
	        } 
	        catch (Exception e) 
		        {
		            log.error("Database Exception..", e);
		            throw new ApplicationException("Exception : Exception in search user");
		        }
		        
	        finally 
		        {
		            JDBCDataSource.closeConnection(conn);
		        }

		        log.debug("Model search End");
		        
	 
		        return list;
			    
		        }
	 
	 
	  /**
	     * Get List of Faculty
	     *
	     * @return list : List of Faculty
	     * @throws ApplicationException
	     */


	 public List list() throws ApplicationException {
	        return list(0, 0);
	    }
	 
	 /**
	     * Get List of Facutly with pagination
	     *
	     * @return list : List of Faculty
	     * @param pageNo : Current Page No.
	     * @param pageSize : Size of Page
	     * @throws ApplicationException
	     */
	 public List list(int pageNo, int pageSize) throws ApplicationException {
	       
		 log.debug("Model list Started");
	     
		
		 ArrayList list = new ArrayList();
	     
		System.out.println("list.......1");
		 StringBuffer sql = new StringBuffer("select * from ST_FACULTY");
	        	
	     
		 if (pageSize > 0) {
	         System.out.println("list.......2");   
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	           
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            
	            ResultSet rs = pstmt.executeQuery();
	            
	            while (rs.next()) {
	                FacultyDTO dto = new FacultyDTO();
	                dto.setId(rs.getLong(1));
	                dto.setCollegeId(rs.getLong(2));
	                dto.setFirstName(rs.getString(3));
	                dto.setLastName(rs.getString(4));
	                dto.setCourseId(rs.getLong(5));
	                dto.setCourseName(rs.getString(6));
	               
	                dto.setSubjectId(rs.getLong(7));
	                dto.setSubjectName(rs.getString(8));
	               
	                dto.setCollegeName(rs.getString(9));
	                dto.setQualification(rs.getString(10));
	                dto.setEmailId(rs.getString(11));
	                dto.setDob(rs.getDate(12));
	                dto.setMobNo(rs.getString(13));
	                dto.setCreatedBy(rs.getString(14));
	                dto.setModifiedBy(rs.getString(15));
	                dto.setCreatedDatetime(rs.getTimestamp(16));
	                dto.setModifiedDatetime(rs.getTimestamp(17));

	                list.add(dto);
	            }
	            
	            rs.close();
	        }
	        catch (Exception e) 
		        {
		            log.error("Database Exception..", e);
		            throw new ApplicationException("Exception : Exception in getting list of users");
		        } 
	        finally 
		        {
		            JDBCDataSource.closeConnection(conn);
		        }

		        log.debug("Model list End");
		        return list;

	    }
	 
	 /**
	     * Find Faculty by PK
	     *
	     * @param pk : get parameter
	     * @return dto
	     * @throws ApplicationException
	     */

	 public FacultyDTO findByPK(long pk) throws ApplicationException{
			
			log.debug("Model findByPK Started");
			
			StringBuffer sql=new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
			
			FacultyDTO dto=null;
			Connection conn=null;
			
			
			try {
				
				conn=JDBCDataSource.getConnection();
				PreparedStatement pstmt=conn.prepareStatement(sql.toString());
				pstmt.setLong(1, pk);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next())
				{
						dto=new FacultyDTO();
						
						dto.setId(rs.getLong(1));
						dto.setCollegeId(rs.getLong(2));
						dto.setFirstName(rs.getString(3));
						dto.setLastName(rs.getString(4));
						dto.setCourseId(rs.getLong(5));
						dto.setCourseName(rs.getString(6));
						
						dto.setSubjectId(rs.getLong(7));
						dto.setSubjectName(rs.getString(8));
						dto.setCollegeName(rs.getString(9));
						dto.setQualification(rs.getString(10));
						dto.setEmailId(rs.getString(11));
						dto.setDob(rs.getDate(12));
						dto.setMobNo(rs.getString(13));
		                dto.setCreatedBy(rs.getString(14));
		                dto.setModifiedBy(rs.getString(15));
		                dto.setCreatedDatetime(rs.getTimestamp(16));
		                dto.setModifiedDatetime(rs.getTimestamp(17));

				
				}
				rs.close();
				
			}
			catch (Exception e) {
				e.printStackTrace();
				
				log.error("Database Exception.."+ e);
				 throw new ApplicationException("Exception in getting User by pk");
				 
			}
			finally
				{
					JDBCDataSource.closeConnection(conn);
					
				}
			log.debug("Model findByPK End");
			
			return dto;
		}
		
	 public FacultyDTO findByEmailId(String Email)throws ApplicationException
	 {
		 log.debug("Model findBy Email Started");
	     StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
	     FacultyDTO dto = null;
	     Connection conn = null;
	     try 
	     {
	         conn = JDBCDataSource.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	         pstmt.setString(1, Email);
	         ResultSet rs = pstmt.executeQuery();
	        
	         while (rs.next()) 
	         {
	             	dto = new FacultyDTO();
	             	
	             	dto.setId(rs.getLong(1));
	                
	                dto.setCollegeId(rs.getLong(2));
	                dto.setFirstName(rs.getString(3));
	                dto.setLastName(rs.getString(4));

	                dto.setCourseId(rs.getLong(5));
	                dto.setCourseName(rs.getString(6));
	                dto.setSubjectId(rs.getLong(7));
	                dto.setSubjectName(rs.getString(8));
	                dto.setCollegeName(rs.getString(9));
	                dto.setQualification(rs.getString(10));
	                dto.setEmailId(rs.getString(11));
	                dto.setDob(rs.getDate(12));
	                
	               
	                dto.setMobNo(rs.getString(13));
	                dto.setCreatedBy(rs.getString(14));
	                dto.setModifiedBy(rs.getString(15));
	                dto.setCreatedDatetime(rs.getTimestamp(16));
	                dto.setModifiedDatetime(rs.getTimestamp(17));

	         }
	         rs.close();
	     }
	     catch (Exception e)
	     {
	         log.error("Database Exception..", e);
	         throw new ApplicationException("Exception : Exception in getting User by Email");
	     }
	     finally
	     {
	         JDBCDataSource.closeConnection(conn);
	     }
	     log.debug("Model findBy Email End");
	     return dto;
	 }
	 
	            

}
