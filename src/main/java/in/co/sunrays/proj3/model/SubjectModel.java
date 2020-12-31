package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;
/**
 * JDBC Implementation of SubjectModel
 *
 * @author SunilOS
 * @version 1.0
 
 */
public class SubjectModel {
	
	private static Logger log = Logger.getLogger(SubjectModel.class);
	
	  /**
     * Find next PK of Subject
     *
     * @throws DatabaseException
     */
	  public Integer nextPK() throws DatabaseException {
	       
		  log.debug("Timetable Model nextPK Started");
	      
		  Connection conn = null;
	      
		  int pk = 0;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_SUBJECT");
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
			        log.debug("Subject Model nextPK End");
			        return pk + 1;
	  }
	  

	  /**
	     * Add a Subject
	     *
	     * @param dto
	     * @throws ApplicationException DuplicateRecordException
	     *
	     */
	 
	  public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException
	  {
			log.debug("Subject Model add Started");
			
			Connection conn=null;
			int pk=0;
			
			CourseModel coursemodel = new CourseModel();
			CourseDTO coursedto = coursemodel.findByPK(dto.getCourseId());
			dto.setCourseName(coursedto.getName());
			
			
			SubjectDTO dton=findByName(dto.getName());
			
			if(dton !=null){
				throw new DuplicateRecordException("Name  already exists");
				
			}
			try {
				conn=JDBCDataSource.getConnection();
				pk=nextPK();
				
				System.out.println(pk+"in ModelJDBC");
				conn.setAutoCommit(false);
				PreparedStatement pstmt=conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");
				
				pstmt.setInt(1, pk);
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getDescription());
				pstmt.setLong(4, dto.getCourseId());
				pstmt.setString(5, dto.getCourseName());
				pstmt.setString(6, dto.getCreatedBy());
				pstmt.setString(7, dto.getModifiedBy());
				pstmt.setTimestamp(8, dto.getCreatedDatetime());
				pstmt.setTimestamp(9, dto.getModifiedDatetime());
				pstmt.executeUpdate();
				
				conn.commit();
				pstmt.close();
				
				
				
			} catch (Exception e) {
				log.error("Database Exception.."+ e);
				
				try {
					conn.rollback();
					
				} catch (Exception ex) {
					
					ex.printStackTrace();
					
					throw new ApplicationException("Exception: add rollback exception"+ex.getMessage());
					
				}
				
				throw new ApplicationException("Exception : Exception in add Subject");
			  } 
			
			finally 
				{
		            JDBCDataSource.closeConnection(conn);
		        }
		       
				
				log.debug("Model add End");
		        return pk;
		    }
	  
	  
	  
	  
	  /**
	     * Delete a Subject
	     *
	     * @param dto
	     * @throws ApplicationException
	     */
	  
		public void delete(SubjectDTO dto)throws ApplicationException{
			log.debug("Model delete Started");
			Connection conn=null;
			try {
				
				conn=JDBCDataSource.getConnection();
				conn.setAutoCommit(false);//begin transaction
				
				PreparedStatement pstmt=conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
				
				pstmt.setLong(1, dto.getId());//doubt
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
	     * Update a Subject
	     *
	     * @param dto
	     * @throws ApplicationException DuplicateRecordException
	     */
	 
		public void update(SubjectDTO dto)throws ApplicationException,DuplicateRecordException{
			
			log.debug("Subject Model Updated Started");
			
			Connection conn=null;
			
			CourseModel coursemodel = new CourseModel();
			CourseDTO coursedto = coursemodel.findByPK(dto.getCourseId());
			dto.setCourseName(coursedto.getName());
			

			
			/*SubjectDTO dtoExist=findByLogin(dto.getLogin());
			
			if(dtoExist!=null && !(dtoExist.getId()==dto.getId()))
				{
					throw new DuplicateRecordException("LoginId is already exist");
					
				}*/
			try {
				conn = JDBCDataSource.getConnection();
	            conn.setAutoCommit(false); // Begin transaction
	           
	            PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_SUBJECT SET NAME=?,DESCRIPTION=?,COURSE_ID=?,COURSE_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
	           
	            pstmt.setString(1, dto.getName());
	            pstmt.setString(2,dto.getDescription());
	            pstmt.setLong(3, dto.getCourseId());
	            pstmt.setString(4, dto.getCourseName());
	            pstmt.setString(5, dto.getCreatedBy());
				pstmt.setString(6, dto.getModifiedBy());
				pstmt.setTimestamp(7, dto.getCreatedDatetime());
				pstmt.setTimestamp(8, dto.getModifiedDatetime());
				pstmt.setLong(9,dto.getId());
	            
	            
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
	            	
	            		throw new ApplicationException("Exception in updating Subject ");
	        } 
			finally 
				{
	            JDBCDataSource.closeConnection(conn);
	        	}
	        		log.debug("Model update End");
	  
	}

		
		 /**
	     * Search Subject
	     *
	     * @param dto
	     *            : Search Parameters
	     * @throws ApplicationException
	     */



		public List search(SubjectDTO dto) throws ApplicationException {
	        return search(dto, 0, 0);
	    }
	    
		  /**
	     * Search Subject with pagination
	     *
	     * @return list : List of Users
	     * @param dto
	     *            : Search Parameters
	     * @param pageNo
	     *            : Current Page No.
	     * @param pageSize
	     *            : Size of Page
	     *
	     * @throws ApplicationException
	     */
		 public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException 
		 {
		       
			 log.debug("Model search Started");
		        
			 StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");

		        if (dto != null) {
		           
		            if (dto.getName() != null && dto.getName().length() > 0) 
			            {
			                sql.append(" AND NAME like '" + dto.getName() + "%'");
			            }
		            
		            if (dto.getDescription() != null && dto.getDescription().length() > 0)
			            {
			                sql.append(" AND DESCRIPTION like '" + dto.getDescription() + "%'");
			            }
		            
		            if (dto.getCourseId() > 0) 
		            {
		                sql.append(" AND COURSE_ID = " + dto.getCourseId());
		            }

		            
		            
		         /*   if (dto.getCourseName()!= null && dto.getCourseName().length() > 0) 
			            {
			                sql.append(" AND COURSE_NAME like '" + dto.getCourseName() + "%'");
			            }
*/			         
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
		            ResultSet rs=pstmt.executeQuery();
		            while (rs.next()) 
		            {
		                dto = new SubjectDTO();
		               
		                dto.setId(rs.getLong(1));
		                dto.setName(rs.getString(2));
		                dto.setDescription(rs.getString(3));
		                dto.setCourseId(rs.getLong(4));
		                dto.setCourseName(rs.getString(5));
		                dto.setCreatedBy(rs.getString(6));
		                dto.setModifiedBy(rs.getString(7));
		                dto.setCreatedDatetime(rs.getTimestamp(8));
		                dto.setModifiedDatetime(rs.getTimestamp(9));

		               
		                
		                list.add(dto);
		            }
		           
		            rs.close();
		        } 
		        catch (Exception e) 
			        {
		        	e.printStackTrace();
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
		     * Get List of Subject
		     *
		     * @return list : List of Subject
		     * @throws ApplicationException
		     */

		 public List list() throws ApplicationException {
		        return list(0, 0);
		    }
		 
		  /**
		     * Get List of Subject with pagination
		     *
		     * @return list : List of Subject
		     * @param pageNo : Current Page No.
		     * @param pageSize : Size of Page
		     * @throws ApplicationException
		     */


		 public List list(int pageNo, int pageSize) throws ApplicationException {
		       
			 log.debug("Model list Started");
		     
			 ArrayList list = new ArrayList();
		     
			 StringBuffer sql = new StringBuffer("select * from ST_SUBJECT");
		        
		     
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
		                SubjectDTO dto = new SubjectDTO();
		               
		                dto.setId(rs.getLong(1));
		                dto.setName(rs.getString(2));
		                dto.setDescription(rs.getString(3));
		                dto.setCourseId(rs.getLong(4));
		                dto.setCourseName(rs.getString(5));
		                dto.setCreatedBy(rs.getString(6));
		                dto.setModifiedBy(rs.getString(7));
		                dto.setCreatedDatetime(rs.getTimestamp(8));
		                dto.setModifiedDatetime(rs.getTimestamp(9));

		               
		                
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
		  * Find Subject by PK
		  *
		  * @param pk
		  *            : get parameter
		  * @return dto
		  * @throws ApplicationException
		  */

		 public SubjectDTO findByPK(long pk) throws ApplicationException{
				
				log.debug("Model findByPK Started");
				
				StringBuffer sql=new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
				
				SubjectDTO dto=null;
				Connection conn=null;
				
				
				try {
					
					conn=JDBCDataSource.getConnection();
					PreparedStatement pstmt=conn.prepareStatement(sql.toString());
					pstmt.setLong(1, pk);
					ResultSet rs=pstmt.executeQuery();
					while(rs.next())
					{
							dto=new SubjectDTO();
							
							dto.setId(rs.getLong(1));
							dto.setName(rs.getString(2));
							dto.setDescription(rs.getString(3));
							dto.setCourseId(rs.getLong(4));
							dto.setCourseName(rs.getString(5));
			
							dto.setCreatedBy(rs.getString(6));
			                dto.setModifiedBy(rs.getString(7));
			                dto.setCreatedDatetime(rs.getTimestamp(8));
			                dto.setModifiedDatetime(rs.getTimestamp(9));

					
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
			

		 
		 public SubjectDTO findByName(String name) throws ApplicationException{
				
				log.debug("Model findByName Started");
				
				StringBuffer sql=new StringBuffer("SELECT * FROM ST_SUBJECT WHERE NAME=?");
				
				SubjectDTO dto=null;
				Connection conn=null;
				
				
				try {
					
					conn=JDBCDataSource.getConnection();
					PreparedStatement pstmt=conn.prepareStatement(sql.toString());
					pstmt.setString(1, name);
					ResultSet rs=pstmt.executeQuery();
					while(rs.next())
					{
							dto=new SubjectDTO();
							
							dto.setId(rs.getLong(1));
							dto.setName(rs.getString(2));
							dto.setDescription(rs.getString(3));
							dto.setCourseId(rs.getLong(4));
							dto.setCourseName(rs.getString(5));
			
							dto.setCreatedBy(rs.getString(6));
			                dto.setModifiedBy(rs.getString(7));
			                dto.setCreatedDatetime(rs.getTimestamp(8));
			                dto.setModifiedDatetime(rs.getTimestamp(9));

					
					}
					rs.close();
					
				}
				catch (Exception e) {
					e.printStackTrace();
					
					log.error("Database Exception.."+ e);
					 throw new ApplicationException("Exception in getting User by name");
					 
				}
				finally
					{
						JDBCDataSource.closeConnection(conn);
						
					}
				log.debug("Model findByName End");
				
				return dto;
			}
			

}
