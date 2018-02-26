package com.chape.showg.mvp.data.source;

import com.chape.showg.base.Picture;
import com.chape.showg.mvp.data.source.remote.DataSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 作者： Administrator on 2018/2/21.
 * 邮箱：773156959@qq.com
 */

public class Repository implements DataSource {
    @Override
    public Observable<List<Picture>> getData() {
        return Observable.create(new ObservableOnSubscribe<List<Picture>>() {
            List<Picture> mPictures=new ArrayList<>();
            @Override
            public void subscribe(ObservableEmitter<List<Picture>> emitter) throws Exception {
                try {
                    Document doc = Jsoup.connect("http://www.33mn.net/ns/" +"2").get();
                    Elements titleLinks = doc.select("div.hm");    //解析来获取每条新闻的标题与链接地址
                    for (int j = 0; j < titleLinks.size(); j++) {

                        String title = "http://www.33mn.net" + titleLinks.get(j).select("a").attr("href");
                        String picurl = titleLinks.get(j).select("img").attr("name");
                        //     Log.e("title", picUrl.url);
                        Picture picUrl = new Picture(title, picurl);
                        mPictures.add(picUrl);
                    }
                    emitter.onNext(mPictures);
                    emitter.onComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }
}
