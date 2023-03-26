package com.example.onlineshopdemo.dao;

import com.example.onlineshopdemo.entity.ProductOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductOwnerRepository extends JpaRepository<ProductOwner, UUID> {
    Optional<ProductOwner> findByEmail(String email);
}

