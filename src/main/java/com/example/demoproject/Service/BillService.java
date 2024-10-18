package com.example.demoproject.Service;




import java.util.List;

import com.example.demoproject.Entity.Bills;
import com.example.demoproject.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public BillService() {
    }

    public Bills createBill(Bills bills) {
        return (Bills)this.billRepository.save(bills);
    }

    public ResponseEntity<Bills> getBillById(Long id) {
        return (ResponseEntity)this.billRepository.findById(id).map((bill) -> {
            return ResponseEntity.ok().body(bill);
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Bills> updateBill(Long id, Bills bills) {
        return (ResponseEntity)this.billRepository.findById(id).map((bill) -> {
            bill.setInvoiceDate(bills.getInvoiceDate());
            bill.setTotalAmount(bills.getTotalAmount());
            bill.setTaxAmount(bills.getTaxAmount());
            bill.setShippingAmount(bills.getShippingAmount());
            return ResponseEntity.ok((Bills)this.billRepository.save(bill));
        }).orElseThrow(() -> {
            return new RuntimeException("Bill not found");
        });
    }

    public ResponseEntity<Void> deleteBill(Long id) {
        return (ResponseEntity)this.billRepository.findById(id).map((bill) -> {
            this.billRepository.delete(bill);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> {
            return new RuntimeException("Bill not found");
        });
    }

    public List<Bills> getBillsByOrderId(Long orderId) {
        return this.billRepository.findByOrderId(orderId);
    }
}