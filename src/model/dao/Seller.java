package model.dao;

import java.util.List;

public interface Seller {
	
	public void insert (Seller Seller);
	public void update (Seller Seller);
	public void deleteById (Integer id);
	public Seller findById(Integer id);
	public List<Seller> findAll();
	
}
