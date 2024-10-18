package com.example.demoproject;

import com.example.demoproject.Controller.BillController;
import com.example.demoproject.Entity.Bills;
import com.example.demoproject.Service.BillService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

	@SpringBootTest
	@AutoConfigureMockMvc
	@ExtendWith({MockitoExtension.class})

	class DemoprojectApplicationTests {


		@Autowired
		private MockMvc mockMvc;
		@Mock
		private BillService billService;
		@InjectMocks
		private BillController billController;
		private Bills bill;




		@Test
		void contextLoads() {
		}

		@BeforeEach
		public void setup() {
			this.bill = new Bills();
			this.bill.setBillId(2L);
			this.bill.setOrderId((Long)null);
			this.bill.setTotalAmount(150.0);
			this.bill.setInvoiceDate("2024-10-16");
			this.bill.setTaxAmount(12.0);
			this.bill.setShippingAmount(5.0);
		}

		@BeforeEach
		public void setup1() {
			MockitoAnnotations.openMocks(this);
			this.mockMvc = MockMvcBuilders.standaloneSetup(new Object[]{this.billController}).build();
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
