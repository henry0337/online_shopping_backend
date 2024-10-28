package com.henry.online_shopping.repository;

import com.henry.online_shopping.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {}
