package com.sawit.pro.simpleapp.util;

public class HtmlHelper {

    public static String HEADER =
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "   <title>HTML Font color</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "   <h1>RESULT</h1>\n";

    public static String FOOTER =
                    "</body>\n" +
                    "</html>";

    public static String writeBodyText(String title, String sentence, String color){
       return  "   <p>"+title+"</p>\n"+
               "   <p style=\"color:"+color+"\">"+sentence+"</p>\n";
    }

    public static String writeBodyText(String title, String sentence){
        StringBuilder builder = new StringBuilder();
        builder.append("   <p>").append(title).append("</p>\n");

        for (char c : sentence.toCharArray()) {
            if (c == 'o' || c == 'O') {
                builder.append("<span style=\"color: blue;\">").append(c).append("</span>");
            } else {
                builder.append(c);
            }
        }

        builder.append(".</p>");
        return  builder.toString();
    }

}
