package com.example.demoproject.repository;




import java.util.Optional;

import com.example.demoproject.Entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByName(String fileName);
}