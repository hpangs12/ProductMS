package com.productms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.productms.dto.ProductDTO;
import com.productms.entity.Product;
import com.productms.exception.ProductException;
import com.productms.repository.ProductRepository;
import com.productms.utility.KafkaUtitily;
import com.productms.utility.ProductUtility;

import jakarta.transaction.Transactional;

/***
 * ProductService : Class
 * 
 * Implementation of the ProductService Interface
 * 
 * Refer Interface for method description
 * */
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository; // Autowiring Repository
	
	@Autowired
	KafkaUtitily kafkaUtitily;
	
	@Override
	public Page<Product> getAllProducts(Pageable pageable) {

		return productRepository.findAll(pageable);
	}

	@Override
	public Product addProduct(ProductDTO productDTO) {

		return productRepository.save(ProductUtility.getProductEntity(productDTO));
	}
	
	@Override
	@CacheEvict(value = "products", key = "#id")
	public String deleteProduct(Integer id) throws ProductException {
		
		Optional<Product> optional = productRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new ProductException("Product Not Found!");
		}
		
		productRepository.deleteById(id);
		kafkaUtitily.productDeleted(id.toString());
		return "Deleted";
	}

	@Override
	@Cacheable(value = "products", key = "#id")
	public Product getProduct(Integer id) throws ProductException{
		
		Optional<Product> optional = productRepository.findById(id);
		Product product = optional.orElseThrow(() -> new ProductException("Product not found!"));
		
		return product;
	}

	@Override
	@CachePut(value = "products", key = "#productDTO.productId")
	public ProductDTO updateProduct(ProductDTO productDTO) throws ProductException {

		Optional<Product> optional = productRepository.findById(productDTO.getProductId());
		Product product = ProductUtility.updateEntity(optional.orElseThrow(
				() -> new ProductException("Product not found!")), productDTO);
		productRepository.save(product);
		
		return ProductUtility.getProductDTO(product);
	}

	@Override
	public Page<Product> searchProduct(String key, Pageable pageable) {

		return productRepository.searchByNameOrDesc(key, pageable);
	}

	@Override
	public Product patchProduct(Integer id, Map<String, Object> updates) throws ProductException {
		
		Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found"));
		
		if (updates.containsKey("productPrice")) {
			product.setProductPrice((Double)updates.get("productPrice"));
		}
		if (updates.containsKey("productImage")) {
			product.setProductImage(updates.get("productPrice").toString());
		}
		
		return productRepository.save(product);
	}
	
	@Override
	public Page<Product> filterProducts(String name, String desc, String category, Double minPrice, 
	Double maxPrice, Integer minQuantity, Pageable pageable) {
		
		return productRepository.filterProducts(name, desc, category, minPrice, maxPrice, minQuantity, pageable);
	}

	
	@Override
	@Cacheable(value = "instock", key = "#id")
	public boolean isInStock(Integer id) {
        return productRepository.findById(id)
                .map(product -> product.getProductQuantity() != null && product.getProductQuantity() > 0)
                .orElse(false);
    }
	
	@Transactional
	@Override
    public List<ProductDTO> saveAllProducts(List<Product> products) {
        return ProductUtility.toResponseList(productRepository.saveAll(products));
    }

}
