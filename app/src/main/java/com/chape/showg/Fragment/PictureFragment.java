package com.chape.showg.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chape.showg.activity.ImageBrowseActivity;
import com.chape.showg.adapter.PicturebaseAdapter;
import com.chape.showg.util.MyApplication;
import com.chape.showg.R;
import com.chape.showg.activity.WebviewActivity;
import com.chape.showg.listener.EndLessOnScrollListener;
import com.chape.showg.adapter.PictureAdapter;
import com.chape.showg.base.Picture;
import com.chape.showg.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/19.
 */

public class PictureFragment extends Fragment {
   private List<Picture> picList;
    private RecyclerView recyclerView;
    private PicturebaseAdapter adapter;
    private View view;
   private SwipeRefreshLayout swipeRefreshLayout;
  private GridLayoutManager gridLayoutManager;
  private int page=1;

    @Nullable



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.picture,container,false);

        initRecyclerView();
        initData();
        initAdapter();
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


private void initData(){
    picList=new ArrayList<>();
    getData("10");

}
private void initAdapter(){
    adapter=new PicturebaseAdapter(R.layout.pic,picList);
   // adapter.openLoadAnimation();
    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
           // Toast.makeText(MyApplication.getContext(),picList.get(position).getContent_url(),Toast.LENGTH_LONG).show();
           Intent intent=new Intent(MyApplication.getContext(),ImageBrowseActivity.class);
            intent.putExtra("picurl",picList.get(position).getContent_url());
            startActivity(intent);

        }
    });
    recyclerView.setAdapter(adapter);

}




    private void initRecyclerView(){

        gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView=(RecyclerView) view.findViewById(R.id.pic_rv);
        recyclerView.setLayoutManager(gridLayoutManager);


      /*  adapter.setOnItemClickListener(new PictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               Toast.makeText(MyApplication.getContext(),picList.get(position).getContent_url(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MyApplication.getContext(),ImageBrowseActivity.class);
                intent.putExtra("picurl",picList.get(position).getContent_url());
                startActivity(intent);
            }
        });*/

    //    swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.refresh);
     /*   recyclerView.addOnScrollListener(new EndLessOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                String str=""+page;
               getData(str);
               page++;
            }
        });*/
     /*   swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
        //    new getData().execute("http://10.0.2.2/picture.json");
                picList.clear();
                getData("10");
            }
        });*/
    }
private void getData(final String page){
    Observable.create(new ObservableOnSubscribe<Picture>() {
        @Override
        public void subscribe(ObservableEmitter<Picture> emitter) throws Exception {
            try {
                Document doc = Jsoup.connect("http://www.33mn.net/ns/"+page).get();
                Elements titleLinks = doc.select("div.hm");    //解析来获取每条新闻的标题与链接地址
                for (int j = 0; j < titleLinks.size(); j++) {

                    String title = "http://www.33mn.net"+titleLinks.get(j).select("a").attr("href");
                    String picurl = titleLinks.get(j).select("img").attr("name");
                   //     Log.e("title", picUrl.url);
                    Picture picUrl = new Picture(title,picurl);

                    emitter.onNext(picUrl);

                }
                emitter.onComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Picture>() {
                           @Override
                           public void onSubscribe(Disposable d) {

                           }

                           @Override
                           public void onNext(Picture picture) {
                            picList.add(picture);
                           }

                           @Override
                           public void onError(Throwable e) {

                           }

                           @Override
                           public void onComplete() {
                               adapter.notifyDataSetChanged();
                             //  adapter=new PictureAdapter(picList,getContext());
                              // recyclerView.setAdapter(adapter);
                             //  adapter.notifyDataSetChanged();
                              // swipeRefreshLayout.setRefreshing(false);

                           }
                       }
            );

}

}
