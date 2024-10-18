package com.example.demoproject;


import com.example.demoproject.Controller.ProductController;
import com.example.demoproject.Entity.Product;
import com.example.demoproject.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({MockitoExtension.class})
public class ProductTest {
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;
    private MockMvc mockMvc;

    public ProductTest() {
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new Object[]{this.productController}).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(100.0);
        product1.setStockQuantity(10);
        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(200.0);
        product2.setStockQuantity(20);
        List<Product> sampleProducts = Arrays.asList(product1, product2);
        Mockito.when(this.productService.getAllProducts()).thenReturn(sampleProducts);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products", new Object[0]).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ((ProductService)Mockito.verify(this.productService)).getAllProducts();
    }

    @Test
    public void testGetProductById() throws Exception {
        Product sampleProduct = new Product();
        sampleProduct.setName("Product 1");
        sampleProduct.setDescription("Description 1");
        sampleProduct.setPrice(100.0);
        sampleProduct.setStockQuantity(10);
        Mockito.when(this.productService.getProductById(1L)).thenReturn(ResponseEntity.ok(sampleProduct));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1", new Object[0]).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ((ProductService)Mockito.verify(this.productService)).getProductById(1L);
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product productToCreate = new Product();
        productToCreate.setName("Product 1");
        productToCreate.setDescription("Description 1");
        productToCreate.setPrice(100.0);
        productToCreate.setStockQuantity(10);
        Product createdProduct = new Product();
        createdProduct.setName("Product 1");
        createdProduct.setDescription("Description 1");
        createdProduct.setPrice(100.0);
        createdProduct.setStockQuantity(10);
        Mockito.when(this.productService.createProduct((Product)ArgumentMatchers.any(Product.class))).thenReturn(createdProduct);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/products", new Object[0]).contentType(MediaType.APPLICATION_JSON).content((new ObjectMapper()).writeValueAsString(productToCreate))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ((ProductService)Mockito.verify(this.productService)).createProduct((Product)ArgumentMatchers.any(Product.class));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product productToUpdate = new Product();
        productToUpdate.setName("Updated Product");
        productToUpdate.setDescription("Updated Description");
        productToUpdate.setPrice(150.0);
        productToUpdate.setStockQuantity(15);
        Mockito.when(this.productService.updateProduct(ArgumentMatchers.eq(1L), (Product)ArgumentMatchers.any(Product.class))).thenReturn(ResponseEntity.ok(productToUpdate));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/products/1", new Object[0]).contentType(MediaType.APPLICATION_JSON).content((new ObjectMapper()).writeValueAsString(productToUpdate))).andExpect(MockMvcResultMatchers.status().isOk());
        ((ProductService)Mockito.verify(this.productService)).updateProduct(ArgumentMatchers.eq(1L), (Product)ArgumentMatchers.any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Mockito.when(this.productService.deleteProduct(1L)).thenReturn(ResponseEntity.noContent().build());
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/1", new Object[0]).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent());
        ((ProductService)Mockito.verify(this.productService)).deleteProduct(1L);
    }
}