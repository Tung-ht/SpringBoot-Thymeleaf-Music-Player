package com.xpotify.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Log4j2
public class FileUtils {
    // return link of file
    public static String saveFile(String uploadDir, String fileName,
                                  MultipartFile multipartFile) throws IOException {
        String fileLink = null;
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            fileLink = "/" + uploadDir + "/" + fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        return fileLink;
    }

    public static boolean deleteFile(String filePath) {
        try {
            // work with : xpotify ; not /xpotify
            String realPath = filePath.substring(1, filePath.length());
            File f = new File(realPath);
            if (f.delete()) {
                log.debug(f.getName() + " was deleted!");
                return true;
            } else {
                log.debug("Deleted " + f.getName() + " failed!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
