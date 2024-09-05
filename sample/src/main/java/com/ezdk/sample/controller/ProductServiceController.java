package com.ezdk.sample.controller;

import com.ezdk.sample.Middleware.ProductNotfoundException;
import com.ezdk.sample.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductServiceController {

    private static final Map<Long, Product> productRepo = new HashMap<>();

    static {
        Product honey = new Product();
        honey.setId(1L);
        honey.setName("Honey");
        productRepo.put(honey.getId(), honey);

        Product almond = new Product();
        almond.setId(2L);
        almond.setName("Almond");
        productRepo.put(almond.getId(), almond);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotfoundException();
        }
        productRepo.remove(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotfoundException();
        }
        product.setId(id);
        productRepo.put(id, product);
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        if (productRepo.containsKey(product.getId())) {
            return new ResponseEntity<>("Product with this ID already exists", HttpStatus.BAD_REQUEST);
        }
        productRepo.put(product.getId(), product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }
}
