package com.productms.utility;

import java.util.List;

import com.productms.dto.ProductDTO;
import com.productms.entity.Product;


/***
 * ProductUtility : Class
 * 
 * Utility Class for Product
 * 
 */
public class ProductUtility {
	
	public static ProductDTO getProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(product.getProductId());
		productDTO.setProductName(product.getProductName());
		productDTO.setProductPrice(product.getProductPrice());
		productDTO.setProductDesc(product.getProductDesc());
		productDTO.setProductImage(product.getProductImage());
		return productDTO;
	}
	
	public static Product getProductEntity(ProductDTO productDTO) {
		Product product = new Product();
		product.setProductId(productDTO.getProductId());
		product.setProductName(productDTO.getProductName());
		product.setProductPrice(productDTO.getProductPrice());
		product.setProductDesc(productDTO.getProductDesc());
		product.setProductImage(productDTO.getProductImage());
		return product;
	}
	
	public static List<ProductDTO> toResponseList(List<Product> products) {
        return products.stream()
                .map(ProductUtility::getProductDTO)
                .toList();
    }
	
	public static Product updateEntity(Product product, ProductDTO dto) {
		
		// Validations for fields will come here
		if(dto.getProductName()!="" && product.getProductName()!=dto.getProductName()) product.setProductName(dto.getProductName());
		if(dto.getProductPrice()>0 && product.getProductPrice()!=dto.getProductPrice()) product.setProductPrice(dto.getProductPrice());
		if(dto.getProductDesc()!="" && product.getProductDesc()!=dto.getProductDesc()) product.setProductDesc(dto.getProductDesc());
		if(dto.getProductImage()!="" && product.getProductImage()!=dto.getProductImage()) product.setProductImage(dto.getProductImage());
		
		return product;
	}

}
