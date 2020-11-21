package aplication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Principal {

	public static void main(String[] args) throws ParseException, SQLException {

		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		
		Department dep = new Department("Avante tricoloss");
		
		ResultSet rs = depDao.insertReturningId(dep);
		
		while(rs.next()) {
			System.out.println(rs.getInt(1));
		}
	}

}
