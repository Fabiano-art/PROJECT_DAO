package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection con;
	
	public SellerDaoJDBC(Connection con) {
		this.con = con;
	}
	
	@Override
	public void insert(Seller Seller) {
		
		
	}

	@Override
	public void update(Seller Seller) {
		
		
	}

	@Override
	public void deleteById(Integer id) {
		
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		try {
			String sql = "SELECT seller.*,department.Name as DepName\r\n" + 
					"FROM seller INNER JOIN department\r\n" + 
					"ON seller.DepartmentId = department.Id\r\n" + 
					"WHERE seller.Id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				Department dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
				
				Seller sel = new Seller();
				
				sel.setId(rs.getInt("Id"));
				sel.setName(rs.getString("Name"));
				sel.setEmail(rs.getString("Email"));
				sel.setBaseSalary(rs.getDouble("BaseSalary"));
				sel.setBirthDate(rs.getDate("BirthDate"));
				sel.setDepartment(dep);
				
				return sel;
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeOperations(ps, rs);
		}
		
		return null;
	}

	@Override
	public List<Seller> findAll() {
		
		return null;
	}

}
