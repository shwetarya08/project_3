package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of Marksheet Model
 *
 * @author SunilOS
 * @version 1.0
 
 */
public class MarksheetModel {

	Logger log=Logger.getLogger(MarksheetModel.class);
	
	 /**
     * Find next PK of Marksheet
     *
     * @throws DatabaseException
     */
	
	public Integer nextPK() throws DatabaseException
	{
		log.debug("Model nextPK Started");
		
		Connection conn=null;
		
		int pk=0;
		
		try {
			conn=JDBCDataSource.getConnection();
			
			System.out.println("Connection successfully establish");
			
			PreparedStatement pstmt=conn.prepareStatement("SELECT MAX(ID) FROM ST_MARKSHEET");
			
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				pk=rs.getInt(1);
				
			}
			rs.close();
			
		} 
		
		catch (Exception e) 
			{
				log.error(e);
				throw new DatabaseException("Exception in Marksheet getting PK");
			}
				
		finally
			{
				
				JDBCDataSource.closeConnection(conn);	
					
			}
		
		log.debug("Model nextPK end");
		
		return pk+1;
	}
	
	
	/**
     * Adds a Marksheet
     *
     
     * @throws ApplicationException DuplicateRecordException
		* @return dto
     *
     */

	
	public long add(MarksheetDTO dto)throws ApplicationException,DuplicateRecordException
		{
			log.debug("Model add Started");
			
			Connection conn=null;
			
				StudentModel sModel=new StudentModel();
			    
				StudentDTO studentdto = sModel.findByPK(dto.getStudentId());
			    
				dto.setName(studentdto.getFirstName()+" "+studentdto.getLastName());
			   
		
		MarksheetDTO duplicateMarksheet=findByRollNo(dto.getRollNo());
		
		int pk=0;
		
		if(duplicateMarksheet !=null)
		{
			throw new DuplicateRecordException("Roll Number already exist");
		}
		try {
			conn=JDBCDataSource.getConnection();
			
			pk=nextPK();
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt=conn.prepareStatement("INSERT INTO ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			
			    pstmt.setInt(1, pk);
	            pstmt.setString(2, dto.getRollNo());
	            pstmt.setLong(3, dto.getStudentId());
	            pstmt.setString(4, dto.getName());
	            pstmt.setInt(5, dto.getPhysics());
	            pstmt.setInt(6, dto.getChemistry());
	            pstmt.setInt(7, dto.getMaths());
	            pstmt.setString(8, dto.getCreatedBy());
	            pstmt.setString(9, dto.getModifiedBy());
	            pstmt.setTimestamp(10, dto.getCreatedDatetime());
	            pstmt.setTimestamp(11, dto.getModifiedDatetime());
	            pstmt.executeUpdate();
	            conn.commit(); 
	            pstmt.close();
	        }
		catch (Exception e)
		{
	            log.error(e);
	            try
	            {
	            	e.printStackTrace();
	                conn.rollback();
	            }
	            catch (Exception ex) 
	            {
	                throw new ApplicationException("add rollback exception "+ ex.getMessage());
	            }
	            throw new ApplicationException("Exception in add marksheet");
	        } 
	       finally
		     {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model add End");
	        return pk;
	    }

	
	   /**
     * Deletes a Marksheet
     *
   
     * @throws ApplicationException
     */
	
	  public void delete(MarksheetDTO dto) throws ApplicationException 
	  {

	        log.debug("Model delete Started");

	        Connection conn = null;
	        try 
	        {
	            conn = JDBCDataSource.getConnection();
	            
	            conn.setAutoCommit(false); 
	            
	            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_MARKSHEET WHERE ID=?");
	            
	            pstmt.setLong(1, dto.getId());
	            
	            System.out.println("Deleted MarkSheet");
	            
	            pstmt.executeUpdate();
	            
	            conn.commit(); 
	            pstmt.close();

	        } 
	        catch (Exception e) 
	        {
	            log.error(e);
	            try
		            {
		              //  conn.rollback();
		            }
	            catch (Exception ex) 
		            {
		                log.error(ex);
		                throw new ApplicationException("Delete rollback exception "+ ex.getMessage());
		            }
	            throw new ApplicationException("Exception in delete marksheet");
	        } 
	        
	        finally
		        {
		            JDBCDataSource.closeConnection(conn);
		        }

	        log.debug("Model delete End");
	    }
	  
	  
	  /**
	     * Finds Marksheet by Roll No
	     *
	  
	     * @return dto
	     * @throws ApplicationException
	 
	     */
	  
	  public MarksheetDTO findByRollNo(String rollNo)throws ApplicationException
	  {
	        log.debug("Model findByRollNo Started");

	        StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ROLL_NO=?");
	        
	        MarksheetDTO dto = null;
	        Connection conn = null;
	        try
	        {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            pstmt.setString(1, rollNo);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) 
	            {
	                dto = new MarksheetDTO();
	                dto.setId(rs.getLong(1));
	                dto.setRollNo(rs.getString(2));
	                dto.setStudentId(rs.getLong(3));
	                dto.setName(rs.getString(4));
	                dto.setPhysics(rs.getInt(5));
	                dto.setChemistry(rs.getInt(6));
	                dto.setMaths(rs.getInt(7));
	                dto.setCreatedBy(rs.getString(8));
	                dto.setModifiedBy(rs.getString(9));
	                dto.setCreatedDatetime(rs.getTimestamp(10));
	                dto.setModifiedDatetime(rs.getTimestamp(11));
	            }
	            rs.close();
	        } catch (Exception e)
	        {
	            log.error(e);
	            throw new ApplicationException("Exception in getting marksheet by roll no");
	        } finally 
	        {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model findByRollNo End");
	        return dto;
	    }
	  
	  /**
	     * Finds Marksheet by PK
	     *
	  
	     * @return dto
	     * @throws ApplicationException 

	     */
	  
	  
	  public MarksheetDTO findByPK(long pk) throws ApplicationException 
	  {
	        log.debug("Model findByPK Started");

	        StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ID=?");
	        MarksheetDTO dto = null;
	        Connection conn = null;
	        
	        try
	        {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            pstmt.setLong(1, pk);
	            
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) 
	            {
	                dto = new MarksheetDTO();
	                dto.setId(rs.getLong(1));
	                dto.setRollNo(rs.getString(2));
	                dto.setStudentId(rs.getLong(3));
	                dto.setName(rs.getString(4));
	                dto.setPhysics(rs.getInt(5));
	                dto.setChemistry(rs.getInt(6));
	                dto.setMaths(rs.getInt(7));
	                dto.setCreatedBy(rs.getString(8));
	                dto.setModifiedBy(rs.getString(9));
	                dto.setCreatedDatetime(rs.getTimestamp(10));
	                dto.setModifiedDatetime(rs.getTimestamp(11));

	            }
	            rs.close();
	        } catch (Exception e) 
	        {
	            log.error(e);
	            throw new ApplicationException("Exception in getting marksheet by pk");
	        } 
	        finally 
	        {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model findByPK End");
	        return dto;
	    }

	  
	  
	    /**
	     * Updates a Marksheet
	     *

	  
	     * @throws ApplicationException  DuplicateRecordException

	     */

	  
	  public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException
			  {
		
		  log.debug("Model update Started");
		
		  Connection conn = null;
		  MarksheetDTO dtoExist = findByRollNo(dto.getRollNo());
		
		  
		  if (dtoExist != null && dtoExist.getId() != dto.getId()) 
		  {
		      throw new DuplicateRecordException("Roll No is already exist");
		  }
		
  
	  StudentModel sModel = new StudentModel();
	  StudentDTO studentdto = sModel.findByPK(dto.getStudentId());
	  dto.setName(studentdto.getFirstName() + " " + studentdto.getLastName());
	
	  try 
	  {
      conn = JDBCDataSource.getConnection();

      conn.setAutoCommit(false); 
      PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
      pstmt.setString(1, dto.getRollNo());
      pstmt.setLong(2, dto.getStudentId());
      pstmt.setString(3, dto.getName());
      pstmt.setInt(4, dto.getPhysics());
      pstmt.setInt(5, dto.getChemistry());
      pstmt.setInt(6, dto.getMaths());
      pstmt.setString(7, dto.getCreatedBy());
      pstmt.setString(8, dto.getModifiedBy());
      pstmt.setTimestamp(9, dto.getCreatedDatetime());
      pstmt.setTimestamp(10, dto.getModifiedDatetime());
      
      pstmt.setLong(11, dto.getId());
     
      pstmt.executeUpdate();
      conn.commit(); 
      pstmt.close();
      
  }
	  
	  catch (Exception e) 
	  {
		  e.printStackTrace();
		  
		  
      log.error(e);
      
      
      try 
      {
    	  
          conn.rollback();
      } 
      
      catch (Exception ex) 
      {
          throw new ApplicationException("Update rollback exception "+ ex.getMessage());
      }
      throw new ApplicationException("Exception in updating Marksheet ");
  
  } 	finally
  {
      JDBCDataSource.closeConnection(conn);
  }

  log.debug("Model update End");

}


	    /**
	     * Searches Marksheet
	     *
	     * @param dto: Search Parameters
	     * @throws ApplicationException
	     */
	  
	  public List search(MarksheetDTO dto) throws ApplicationException 
	  {
	        return search(dto, 0, 0);
	    }
	  
	  /**
	     * Searches Marksheet with pagination
	     *
	     * @return list : List of Marksheets
	     * @param dto
	     *            : Search Parameters
	     * @param pageNo
	     *            : Current Page No.
	     * @param pageSize
	     *            : Size of Page
	     *
	     * @throws ApplicationException
	     */

	  
	  
	  public List search(MarksheetDTO dto, int pageNo, int pageSize)throws ApplicationException
	  {

	        log.debug("Model  search Started");

	        StringBuffer sql = new StringBuffer("select * from ST_MARKSHEET where 1=1");

	        if (dto != null)
	        {
	            System.out.println("service" + dto.getName());
	           
	            if (dto.getId() > 0) 
	            {
	                sql.append(" AND id = " + dto.getId());
	            }
	            if (dto.getRollNo() != null && dto.getRollNo().length() > 0)
	            {
	                sql.append(" AND roll_no like '" + dto.getRollNo() + "%'");
	            }
	            if (dto.getName() != null && dto.getName().length() > 0) 
	            {
	                sql.append(" AND name like '" + dto.getName() + "%'");
	            }
	            if (dto.getPhysics() == 0 && dto.getPhysics() > 0) 
	            {
	                sql.append(" AND physics = " + dto.getPhysics());
	            }
	            if (dto.getChemistry() == 0 && dto.getChemistry() > 0)
	            {
	                sql.append(" AND chemistry = " + dto.getChemistry());
	            }
	            if (dto.getMaths() !=0 && dto.getMaths() > 0) 
	            {
	                sql.append(" AND maths = '" + dto.getMaths());
	            }
	            	
	        }

	        if (pageSize > 0)
	        	        {
	            
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            
	        }

	        ArrayList list = new ArrayList();
	        
	        Connection conn = null;
	        try 
	        {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	           
	            ResultSet rs = pstmt.executeQuery();
	            
	            while (rs.next())
	            {
	                dto = new MarksheetDTO();
	                dto.setId(rs.getLong(1));
	                dto.setRollNo(rs.getString(2));
	                dto.setStudentId(rs.getLong(3));
	                dto.setName(rs.getString(4));
	                dto.setPhysics(rs.getInt(5));
	                dto.setChemistry(rs.getInt(6));
	                dto.setMaths(rs.getInt(7));
	                dto.setCreatedBy(rs.getString(8));
	                dto.setModifiedBy(rs.getString(9));
	                dto.setCreatedDatetime(rs.getTimestamp(10));
	                dto.setModifiedDatetime(rs.getTimestamp(11));
	                list.add(dto);
	            }
	            rs.close();
	        } catch (Exception e) 
	        {
	            log.error(e);
	            throw new ApplicationException("Update rollback exception "+ e.getMessage());
	        } finally
	        {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model  search End");
	        return list;
	    }
	  
	  
	  
	  /**
	     * Gets List of Marksheet
	     *
	     * @return list : List of Marksheets
	     * @throws ApplicationException

	     */
	  
	  public List list() throws ApplicationException
	  {
	        return list(0, 0);
	    }
	
	  
	   /**
	     * get List of Marksheet with pagination
	     *
	     * @return list : List of Marksheets
	     * @param pageNo
	     *            : Current Page No.
	     * @param pageSize
	     *            : Size of Page
	     * @throws ApplicationException

	     */
	  
	  public List list(int pageNo, int pageSize) throws ApplicationException {

	        log.debug("Model  list Started");

	        ArrayList list = new ArrayList();
	        StringBuffer sql = new StringBuffer("select * from ST_MARKSHEET");
	       
	        if (pageSize > 0)
	        {
	            
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try 
	        {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) 
	            {
	                MarksheetDTO dto = new MarksheetDTO();
	                dto.setId(rs.getLong(1));
	                dto.setRollNo(rs.getString(2));
	                dto.setStudentId(rs.getLong(3));
	                dto.setName(rs.getString(4));
	                dto.setPhysics(rs.getInt(5));
	                dto.setChemistry(rs.getInt(6));
	                dto.setMaths(rs.getInt(7));
	                dto.setCreatedBy(rs.getString(8));
	                dto.setModifiedBy(rs.getString(9));
	                dto.setCreatedDatetime(rs.getTimestamp(10));
	                dto.setModifiedDatetime(rs.getTimestamp(11));
	                list.add(dto);
	            }
	            rs.close();
	        }
	        catch (Exception e)
	        {
	            log.error(e);
	            throw new ApplicationException("Exception in getting list of Marksheet");
	        } finally
	        {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model  list End");
	        return list;

	    }

	  /**
	     * get Merit List of Marksheet with pagination
	     *
	     * @return list : List of Marksheets
	     * @param pageNo
	     *            : Current Page No.
	     * @param pageSize
	     *            : Size of Page
	     * @throws ApplicationException
	     */

	  
	  
	  public List getMeritList(int pageNo,int pageSize)throws ApplicationException
	  {
		  log.debug("Model MeritList Started");
		  ArrayList list=new ArrayList();
		 
		  StringBuffer sql=new StringBuffer( "SELECT `ID`,`ROLL_NO`, `NAME`, `PHYSICS`, `CHEMISTRY`, `MATHS` , (PHYSICS + CHEMISTRY + MATHS) as total from `ST_MARKSHEET` where (physics>33 or chemistry>33 or maths>33) order by total desc");

		  if (pageSize > 0) 
		  {
	            
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try 
	        {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next())
	            {
	                MarksheetDTO dto = new MarksheetDTO();
	                dto.setId(rs.getLong(1));
	                dto.setRollNo(rs.getString(2));
	                dto.setName(rs.getString(3));
	                dto.setPhysics(rs.getInt(4));
	                dto.setChemistry(rs.getInt(5));
	                dto.setMaths(rs.getInt(6));
	                list.add(dto);
	            }
	            rs.close();
	        } catch (Exception e)
	        {
	            log.error(e);
	            throw new ApplicationException("Exception in getting merit list of Marksheet");
	        } finally 
	        {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model  MeritList End");
	        return list;
	    }

	}


	  

