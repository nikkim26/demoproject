package com.example.demoproject.Service;




import java.io.IOException;
import java.util.Optional;

import com.example.demoproject.Entity.ImageData;
import com.example.demoproject.Util.ImageUtil;
import com.example.demoproject.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
    @Autowired
    private StorageRepository repository;

    public StorageService() {
    }
    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = (ImageData)this.repository.save(ImageData.builder().name(file.getOriginalFilename()).type(file.getContentType()).imageData(ImageUtil.compressImage(file.getBytes())).build());
        return imageData != null ? "file uploaded successfully : " + file.getOriginalFilename() : null;
    }
//

    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = this.repository.findByName(fileName);
        byte[] images = ImageUtil.decompressImage(((ImageData)dbImageData.get()).getImageData());
        return images;
    }
}
