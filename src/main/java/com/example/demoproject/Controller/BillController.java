package com.example.demoproject.Controller;



import com.example.demoproject.Entity.Bills;
import com.example.demoproject.Service.BillService;
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
@RequestMapping({"/api/bills"})
public class BillController {
    @Autowired
    private BillService billService;

    public BillController() {
    }

    @PostMapping
    public Bills createBill(@RequestBody Bills bills) {
        return this.billService.createBill(bills);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Bills> getBillById(@PathVariable Long id) {
        return this.billService.getBillById(id);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Bills> updateBill(@PathVariable Long id, @RequestBody Bills bills) {
        return this.billService.updateBill(id, bills);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        return this.billService.deleteBill(id);
    }
}
