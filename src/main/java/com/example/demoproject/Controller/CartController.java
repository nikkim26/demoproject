package com.example.demoproject.Controller;



import com.example.demoproject.Entity.Cart;
import com.example.demoproject.Service.CartService;
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

@RestController
@RequestMapping({"/api/carts"})
public class CartController {
    @Autowired
    private CartService cartService;

    public CartController() {
    }

    @GetMapping({"/{userId}"})
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        return this.cartService.getCartByUserId(userId);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return this.cartService.createCart(cart);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        return this.cartService.updateCart(id, cart);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        return this.cartService.deleteCart(id);
    }
}
