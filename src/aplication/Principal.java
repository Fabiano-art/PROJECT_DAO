package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Principal {

	public static void main(String[] args) {
		
		SellerDao selDao = DaoFactory.createSellerDao();
		
		Seller sel = selDao.findById(8);
		
		System.out.println(sel);
	}

}
