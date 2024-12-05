package com.um.inventory.util;

import java.sql.SQLException;
import java.util.Base64;

public class ImageUtil {
    public static byte[] convertToBytes(String base64String) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return decodedBytes;
    }

    public static String encodeToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
