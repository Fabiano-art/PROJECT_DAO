package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void insert(Seller seller) {
			
		PreparedStatement ps = null;
		
		try {
			
			String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES(?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getid());
			
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public void update(Seller seller) {

	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT seller.*,department.Name as DepName\r\n" + "FROM seller INNER JOIN department\r\n"
					+ "ON seller.DepartmentId = department.Id\r\n" + "WHERE seller.Id = ?";
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
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeOperations(ps, rs);
		}

		return null;
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "select seller.*, department.Id, department.name as depName "
					+ "from seller join department on seller.DepartmentId = department.Id";
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Department dep = new Department();
			
			while (rs.next()) {
				Seller sel = new Seller();
				
				sel.setId(rs.getInt("Id"));
				sel.setName(rs.getString("Name"));
				sel.setEmail(rs.getString("Email"));
				sel.setBaseSalary(rs.getDouble("BaseSalary"));
				sel.setBirthDate(rs.getDate("BirthDate"));
				
				dep.setid(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				sel.setDepartment(dep);
				
				list.add(sel);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name";
					
			ps = con.prepareStatement(sql);
			ps.setInt(1, department.getid());

			rs = ps.executeQuery();

			List<Seller>  list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller sel = new Seller();

				sel.setId(rs.getInt("Id"));
				sel.setName(rs.getString("Name"));
				sel.setEmail(rs.getString("Email"));
				sel.setBaseSalary(rs.getDouble("BaseSalary"));
				sel.setBirthDate(rs.getDate("BirthDate"));
				sel.setDepartment(dep);

				list.add(sel);
				
			}
			return list;
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeOperations(ps, rs);
		}
		
	}

}
