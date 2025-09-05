package com.productms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.productms.dto.ProductDTO;
import com.productms.entity.Product;
import com.productms.exception.ProductException;
import com.productms.repository.ProductRepository;
import com.productms.service.ProductService;
import com.productms.service.ProductServiceImpl;

@SpringBootTest
public class ProductServiceTests {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	
	@Test
	public void testGetProduct() throws ProductException {
		
		// Arrange
	    Product expectedProduct = new Product();
	    when(productRepository.findById(anyInt())).thenReturn(Optional.of(expectedProduct));

	    // Act
	    Optional<Product> actualProductOpt = productRepository.findById(101);

	    // Assert
	    assertTrue(actualProductOpt.isPresent());
	    Product actualProduct = actualProductOpt.get();

	    assertEquals(expectedProduct.getProductId(), actualProduct.getProductId());
	    assertEquals(expectedProduct.getProductName(), actualProduct.getProductName());
	    assertEquals(expectedProduct.getProductDesc(), actualProduct.getProductDesc());
	    assertEquals(expectedProduct.getProductPrice(), actualProduct.getProductPrice(), 0.001);
	    assertEquals(expectedProduct.getProductImage(), actualProduct.getProductImage());
		
	}
	
//	public Product addProduct(ProductDTO productDTO) {
//		// TODO Auto-generated method stub
//		return productRepository.save(ProductDTO.getProductEntity(productDTO));
//	}
//	
//	@Override
//	public String deleteProduct(Integer id) {
//		productRepository.deleteById(id);
//		return "Deleted";
//	}
//
//	@Override
//	public Product getProduct(Integer id) throws ProductException{
//		
//		Optional<Product> optional = productRepository.findById(id);
//		Product product = optional.orElseThrow(() -> new ProductException("Product not found!"));
//		
//		return product;
//	}
//
//	@Override
//	public List<Product> searchProduct(String productName) {

}
