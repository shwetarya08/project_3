package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of Course Model
 *
 * @author SunilOS
 * @version 1.0
 
 */

public class CourseModel {
	
Logger log=Logger.getLogger(CourseModel.class);
	
/**
 * Find next PK of Course
 *
 * @throws DatabaseException
 */


public Integer nextPK() throws DatabaseException
	{
		log.debug("Model nextPK Started");
		Connection conn=null;
		int pk=0;
		
		try
		{
			conn=JDBCDataSource.getConnection();
			System.out.println("Connection successfully establish");
			
			PreparedStatement pstmt=conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
			
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				pk=rs.getInt(1);
				
			}
			rs.close();
			
		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Exception in Course getting PK");
		}
				finally
				{
				JDBCDataSource.closeConnection(conn);	
				
				}
		log.debug("Model nextPK end");
		return pk+1;
	}
	
	 /**
     * Add a Course
     *
     * @param dto
     * @throws ApplicationException DuplicateRecordException

     *
     */
	
	 public long add(CourseDTO dto) throws ApplicationException,DuplicateRecordException
 {
 
		 log.debug("Model add Started");
		 Connection conn = null;
		 int pk = 0;

		 
		 CourseDTO dton=findByCourseName(dto.getName());
			
			if(dton !=null){
				throw new DuplicateRecordException("Course already exists");
				
			}
		 try 
		 {
	        
	 conn = JDBCDataSource.getConnection();
	 pk = nextPK();
	          
	 conn.setAutoCommit(false);
	 
	 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
	 
	  pstmt.setInt(1, pk);
	  pstmt.setString(2,dto.getName());
      pstmt.setString(3, dto.getDescription());
      pstmt.setString(4, dto.getDuration());
      pstmt.setString(5, dto.getCreatedBy());
      pstmt.setString(6, dto.getModifiedBy());
      pstmt.setTimestamp(7, dto.getCreatedDatetime());
      pstmt.setTimestamp(8, dto.getModifiedDatetime());
      
      pstmt.executeUpdate();
      conn.commit(); 
      pstmt.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            try 
	            {
	                conn.rollback();
	            } 
	            catch (Exception ex)
	            {
	                ex.printStackTrace();
	                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
	            }
	            throw new ApplicationException("Exception : Exception in add College");
	        } 
		 finally 
		 {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model add End");
	        return pk;
	    }


	 /**
     * Delete a Course
     *
     * @param dto
     * @throws ApplicationException
     */

	 
	 public void delete(CourseDTO dto) throws ApplicationException
	 {
	        log.debug("Model delete Started");
	        Connection conn = null;
	        try
	        {
	            conn = JDBCDataSource.getConnection();
	            conn.setAutoCommit(false); 
	            
	            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
	            
	            pstmt.setLong(1, dto.getId());
	            pstmt.executeUpdate();
	            conn.commit(); 
	            pstmt.close();

	        } 
	        catch (Exception e)
	        {
	            log.error("Database Exception..", e);
	            try 
	            {
	                conn.rollback();
	            }
	            catch (Exception ex)
	            {
	                throw new ApplicationException("Exception : Delete rollback exception "+ ex.getMessage());
	            }
	            throw new ApplicationException("Exception : Exception in delete course");
	        }
	        finally
	        {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model delete Started");
	    }


	 /**
	     * Find Course by PK
	     *
	     
	     * @return dto
	     * @throws ApplicationException
	     */
	 public CourseDTO findByPK(long pk) throws ApplicationException
	 {
	     log.debug("Model findByPK Started");
	     StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
	     CourseDTO dto = null;
	     
	     Connection conn = null;
	     try
	     {
	         conn = JDBCDataSource.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	         pstmt.setLong(1, pk);
	         ResultSet rs = pstmt.executeQuery();
	         while (rs.next())
	         {
	             dto = new CourseDTO();
	             dto.setId(rs.getLong(1));
	             dto.setName(rs.getString(2));
	             dto.setDescription(rs.getString(3));
	             dto.setDuration(rs.getString(4));
	             dto.setCreatedBy(rs.getString(5));
	             dto.setModifiedBy(rs.getString(6));
	             dto.setCreatedDatetime(rs.getTimestamp(7));
	             dto.setModifiedDatetime(rs.getTimestamp(8));
	         }
	         rs.close();
	     } catch (Exception e) 
	     {
	         log.error("Database Exception..", e);
	         throw new ApplicationException("Exception : Exception in getting User by pk");
	     } finally 
	     {
	         JDBCDataSource.closeConnection(conn);
	     }
	     log.debug("Model findByPK End");
	     return dto;
	 }
	 

	 
	 
	  /**
	     * Update a Course
	     *
	     * @param dto
	     * @throws ApplicationException DuplicateRecordException
	     */


	 public void update(CourseDTO dto) throws ApplicationException,DuplicateRecordException
	 {
		 log.debug("Model update Started");
		 Connection conn = null;

/*CourseDTO dtoExist = findByName(dto.getName());

 
 	if (dtoExist != null && dtoExist.getId() != dto.getId()) {

     throw new DuplicateRecordException("Course is already exist");
 }
*/
		 try
		 {

     conn = JDBCDataSource.getConnection();

     conn.setAutoCommit(false); 
     PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_COURSE SET NAME=?, DESCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
    // pstmt.setLong(1, dto.getId());

     pstmt.setString(1,dto.getName());
     pstmt.setString(2, dto.getDescription());
     pstmt.setString(3, dto.getDuration());
     pstmt.setString(4, dto.getCreatedBy());
     pstmt.setString(5, dto.getModifiedBy());
     pstmt.setTimestamp(6, dto.getCreatedDatetime());
     pstmt.setTimestamp(7, dto.getModifiedDatetime());
     pstmt.setLong(8,dto.getId()); 
     pstmt.executeUpdate();
     
     conn.commit(); 
     pstmt.close();
 } 
		 catch (Exception e) 
		 {
     log.error("Database Exception..", e);
     try
     {
         conn.rollback();
     } 
     catch (Exception ex)
     {
         throw new ApplicationException("Exception : Delete rollback exception "+ ex.getMessage());
     }
     throw new ApplicationException("Exception in updating Course ");
 } 
		 finally
		 {
     JDBCDataSource.closeConnection(conn);
 }
 log.debug("Model update End");
}
	
	 /**
	     * Search Course
	     *
	     * @param dto : Search Parameters
	     * @throws ApplicationException
	     */
	 public List search(CourseDTO dto) throws ApplicationException
	 {
	        return search(dto, 0, 0);
	    }


	 
	 /**
	     * Search Course with pagination
	     *
	     * @return list : List of Course
	     * @param dto : Search Parameters
	     * @param pageNo: Current Page No.
	     * @param pageSize: Size of Page
	     *
	     * @throws ApplicationException
	     */


	 
		 
	 public List search(CourseDTO dto, int pageNo, int pageSize)throws ApplicationException
	 {
	        log.debug("Model search Started");
	        StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");

	        if (dto != null)
	        {
	            if (dto.getId() > 0)
	            {
	                sql.append(" AND id = " + dto.getId());
	            }
	            
	            if (dto.getName() != null && dto.getName().length() > 0) 
	            {
	                sql.append(" AND NAME like '" + dto.getName() + "%'");
	                
	            }
	          
	            
	            if (dto.getDescription() != null && dto.getDescription().length() > 0) 
	            {
	                sql.append(" AND DESCRIPTION like '" + dto.getDescription() + "%'");
	                
	            }
	            if (dto.getDuration() != null && dto.getDuration().length() > 0)
	            {
	                sql.append(" AND DURATION like '" + dto.getDuration() + "%'");
	            }
	           
	           
	        }

	        
	        if (pageSize > 0)
	        {
	           
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            
	        }

	        ArrayList list = new ArrayList();
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                dto = new CourseDTO();
	                dto.setId(rs.getLong(1));
	                dto.setName(rs.getString(2));
	                dto.setDescription(rs.getString(3));
	                dto.setDuration(rs.getString(4));
	               	dto.setCreatedBy(rs.getString(5));
	                dto.setModifiedBy(rs.getString(6));
	                dto.setCreatedDatetime(rs.getTimestamp(7));
	                dto.setModifiedDatetime(rs.getTimestamp(8));
	                
	                list.add(dto);
	            }
	            rs.close();
	        }
	        catch (Exception e) 
	        {
	            log.error("Database Exception..", e);
	            throw new ApplicationException("Exception : Exception in search college");
	        } 
	        finally
	        {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model search End");
	        return list;
	    }

	 
	 
	 
	 /**
	     * Get List of Course
	     *
	     * @return list : List of Course
	     * @throws ApplicationException
	     */


	 
	 
	 public List list() throws ApplicationException 
	 {
	        return list(0, 0);
	    }

	 
	  /**
	     * Get List of Course with pagination
	     *
	     * @return list : List of Course
	     * @param pageNo : Current Page No.
	     * @param pageSize : Size of Page
	     * @throws ApplicationException
	     */



	  public List list(int pageNo, int pageSize) throws ApplicationException
	  {
	        log.debug("Model list Started");
	        
	        ArrayList list = new ArrayList();
	        StringBuffer sql = new StringBuffer("select * from ST_COURSE");
	        
	        if (pageSize > 0) 
	        {
	            
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            
	            ResultSet rs = pstmt.executeQuery();
	            
	            while (rs.next()) 
	            {
	                CourseDTO dto = new CourseDTO();
	                dto.setId(rs.getLong(1));
	                dto.setName(rs.getString(2));
	                dto.setDescription(rs.getString(3));
	                dto.setDuration(rs.getString(4));
	                dto.setCreatedBy(rs.getString(5));
	                dto.setModifiedBy(rs.getString(6));
	                dto.setCreatedDatetime(rs.getTimestamp(7));
	                dto.setModifiedDatetime(rs.getTimestamp(8));
	                
	                list.add(dto);
	            }
	            
	            rs.close();
	        } 
	        catch (Exception e)
	        {
	            log.error("Database Exception..", e);
	            
	            throw new ApplicationException( "Exception : Exception in getting list of users");
	        }
	        finally
	        {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model list End");
	        return list;

	    }


public CourseDTO findByCourseName(String cname) throws ApplicationException
{
    log.debug("Model findByCourseName Started");
    StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE NAME=?");
    CourseDTO dto = null;
    
    Connection conn = null;
    try
    {
        conn = JDBCDataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        pstmt.setString(1,cname);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            dto = new CourseDTO();
            dto.setId(rs.getLong(1));
            dto.setName(rs.getString(2));
            dto.setDescription(rs.getString(3));
            dto.setDuration(rs.getString(4));
            dto.setCreatedBy(rs.getString(5));
            dto.setModifiedBy(rs.getString(6));
            dto.setCreatedDatetime(rs.getTimestamp(7));
            dto.setModifiedDatetime(rs.getTimestamp(8));
        }
        rs.close();
    } catch (Exception e) 
    {
        log.error("Database Exception..", e);
        throw new ApplicationException("Exception : Exception in getting User by cname");
    } finally 
    {
        JDBCDataSource.closeConnection(conn);
    }
    log.debug("Model findByCourseName End");
    return dto;
}


}