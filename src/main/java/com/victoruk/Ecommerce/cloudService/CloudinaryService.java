package com.victoruk.Ecommerce.cloudService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



@Service
public class CloudinaryService {


        private final Cloudinary cloudinary;

        // Constructor to initialize Cloudinary with values from application properties or environment variables
        public CloudinaryService(@Value("${cloudinary.cloud_name}") String cloudName,
                                 @Value("${cloudinary.api_key}") String apiKey,
                                 @Value("${cloudinary.api_secret}") String apiSecret) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("cloud_name", cloudName);
            valuesMap.put("api_key", apiKey);
            valuesMap.put("api_secret", apiSecret);
            this.cloudinary = new Cloudinary(valuesMap);
        }


    public String extractPublicId(Map<String, Object> uploadResult) {
        // Check if the result contains the "public_id" key
        if (uploadResult.containsKey("public_id")) {
            return (String) uploadResult.get("public_id");
        }
        throw new RuntimeException("Public ID not found in Cloudinary response");
    }


    // Method to upload a file to Cloudinary
    public Map<String, Object> upload(MultipartFile multipartFile) throws Exception {
        File file = convert(multipartFile);

        try {
            // Upload file to Cloudinary
            Map<String, Object> result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            return result;  // Return the result for further processing
        } catch (Exception e) {
            throw new IOException("Cloudinary upload failed: " + e.getMessage());
        } finally {
            // Delete the temporary file after upload
            if (!Files.deleteIfExists(file.toPath())) {
                throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
            }
        }
    }

    public void delete(String publicId) throws Exception {
        try {
            // Attempt to delete the image using Cloudinary's destroy method
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new IOException("Cloudinary deletion failed: " + e.getMessage());
        }
    }

//        // Method to upload a file to Cloudinary
//        public Map<String, Object> upload(MultipartFile multipartFile) throws Exception {
//            File file = convert(multipartFile);
//
//            // Upload file to Cloudinary
//            Map<String, Object> result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
//
//            // Delete the temporary file after upload
//            if (!Files.deleteIfExists(file.toPath())) {
//                throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
//            }
//
//            return result;
//        }

        // Private method to convert MultipartFile to a File object
        private File convert(MultipartFile multipartFile) throws Exception {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            try (FileOutputStream fo = new FileOutputStream(file)) {
                fo.write(multipartFile.getBytes());
            }
            return file;
        }

}
