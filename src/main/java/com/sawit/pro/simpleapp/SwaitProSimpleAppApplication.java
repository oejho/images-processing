package com.sawit.pro.simpleapp;

import com.sawit.pro.simpleapp.service.FileService;
import com.sawit.pro.simpleapp.util.HtmlHelper;
import com.sawit.pro.simpleapp.util.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Objects;


@Slf4j
@SpringBootApplication
public class SwaitProSimpleAppApplication implements CommandLineRunner {

    @Autowired
    FileService fileService;

    public static void main(String[] args) {
        SpringApplication.run(SwaitProSimpleAppApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        File directoryPath = new ClassPathResource("images").getFile();

        for (String s : Objects.requireNonNull(directoryPath.list())) {
            String id = fileService.upload(directoryPath.getAbsolutePath() + File.separator +  s);
            log.info("success upload file ");
            log.info("id -> "+id);
        }

        String result1 = fileService.readTextFromImage(new ClassPathResource("images/ImageWithWords1.jpg").getFile().getAbsolutePath(), "eng");
        String result2 = fileService.readTextFromImage(new ClassPathResource("images/ImageWithWords2.png").getFile().getAbsolutePath(), "eng");
        String result3 = fileService.readTextFromImage(new ClassPathResource("images/ImageWithWords3.jpg").getFile().getAbsolutePath(), "eng");
        String result4 = fileService.readTextFromImage(new ClassPathResource("images/ImageWithWords4.jpg").getFile().getAbsolutePath(), "chi_sim");

        String englishWords = StringHelper.getOnlyLetter(result1) + StringHelper.getOnlyLetter(result2) + StringHelper.getOnlyLetter(result3);

        String chineseWords = StringHelper.extractChineseWords(result4);
        englishWords =  englishWords + StringHelper.getOnlyLetter(StringHelper.removeChineseWords(result4));

        String html = HtmlHelper.HEADER +
                HtmlHelper.writeBodyTextHighlight("English Words", englishWords, "o") +
                HtmlHelper.FOOTER;

        fileService.write("./result-english.html", html);

        String htmlResult2 = HtmlHelper.HEADER +
                HtmlHelper.writeBodyText("Chinese Words", chineseWords, "black") +
                HtmlHelper.FOOTER;

        fileService.write("./result-chinese.html", htmlResult2);
    }
}
