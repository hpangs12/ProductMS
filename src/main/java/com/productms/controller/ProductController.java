package com.productms.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productms.dto.ProductDTO;
import com.productms.entity.Product;
import com.productms.exception.ProductException;
import com.productms.service.ProductService;

/***
 * ProductController : Class
 * 
 * REST Controller for product operations
 * 
 */
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<Page<Product>> getAllProducts(
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "productId") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
            ){
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Page<Product> fetchedPage = productService.getAllProducts(PageRequest.of(page, size, sort));
		
		return new ResponseEntity<>(fetchedPage, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
		return  new ResponseEntity<ProductDTO>(productDTO, HttpStatusCode.valueOf(201));
	}
	
	@PostMapping("/bulk")
    public ResponseEntity<List<ProductDTO>> saveAll(@RequestBody List<Product> products) {
        return new ResponseEntity<List<ProductDTO>>(productService.saveAllProducts(products), HttpStatusCode.valueOf(201));
    }
	
	@PutMapping
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws ProductException {
		return  new ResponseEntity<ProductDTO>(productService.updateProduct(productDTO), HttpStatusCode.valueOf(200));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) throws ProductException {
		
		return new ResponseEntity<String>(productService.deleteProduct(id), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) throws Exception {
		return new ResponseEntity<Product>(productService.getProduct(id), HttpStatus.OK);
	}
	
	@GetMapping("/nocache/{id}")
	public ResponseEntity<Product> getProductNoCache(@PathVariable("id") Integer id) throws Exception {
		return new ResponseEntity<Product>(productService.getProductNoCache(id), HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<Product>> searchProduct(
			@RequestParam(required = true, name = "q") String key, 
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "productId") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending){
		
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Page<Product> fetchedPage = productService.searchProduct(key, PageRequest.of(page, size, sort));
		
		return new ResponseEntity<Page<Product>>(fetchedPage, HttpStatus.OK);
	}
	
	
	@PatchMapping("/{id}")
	public Product patchProduct(@PathVariable Integer id, @RequestBody Map<String, Object> updates) throws ProductException {
	    
	    return productService.patchProduct(id, updates);
	}
	
	@GetMapping("/filter")
    public Page<Product> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minQuantity,
            Pageable pageable) {

        return productService.filterProducts(name, desc, category, minPrice, maxPrice, minQuantity, pageable);
    }
	
	@GetMapping("/{id}/in-stock")
    public ResponseEntity<Boolean> isInStock(@PathVariable Integer id) {
        boolean inStock = productService.isInStock(id);
        return ResponseEntity.ok(inStock);
    }
	
}
