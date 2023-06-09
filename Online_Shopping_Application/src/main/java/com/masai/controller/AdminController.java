package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.LoginException;
import com.masai.exception.ProductException;
import com.masai.exception.UserException;
import com.masai.model.CurrentUserSession;
import com.masai.model.Product;
import com.masai.service.AdminService;
import com.masai.service.LogInService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private AdminService adminService;

	private LogInService logService;

	private CurrentUserSession cs;

	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProductHandler(@Valid @RequestBody Product product, @RequestParam String uuid)
			throws LoginException, ProductException, UserException {

		this.setCs(logService.getSessionByUuid(uuid));

		if (cs == null)
			throw new LoginException("This user is not logged in");

		if (!cs.getRole().equalsIgnoreCase("admin"))
			throw new UserException("You are not allowed to add the product...!");

		product.setSellerId(cs.getUserId());

		Product p = adminService.addProduct(product);

		return new ResponseEntity<>(p, HttpStatus.CREATED);

	}

	@DeleteMapping("/removeProduct")
	public ResponseEntity<Product> removeProductHandler(@RequestParam Integer productId,
			@RequestParam String uuid) throws LoginException, ProductException, UserException {

		this.setCs(logService.getSessionByUuid(uuid));

		if (cs == null)
			throw new LoginException("This user is not logged in");

		if (!cs.getRole().equalsIgnoreCase("admin"))
			throw new UserException("You are not allowed to remove the product...!");

		Product p = adminService.removeProduct(productId, cs.getUserId());

		return new ResponseEntity<>(p, HttpStatus.CREATED);

	}

	@PutMapping("/updateProduct")
	public ResponseEntity<Product> updateProductHandler(@RequestBody Product product, @RequestParam String uuid)
			throws ProductException, LoginException, UserException {

		this.setCs(logService.getSessionByUuid(uuid));

		if (cs == null)
			throw new LoginException("This user is not logged in");

		if (!cs.getRole().equalsIgnoreCase("admin"))
			throw new UserException("You are not allowed to add the product...!");

//		Integer sellerId = cs.getUserId();
		Product p = adminService.updateProduct(product);

		return new ResponseEntity<>(p, HttpStatus.CREATED);

	}

	@Autowired
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	@Autowired
	public void setLogService(LogInService logService) {
		this.logService = logService;
	}

	public void setCs(CurrentUserSession cs) {
		this.cs = cs;
	}

}
