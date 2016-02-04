package com.hkt.module.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;

public interface ProductRepository extends CrudRepository<Product, Long>, ProductRepositoryCustom {

	Product getByCode(String code);

	@Query("SELECT p FROM Product p WHERE  (LCASE(p.name) LIKE %:name% and p.recycleBin = 0)")
	List<Product> findProductByName(@Param("name") String name);
	
	@Query("SELECT p FROM Product p WHERE  (LCASE(p.description) = :description and p.recycleBin = 0)")
	List<Product> getProductByBarcode(@Param("description") String description);
	
	@Query("SELECT p FROM Product p WHERE  (LCASE(p.name) LIKE %:name% or LCASE(p.code) LIKE %:name% and p.recycleBin = 0)")
	List<Product> findProductByNameOrCode(@Param("name") String name);
	
	@Query("SELECT p FROM Product p INNER JOIN p.productTags t WHERE (t IN :tags and p.recycleBin = 0) GROUP BY p.id")
	List<Product> findByProductTags(@Param("tags") List<ProductTag> tags);

	@Query("SELECT p FROM Product p INNER JOIN p.productTags t WHERE (t.tag = :tag and p.recycleBin = 0) ORDER BY p.id DESC")
	List<Product> findByProductByTag(@Param("tag") String tag);

	@Query("SELECT p FROM Product p WHERE p.recycleBin = :valueBoolean")
	List<Product> findByProductRecycleBin(@Param("valueBoolean") boolean valueBoolean);
	
	@Modifying
	@Query("DELETE FROM Product WHERE productTags = :productTags")
	public void deleteByProductTag(@Param("productTags") List<ProductTag> productTags);

	@Modifying
	@Query("DELETE FROM Product WHERE name like %:test%")
	public void deleteTest(@Param("test") String test);

	@Modifying
	@Query("DELETE FROM ProductAttribute WHERE value like %:test%")
	public void deleteTestProductAttribute(@Param("test") String test);

}
