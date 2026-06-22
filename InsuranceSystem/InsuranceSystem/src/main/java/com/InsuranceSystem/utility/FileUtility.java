package com.InsuranceSystem.utility;
import com.InsuranceSystem.exceptions.FileNotFoundException;

import com.InsuranceSystem.exceptions.FileInvalidExtensionException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUtility {
    public static void validateFile(MultipartFile file){
        if(file == null || file.isEmpty())
            throw new FileNotFoundException("Please select file to upload");

        List<String> allowedExts = List.of("png", "jpeg", "jpg", "pdf", "docx", "pages");

        String filename = file.getOriginalFilename();
        if(filename == null || !filename.contains("."))
            throw new FileInvalidExtensionException("Invalid file format");

        String ext = filename.split("\\.")[1];

        if(!allowedExts.contains(ext))
            throw new FileInvalidExtensionException(ext + " not allowed");
    }
}
