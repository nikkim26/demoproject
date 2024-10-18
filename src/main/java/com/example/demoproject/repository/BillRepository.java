package com.example.demoproject.repository;




import java.util.List;

import com.example.demoproject.Entity.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bills, Long> {
    List<Bills> findByOrderId(Long orderId);
}