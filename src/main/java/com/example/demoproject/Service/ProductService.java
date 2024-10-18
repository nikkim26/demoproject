package com.example.demoproject.Service;


import java.util.List;

import com.example.demoproject.Entity.Product;
import com.example.demoproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public ResponseEntity<Product> getProductById(Long id) {
        return (ResponseEntity)this.productRepository.findById(id).map((product) -> {
            return ResponseEntity.ok().body(product);
        }).orElse(ResponseEntity.notFound().build());
    }

    public Product createProduct(Product product) {
        return (Product)this.productRepository.save(product);
    }

    public ResponseEntity<Product> updateProduct(Long id, Product productDetails) {
        return (ResponseEntity)this.productRepository.findById(id).map((product) -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            return ResponseEntity.ok((Product)this.productRepository.save(product));
        }).orElseThrow(() -> {
            return new RuntimeException("Product not found");
        });
    }

    public ResponseEntity<Void> deleteProduct(Long id) {
        return (ResponseEntity)this.productRepository.findById(id).map((product) -> {
            this.productRepository.delete(product);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> {
            return new RuntimeException("Product not found");
        });
    }
}