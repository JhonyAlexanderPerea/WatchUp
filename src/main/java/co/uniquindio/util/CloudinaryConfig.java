package co.uniquindio.util;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;

public class CloudinaryConfig {
    private static final Dotenv dotenv = Dotenv.configure().load();

    public static final String CLOUDINARY_URL = dotenv.get("CLOUDINARY_URL");
    Cloudinary cloudinary = new Cloudinary(CloudinaryConfig.CLOUDINARY_URL);

    public static String getCloudinaryUrl() {
        return dotenv.get("CLOUDINARY_URL");
    }
}