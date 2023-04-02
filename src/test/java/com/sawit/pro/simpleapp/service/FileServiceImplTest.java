package com.sawit.pro.simpleapp.service;

import com.sawit.pro.simpleapp.util.HtmlHelper;
import com.sawit.pro.simpleapp.util.StringHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class FileServiceTest {

    @Autowired
    FileService fileService;

    @Test
    void uploadAllImagesShoulSuccess() throws IOException, URISyntaxException, GeneralSecurityException {

        File directoryPath = new ClassPathResource("images").getFile();

        for (String s : Objects.requireNonNull(directoryPath.list())) {
            String id = fileService.upload(directoryPath.getAbsolutePath() + File.separator +  s);
            assertNotNull(id);
        }


    }

    @Test
    void readTextFromImage1ShoulSuccess() throws IOException {

        String result1 = fileService.readTextFromImage(new ClassPathResource("images/ImageWithWords1.jpg").getFile().getAbsolutePath(), "eng");
        String result2 = fileService.readTextFromImage(new ClassPathResource("images/ImageWithWords2.png").getFile().getAbsolutePath(), "eng");
        String result3 = fileService.readTextFromImage(new ClassPathResource("images/ImageWithWords3.jpg").getFile().getAbsolutePath(), "eng");
        String result4 = fileService.readTextFromImage(new ClassPathResource("images/ImageWithWords4.jpg").getFile().getAbsolutePath(), "chi_sim");

        String englishWords = StringHelper.getOnlyLetter(result1) + StringHelper.getOnlyLetter(result2) + StringHelper.getOnlyLetter(result3);

        String chineseWords = StringHelper.extractChineseWords(result4);
        englishWords =  englishWords + StringHelper.getOnlyLetter(StringHelper.removeChineseWords(result4));

        String html = HtmlHelper.HEADER +
                HtmlHelper.writeBodyText("English Words", englishWords) +
                HtmlHelper.FOOTER;

        fileService.write("./result-english.html", html);

        String htmlResult2 = HtmlHelper.HEADER +
                HtmlHelper.writeBodyText("Chinese Words", chineseWords, "black") +
                HtmlHelper.FOOTER;

        fileService.write("./result-chinese.html", htmlResult2);


    }

}