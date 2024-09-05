package com.ezdk.sample.controller;

import com.ezdk.sample.Middleware.ProductNotfoundException;
import com.ezdk.sample.entity.Product;
import com.ezdk.sample.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductServiceController {

    @Autowired
    private ProductRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        // Find the product by ID
        Product product = repo.findById(id).orElseThrow(ProductNotfoundException::new);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        if (!repo.existsById(id)) {
            throw new ProductNotfoundException();
        }
        repo.deleteById(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        if (!repo.existsById(id)) {
            throw new ProductNotfoundException();
        }
        product.setId(id);
        repo.save(product);
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        if (product.getId() != null && repo.existsById(product.getId())) {
            return new ResponseEntity<>("Product with this ID already exists", HttpStatus.BAD_REQUEST);
        }
        repo.save(product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }
}
