package com.example.demoproject.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Bill")
@Data
@NoArgsConstructor
public class Bills {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long billId;
    @Column(
            name = "order_id"
    )
    private Long orderId;
    private String invoiceDate;
    private Double totalAmount;
    private Double taxAmount;
    private Double shippingAmount;
}
