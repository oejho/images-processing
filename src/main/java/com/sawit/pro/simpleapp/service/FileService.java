package com.sawit.pro.simpleapp.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

public interface FileService {

    String upload(String path) throws IOException, URISyntaxException, GeneralSecurityException;

    String readTextFromImage(String path, String lang) throws IOException;

    void write(String fileName, String content);


}
