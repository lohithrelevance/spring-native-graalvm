package com.lohith.graal.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.lohith.graal.entity.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, Integer>{

}
