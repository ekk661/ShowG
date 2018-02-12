package com.chape.showg.util;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2018/1/30.
 */

public class JsoupUtil {
    private static  String url="http://www.mzitu.com/";
    public static void getDoc(){
        try {
            Document document=Jsoup.connect(url).get();
            Elements elements=document.select("a[href]");
            for (Element e :elements){
                System.out.print(e.attr("abs:href"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
