import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

public class Main {
    static String url = "https://student.mirea.ru";
    public static void main(String[] args) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url + "/media/photo/").get();
            Elements slides = doc.select("div.js-slide");

            for(Element slide : slides){
                Element photos = slide.child(1).child(0);
                parseImages(slide.child(2).attr("href"), photos.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void parseImages(String link, String dirName){
        String path = "d:\\ProjectsJava\\Practice\\photos\\";
        try {
            dirName = dirName.replaceAll("\"", "'");
            new File(path + dirName).mkdirs();
            Document document = Jsoup.connect(url + link).get();
            Elements images = document.select("div.row img.img-fluid");

            for (Element img : images){
                URL url2 = new URL(url + img.attr("src"));

                String filePath = url2.getFile();
                String name = path + dirName + filePath.substring(filePath.lastIndexOf("/"));
                InputStream in = url2.openStream();
                OutputStream out = new FileOutputStream(name);

                byte[] bytes = new byte[2048];
                int len;

                while ((len = in.read(bytes)) != -1){
                    out.write(bytes, 0, len);
                }
                in.close();
                out.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}