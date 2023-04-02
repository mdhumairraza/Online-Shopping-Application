package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CartException;
import com.masai.model.Cart;
import com.masai.model.Product;
import com.masai.repository.CartDao;


@Service
public class CartServiceImpl implements CartService{
	    
	
	
		@Autowired
	    private CartDao cartDao;

		
		
	    @Override
	    public Cart addProductToCart(Cart cart, Product p, int quantity) throws CartException {

	        Optional<Cart> byId = cartDao.findById(cart.getCartId());

	        if (byId.isPresent()) {
	        
	        	Cart cart1 = byId.get();
	            
	        	cart1.getProductList().add(p);
	            
	        	cartDao.save(cart1);
	            
	        	return cart1;
	        
	        }
	        
	        else throw new CartException("No Cart Found with this Id"+cart.getCartId());
	    }

	    
	    
	    @Override
	    public Cart removeProductFromCart(Cart cart, Product p) throws CartException {

	        Optional<Cart> byId = cartDao.findById(cart.getCartId());

	        if (byId.isPresent()) {
	            
	        	Cart cart1 = byId.get();
	            
	        	cart1.getProductList().remove(p);
	            
	        	cartDao.save(cart1);
	            
	        	return cart1;
	        
	        }
	        
	        else throw new CartException("No Cart Found with this Id"+cart.getCartId());
	    
	    }

	    
	    
	    
	    @Override
	    public Cart updateProductQuantity(Cart cart, Product p, int quantity) throws CartException {

	        Optional<Cart> byId = cartDao.findById(cart.getCartId());
	        if (byId.isPresent()) {
	            Cart cart1 = byId.get();
	            List<Product> pl = cart1.getProductList();
	            
	            for(Product product:pl) {
	            	
	            	if(product.getProductId().equals(p.getProductId())) {
	            		product.setQuantity(p.getQuantity());
	            	
	            	}
	            }
	            
	            
	            cartDao.save(cart1);
	            
	            return cart1;
	        }
	        else throw new CartException("No Cart Found with this Id"+cart.getCartId());
	    }

	    
	    
	    
	    @Override
	    public Cart removeAllProducts(Cart cart) throws CartException {

	    	Optional<Cart> byId = cartDao.findById(cart.getCartId());
	        
	    	if (byId.isPresent()) {
	        
	    		Cart cart1 = byId.get();
	            
	    		cart1.getProductList().clear();
	            
	    		cartDao.save(cart1);
	            
	    		return cart1;
	        }
	       
	    	else throw new CartException("No Cart Found with this Id"+cart.getCartId());
	    }

	    
	    
	    @Override
	    public Cart viewAllProducts(Cart cart) throws CartException {
	        
	    	Optional<Cart> byId = cartDao.findById(cart.getCartId());
	        
	    	if (byId.isPresent()) {
	        
	    		Cart cart1 = byId.get();
	            
	    		return cart1;
	        
	    	}
	        
	    	
	    	else throw new CartException("No Cart Found with this Id"+cart.getCartId());
	    }	
	    
	    
}




