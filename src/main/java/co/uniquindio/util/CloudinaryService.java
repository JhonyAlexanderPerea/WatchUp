package co.uniquindio.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CloudinaryService {


    private final String cloudName;
    private final String apiKey;
    private final String apiSecret;
    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret
    ) {
        this.cloudName = cloudName;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        config.put("secure", "true");

        this.cloudinary = new Cloudinary(config);
    }
    @Async("taskExecutor")
    public CompletableFuture<String> uploadFile(MultipartFile file) {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "reportes") // Opcional: carpeta en Cloudinary
            );
            return  CompletableFuture.completedFuture((String) uploadResult.get("url"));
        } catch (IOException e) {
            System.out.println("Error al subir el archivo: " + e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}
