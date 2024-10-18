package com.example.demoproject.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.util.Arrays;

import lombok.*;

@Entity
@Table(name = "ImageData")
@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageData {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String type;
    @Lob
    @Column(
            name = "imagedata",
            length = 1000
    )
    private byte[] imageData;

}