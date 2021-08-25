package com.lohith.graal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lohith.graal.entity.Product;
import com.lohith.graal.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("all")
	public Flux<Product> getAll() {
		return service.getAllProducts();
	}

	@GetMapping("{productId}")
	public Mono<ResponseEntity<Product>> getProductById(@PathVariable int productId) {
		return service.getProductById(productId).map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<Product> createProduct(@RequestBody Mono<Product> productMono) {
		return productMono.flatMap(service::createProduct);
	}

	@PutMapping("{productId}")
	public Mono<Product> updateProduct(@PathVariable int productId, @RequestBody Mono<Product> productMono) {
		return service.updateProduct(productId, productMono);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteProduct(@PathVariable int id) {
		return service.deleteProduct(id);
	}

}
