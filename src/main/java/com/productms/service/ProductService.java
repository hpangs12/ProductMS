package com.productms.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.productms.dto.ProductDTO;
import com.productms.entity.Product;
import com.productms.exception.ProductException;

/***
 * ProductService : Interface
 * 
 * Defines all the methods which will be used in ProductService
 * */
public interface ProductService {
	
	// Get all products with Pagination
	public Page<Product> getAllProducts(Pageable pageable);
	
	// Add a Product 
	public Product addProduct(ProductDTO productDTO);
	
	// Get a Product by ProductID
	public Product getProduct(Integer id) throws ProductException;
	
	// Delete a Product by ProductID
	public String deleteProduct(Integer id) throws ProductException;
	
	// Update a Product
	ProductDTO updateProduct(ProductDTO productDTO) throws ProductException;
	
	// Search for a Product -- By Name and Desc
	public Page<Product> searchProduct(String key, Pageable pageable);
	
	// Partially Update a Product
	Product patchProduct(Integer id, Map<String, Object> updates) throws ProductException;
	
	// Get Filtered Products
	Page<Product> filterProducts(String name, String desc, String category, Double minPrice, Double maxPrice,
			Integer minQuantity, Pageable pageable);
	
	// Check if product is in stock
	boolean isInStock(Integer productId);

	List<ProductDTO> saveAllProducts(List<Product> products);

	public void deductQuantity(Long prodId, Integer quantity) throws ProductException;

	Product getProductNoCache(Integer id) throws ProductException;
}
