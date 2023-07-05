package com.example.onlineshopdemo.dao;

import com.example.onlineshopdemo.entity.Product;
import com.example.onlineshopdemo.entity.ProductOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("DELETE FROM Product p WHERE p.productOwner.id = :productOwnerId AND p.id = :productId")
    void deleteProduct(@Param("productOwnerId") UUID productOwnerId, @Param("productId") long productId);
}
