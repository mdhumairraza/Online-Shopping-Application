package com.masai.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.masai.exception.ProductException;
import com.masai.model.Category;
import com.masai.model.Product;
import com.masai.repository.CategoryRepo;
import com.masai.repository.ProductRepo;


@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepo prodRepo;

	private CategoryRepo cRepo;
	
	@Autowired
	public void setProdRepo(ProductRepo prodRepo) {
		this.prodRepo = prodRepo;
	}

	@Autowired
	public void setcRepo(CategoryRepo cRepo) {
		this.cRepo = cRepo;
	}

	@Override
	public List<Product> viewAllProducts() throws ProductException {

		List<Product> products = prodRepo.findAll();
		if (products.size() == 0) {
			throw new ProductException("No Product are there");
		} else {
			return products;
		}
	}

	@Override
	public Product viewProduct(Integer id) throws ProductException {

		Optional<Product> opt = prodRepo.findById(id);
		if (opt.isPresent()) {
			Product viewProduct = opt.get();
			return viewProduct;
		} else {
			throw new ProductException("Product not Found!......");
		}
	}

	@Override
	public List<Product> viewProductByCategory(String cname) throws ProductException {

		Category category = cRepo.findByCategory(cname);

		if (category == null)
			throw new ProductException("No Product found in this category");
		List<Product> products = category.getProducts();

		if (products.isEmpty())
			throw new ProductException("No Product found in this category");

		return products;

	}

	@Override
	public List<Product> viewProductByProductName(String productName) throws ProductException {
		List<Product> products = prodRepo.findByProductName(productName);
		if (products.isEmpty())
			throw new ProductException("No product found with this name");
		return products;
	}

}