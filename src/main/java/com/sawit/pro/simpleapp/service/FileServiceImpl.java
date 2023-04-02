package com.sawit.pro.simpleapp.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import static com.sawit.pro.simpleapp.service.Google.*;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(String path) throws IOException, GeneralSecurityException {

        File fileMetadata = new File();
        // File's content.
        java.io.File filePath = new java.io.File(path);
        fileMetadata.setName(filePath.getName());

        boolean exists = filePath.exists();

        if (!exists){
            throw new FileNotFoundException("file on path "+path+ "is not found");
        }
        // Specify media type and file-path for file.
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        try {
            File file = getDriveService().files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + file.getId());
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }


    }

    @Override
    public String readTextFromImage(String path, String lang) {
        java.io.File imageFile = new java.io.File(path);
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage(lang);
        try {
            return tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            System.out.println("error" + e.getMessage());
            return null;
        }

    }

    @Override
    public void write(String fileName, String content) {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            // Write the HTML content to the file
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
            System.out.println("File written successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


}
