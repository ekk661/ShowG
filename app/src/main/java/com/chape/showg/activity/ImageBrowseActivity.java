package com.chape.showg.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chape.showg.R;
import com.chape.showg.adapter.ImageBrowsePagerAdapter;
import com.chape.showg.base.Picture;
import com.chape.showg.util.HttpUtil;
import com.chape.showg.util.MyApplication;
import com.chape.showg.util.ParseHtml;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ImageBrowseActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    //private ImageBrowsePagerAdapter mAdapter;
   // private List<String> mListUrl;
  private String url;
    private Intent intent;
    List<String> strlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browse);
        initView();
        initData();
    }
    private void initView(){
        mViewPager=(ViewPager)findViewById(R.id.viewpager);

    }
    private void initData(){
        intent=getIntent();
        url=intent.getStringExtra("picurl");
       //new getData().execute(url);
        getData(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // strlist.clear();
    }


    private void getData(final String  url){

        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> pictures=new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(url).get();
                  //  Element element=doc.getElementById("tu");
                 //   Elements titleLinks=element.getElementsByTag("a");
                   Elements titleLinks = doc.select("div.tu>p>img");
                  Log.e("cishu",""+titleLinks.size());
                 /*  for (int j = 0; j < titleLinks.size(); j++) {
                        String picurl = titleLinks.get(j).select("img").attr("name");
                      //  String picurl = titleLinks.get(j).select("img").attr("name");
                        Log.e("title",picurl);
                      pictures.add(picurl);
                    }*/
                   for(Element e:titleLinks){
                    String picurl=e.select("img").attr("name");
                    Log.e("tilte",picurl);
                    pictures.add(picurl);
                   }
                    emitter.onNext(pictures);
                } catch (IOException e) {
                   emitter.onError(e);
                }
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        mViewPager.setAdapter(new ImageBrowsePagerAdapter(strings));
                    }
                }/*new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                     // Toast.makeText(MyApplication.getContext(),s,Toast.LENGTH_LONG).show();
                  //  strlist.add(s);
                        Log.e("tttttt",s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                  //      mViewPager.setAdapter(new ImageBrowsePagerAdapter(strlist));
                  //      mViewPager.notifyAll();
                    }
                }*/);
    }
    private class getData extends AsyncTask<String,Integer,String> {
        @Override
        //
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        //
        protected String doInBackground(String... params) {


            return  HttpUtil.get(params[0]);
        }

        @Override
        //
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result!=null){

                //  String str=result.substring(result.indexOf("<div id=\"tu\" class=\"tu\">")+1, result.lastIndexOf("</div> <div class=\"tub\">"));
                String str= ParseHtml.getHtmlString(result);
                strlist=new ArrayList<>();
                strlist=ParseHtml.getAllImageUrlFromHtml(str);
             //   mAdapter=new ImageBrowsePagerAdapter(ImageBrowseActivity.this,strlist);
                mViewPager.setAdapter(new ImageBrowsePagerAdapter(strlist));
            }else {

            }



        }
    }

    static class ImageBrowsePagerAdapter extends PagerAdapter {
       // private Context context;
        private List<String> urls;

        public ImageBrowsePagerAdapter(List<String> urls) {
          //  this.context=context;
            this.urls=urls;
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
          //  View view = View.inflate(context, R.layout.item_image_browser, null);
           // PhotoView photoView=(PhotoView) view.findViewById(R.id.pv_show_image);
            PhotoView photoView=new PhotoView(container.getContext());
            String picUrl = urls.get(position);
            Uri u = Uri.parse(picUrl);
          //  photoView.setImageURI(u);
            Picasso.with(container.getContext())
                    .load(u)
                    .into(photoView);
            container.addView(photoView);
           // container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
           // container.addView(view);
          //  return view;
            return photoView;
        }
    }
}
