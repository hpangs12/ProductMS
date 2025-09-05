package com.productms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.productms.entity.Product;

/***
 * ProductRepository : Interface
 * 
 * Repository for Product -- Extends JPA Repository
 * 
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query("SELECT p FROM Product p " +
		       "WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "   OR LOWER(p.productDesc) LIKE LOWER(CONCAT('%', :key, '%'))")
	Page<Product> searchByNameOrDesc(String key, Pageable pageable);
	
	
	@Query("SELECT p FROM Product p " +
	           "WHERE p.productQuantity > 0 " + 
	           "AND (:name IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))) " +
	           "AND (:desc IS NULL OR LOWER(p.productDesc) LIKE LOWER(CONCAT('%', :desc, '%'))) " +
	           "AND (:category IS NULL OR p.productCategory = :category) " +
	           "AND (:minPrice IS NULL OR p.productPrice >= :minPrice) " +
	           "AND (:maxPrice IS NULL OR p.productPrice <= :maxPrice) " +
	           "AND (:minQuantity IS NULL OR p.productQuantity >= :minQuantity)")
	    Page<Product> filterProducts(@Param("name") String name,
	                                 @Param("desc") String desc,
	                                 @Param("category") String category,
	                                 @Param("minPrice") Double minPrice,
	                                 @Param("maxPrice") Double maxPrice,
	                                 @Param("minQuantity") Integer minQuantity,
	                                 Pageable pageable);

}
