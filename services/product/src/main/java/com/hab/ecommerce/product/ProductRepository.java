package com.hab.ecommerce.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.annotation.Native;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p WHERE id IN :productIds")
    List<Product> findByListOfIds(List<Integer> productIds);

//    @Query("SELECT p FROM Product p WHERE id:*=ids")
//    List<Product> findAllByIdInOrderById(List<Integer> ids);

}
