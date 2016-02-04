package com.hkt.module.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;

public interface ProductPriceRepository extends CrudRepository<ProductPrice, Long>, ProductPriceRepositoryCustom {
  
  @Query(value = "SELECT p FROM ProductPrice p JOIN p.productPriceType t WHERE (t.type = :type and p.recycleBin = 0) ORDER BY p.id DESC")
  List<ProductPrice> findByProductPriceType(@Param("type") String type);
  
  @Query(value = "SELECT p FROM ProductPrice p JOIN p.product t WHERE (t.code = :code and p.recycleBin = 0) ORDER BY p.id DESC")
  List<ProductPrice> findByProduct(@Param("code") String code);
  
  @Query(value = "SELECT p FROM ProductPrice p WHERE p.recycleBin = :valueBoolean ORDER BY p.id DESC")
  List<ProductPrice> findByProductRecycleBin(@Param("valueBoolean") boolean valueBoolean);
  
  @Query(value = "SELECT p FROM ProductPrice p JOIN p.product t JOIN p.productPriceType e WHERE " +
  		"t.code = :code and e.type = :type and p.recycleBin = 0 ORDER BY p.id DESC")
  ProductPrice getByProductAndProductPriceType(@Param("code") String code, @Param("type") String type);
  
  @Query(value = "SELECT p FROM ProductPrice p JOIN p.product t JOIN p.productPriceType e WHERE t.code = :code and e.type = :type "
      + "and p.currencyUnit = :currencyUnit and p.recycleBin = 0 ORDER BY p.id DESC")
  ProductPrice getByProductAndProductPriceType(@Param("code") String code, @Param("type") String type,@Param("currencyUnit") String currencyUnit);
  
  @Query(value = "SELECT p FROM ProductPrice p JOIN p.product t JOIN p.productPriceType h JOIN t.productTags e WHERE e.tag = :tag and "
  		+ "h.type = :type and p.currencyUnit = :currencyUnit and p.recycleBin = 0 ORDER BY p.id DESC")
  List<ProductPrice> findByProductPriceByTag(@Param("tag") String tag,@Param("type") String type,@Param("currencyUnit") String currencyUnit);
  
  @Query(value = "SELECT p FROM ProductPrice p JOIN p.product t WHERE (t.code =:code and p.recycleBin = 0) ORDER BY p.id DESC")
  List<ProductPrice> findByProductCode(@Param("code") String code);
  
  @Modifying
  @Query("SELECT p FROM ProductPrice p WHERE p.productPriceType = :productPriceType and p.recycleBin = 0 ORDER BY p.id DESC")
  List<ProductPrice> getProductPriceByProductPriceType(@Param("productPriceType") ProductPriceType productPriceType);
  
  @Modifying
  @Query(value = "DELETE FROM ProductPrice WHERE productPriceType = :productPriceType")
  public void deleteByProductPriceType(@Param("productPriceType") ProductPriceType productPriceType);
  
  @Modifying
  @Query(value = "DELETE FROM ProductPrice WHERE product = :product")
  public void deleteByProduct(@Param("product") Product product);
  
  @Modifying
  @Query("DELETE FROM ProductPrice WHERE product_id in (SELECT id FROM Product WHERE name like %:test%)")
  public void deleteTest(@Param("test") String test);
}