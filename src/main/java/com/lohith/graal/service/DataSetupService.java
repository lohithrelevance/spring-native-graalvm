package com.lohith.graal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.lohith.graal.entity.Product;
import com.lohith.graal.repo.ProductRepository;

import io.netty.util.internal.ThreadLocalRandom;
import reactor.core.publisher.Flux;

@Service
public class DataSetupService implements CommandLineRunner {
	
	@Autowired
    private ProductRepository repository;

	@Override
	public void run(String... args) throws Exception {

		Flux<Product> productFlux = Flux.range(1, 100)
				.map(i -> Product.create(i, "product "+ i , ThreadLocalRandom.current().nextInt(1, 100)))
				.flatMap(repository :: save);
		
		repository.deleteAll()
				.thenMany(productFlux)
				.doFinally(s -> System.out.println("Data setup done : " + s))
				.subscribe();
	}

}
