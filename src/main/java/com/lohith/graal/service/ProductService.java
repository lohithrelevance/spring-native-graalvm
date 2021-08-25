package com.lohith.graal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lohith.graal.entity.Product;
import com.lohith.graal.repo.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Flux<Product> getAllProducts() {
		return repository.findAll();
	}

	public Mono<Product> getProductById(int productId) {
		return repository.findById(productId);
	}

	public Mono<Product> createProduct(final Product product) {
		return repository.save(product);
	}

	public Mono<Product> updateProduct(int productId, final Mono<Product> productMono) {

		return repository.findById(productId).flatMap(p -> productMono.map(u -> {
			p.setDescription(u.getDescription());
			p.setPrice(u.getPrice());
			return p;
		})).flatMap(p -> repository.save(p));
	}

	public Mono<Void> deleteProduct(final int id) {
		return repository.deleteById(id);
	}
}
