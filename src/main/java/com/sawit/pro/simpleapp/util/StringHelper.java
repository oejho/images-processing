package com.sawit.pro.simpleapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    public static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]+");

    public static String extractChineseWords(String sentence) {
        Matcher matcher = CHINESE_PATTERN.matcher(sentence);

        StringBuilder chineseWords = new StringBuilder();
        while (matcher.find()) {
            chineseWords.append(matcher.group());
        }

        return chineseWords.toString();
    }

    public static String removeChineseWords(String sentence) {

        return CHINESE_PATTERN.matcher(sentence).replaceAll("");
    }

    public static String getOnlyLetter(String sentence) {
        return sentence.replaceAll("[^a-zA-Z]", " ");
    }

}
