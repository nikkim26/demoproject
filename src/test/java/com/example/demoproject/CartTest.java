package com.example.demoproject;


import com.example.demoproject.Controller.CartController;
import com.example.demoproject.Entity.Cart;
import com.example.demoproject.Service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class})
public class CartTest {
    @Mock
    private CartService cartService;
    @InjectMocks
    private CartController cartController;
    private MockMvc mockMvc;

    public CartTest() {
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new Object[]{this.cartController}).build();
    }

    @Test
    public void testGetCartByUserId() throws Exception {
        Cart sampleCart = new Cart();
        sampleCart.setCartId(1L);
        sampleCart.setUserId(1L);
        sampleCart.setTotalPrice(100.0);
        Mockito.when(this.cartService.getCartByUserId(1L)).thenReturn(ResponseEntity.ok(sampleCart));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/carts/1", new Object[0]).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        ((CartService)Mockito.verify(this.cartService)).getCartByUserId(1L);
    }

    @Test
    public void testCreateCart() throws Exception {
        Cart cartToCreate = new Cart();
        cartToCreate.setCartId(1L);
        cartToCreate.setUserId(1L);
        cartToCreate.setTotalPrice(100.0);
        Mockito.when(this.cartService.createCart((Cart)ArgumentMatchers.any(Cart.class))).thenReturn(cartToCreate);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/carts", new Object[0]).contentType(MediaType.APPLICATION_JSON).content((new ObjectMapper()).writeValueAsString(cartToCreate))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ((CartService)Mockito.verify(this.cartService)).createCart((Cart)ArgumentMatchers.any(Cart.class));
    }

    @Test
    public void testUpdateCart() throws Exception {
        Cart cartToUpdate = new Cart();
        cartToUpdate.setCartId(1L);
        cartToUpdate.setUserId(1L);
        cartToUpdate.setTotalPrice(150.0);
        Mockito.when(this.cartService.updateCart(ArgumentMatchers.eq(1L), (Cart)ArgumentMatchers.any(Cart.class))).thenReturn(ResponseEntity.ok(cartToUpdate));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/carts/1", new Object[0]).contentType(MediaType.APPLICATION_JSON).content((new ObjectMapper()).writeValueAsString(cartToUpdate))).andExpect(MockMvcResultMatchers.status().isOk());
        ((CartService)Mockito.verify(this.cartService)).updateCart(ArgumentMatchers.eq(1L), (Cart)ArgumentMatchers.any(Cart.class));
    }
}
