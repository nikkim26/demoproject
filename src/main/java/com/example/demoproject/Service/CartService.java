package com.example.demoproject.Service;



import java.util.Optional;

import com.example.demoproject.Entity.Cart;
import com.example.demoproject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public CartService() {
    }

    public ResponseEntity<Cart> getCartByUserId(Long userId) {
        Optional<Cart> cart = this.cartRepository.findById(userId);
        return (ResponseEntity)cart.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public Cart createCart(Cart cart) {
        return (Cart)this.cartRepository.save(cart);
    }

    public ResponseEntity<Cart> updateCart(Long id, Cart cartDetails) {
        return (ResponseEntity)this.cartRepository.findById(id).map((cart) -> {
            cart.setUserId(cartDetails.getUserId());
            cart.setItems(cartDetails.getItems());
            cart.setTotalPrice(cartDetails.getTotalPrice());
            return ResponseEntity.ok((Cart)this.cartRepository.save(cart));
        }).orElseThrow(() -> {
            return new RuntimeException("Cart Items not found");
        });
    }

    public ResponseEntity<Void> deleteCart(Long id) {
        return (ResponseEntity)this.cartRepository.findById(id).map((cart) -> {
            this.cartRepository.delete(cart);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> {
            return new RuntimeException("Cart Items not found");
        });
    }
}
