package com.chape.showg.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chape.showg.R;
import com.chape.showg.adapter.ImageBrowsePagerAdapter;
import com.chape.showg.util.HttpUtil;
import com.chape.showg.util.MyApplication;
import com.chape.showg.util.ParseHtml;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        new getData().execute(url);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        strlist.clear();
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
