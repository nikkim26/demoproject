package com.example.demoproject;

import com.example.demoproject.Controller.BillController;
import com.example.demoproject.Entity.Bills;
import com.example.demoproject.Service.BillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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
class BillTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private BillService billService;
    @InjectMocks
    private BillController billController;
    private Bills bill;

    BillTest() {
    }

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void setup() {
        this.bill = new Bills();
        this.bill.setBillId(2L);
        this.bill.setOrderId((Long)null);
        this.bill.setTotalAmount(1100.0);
        this.bill.setInvoiceDate("2024-10-16");
        this.bill.setTaxAmount(120.0);
        this.bill.setShippingAmount(50.0);
    }

    @BeforeEach
    public void setup1() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new Object[]{this.billController}).build();
    }

    @Test
    public void testGetBillById() throws Exception {
        Mockito.when(this.billService.getBillById(2L)).thenReturn(ResponseEntity.ok(this.bill));
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/bills/2", new Object[0])).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        String expectedJson = "{\"billId\":2,\"orderId\":null,\"invoiceDate\":\"2024-10-16\",\"totalAmount\":1100.0,\"taxAmount\":120.0,\"shippingAmount\":50.0}";
        Assertions.assertEquals(expectedJson, responseBody);
    }

    @Test
    public void testCreateBill() throws Exception {
        Bills billToCreate = new Bills();
        billToCreate.setBillId(2L);
        billToCreate.setOrderId((Long)null);
        billToCreate.setInvoiceDate("2024-10-16");
        billToCreate.setTotalAmount(150.0);
        billToCreate.setTaxAmount(12.0);
        billToCreate.setShippingAmount(5.0);
        Mockito.when(this.billService.createBill((Bills)ArgumentMatchers.any(Bills.class))).thenReturn(billToCreate);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bills", new Object[0]).contentType(MediaType.APPLICATION_JSON).content((new ObjectMapper()).writeValueAsString(billToCreate))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Bills createdBill = (Bills)(new ObjectMapper()).readValue(responseBody, Bills.class);
        Assertions.assertEquals(billToCreate.getBillId(), createdBill.getBillId());
        Assertions.assertEquals(billToCreate.getOrderId(), createdBill.getOrderId());
        Assertions.assertEquals(billToCreate.getInvoiceDate(), createdBill.getInvoiceDate());
        Assertions.assertEquals(billToCreate.getTotalAmount(), createdBill.getTotalAmount());
        Assertions.assertEquals(billToCreate.getTaxAmount(), createdBill.getTaxAmount());
        Assertions.assertEquals(billToCreate.getShippingAmount(), createdBill.getShippingAmount());
        ((BillService)Mockito.verify(this.billService)).createBill((Bills)ArgumentMatchers.any(Bills.class));
    }

    @Test
    public void testUpdateBill() throws Exception {
        Mockito.when(this.billService.updateBill(1L, this.bill)).thenReturn(ResponseEntity.ok(this.bill));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bills/1", new Object[0]).contentType(MediaType.APPLICATION_JSON).content((new ObjectMapper()).writeValueAsString(this.bill))).andExpect(MockMvcResultMatchers.status().isOk());
        ((BillService)Mockito.verify(this.billService)).updateBill(1L, this.bill);
    }

    @Test
    public void testDeleteBill() throws Exception {
        Mockito.when(this.billService.deleteBill(1L)).thenReturn(ResponseEntity.ok().build());
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/bills/1", new Object[0])).andExpect(MockMvcResultMatchers.status().isOk());
        ((BillService)Mockito.verify(this.billService)).deleteBill(1L);
    }
}
