package com.chape.showg.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chape.showg.activity.ImageBrowseActivity;
import com.chape.showg.util.MyApplication;
import com.chape.showg.R;
import com.chape.showg.activity.WebviewActivity;
import com.chape.showg.listener.EndLessOnScrollListener;
import com.chape.showg.adapter.PictureAdapter;
import com.chape.showg.base.Picture;
import com.chape.showg.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class PictureFragment extends Fragment {
    private List<Picture> picList;
    private RecyclerView recyclerView;
    private PictureAdapter adapter;
    private View view;
   private Picture picture1=new Picture("http://www.33mn.net/47b5b0","http://s.syasn.com/www.2mmei.net/2mmei.com-741iht01-350x350.jpg");
   private Picture picture2=new Picture("http://www.33mn.net/f84eda","http://s.syasn.com/www.2mmei.net/2mmei.com-j121rf01-350x350.jpg");
    private SwipeRefreshLayout swipeRefreshLayout;
  private GridLayoutManager gridLayoutManager;

    @Nullable



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.picture,container,false);

        initRecyclerView();

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }







    private void initRecyclerView(){
        picList=new ArrayList<>();
        gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView=(RecyclerView) view.findViewById(R.id.pic_rv);
        recyclerView.setLayoutManager(gridLayoutManager);
        picList.add(picture1);
        picList.add(picture2);
        adapter=new PictureAdapter(picList,getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               // Toast.makeText(MyApplication.getContext(),picList.get(position).getContent_url(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MyApplication.getContext(),ImageBrowseActivity.class);
                intent.putExtra("picurl",picList.get(position).getContent_url());
                startActivity(intent);
            }
        });

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.refresh);
        recyclerView.addOnScrollListener(new EndLessOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                for (int i =0; i < 2; i++){
                    picList.add(picture1);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
            new getData().execute("http://10.0.2.2/picture.json");
            }
        });
    }

    private class getData extends AsyncTask<String,Integer,String>{
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
                Gson gson=new Gson();

                List<Picture> more=gson.fromJson(result,new TypeToken<List<Picture>>(){}.getType());

                picList.addAll(more);

                adapter.notifyDataSetChanged();
            }else {

            }

            swipeRefreshLayout.setRefreshing(false);

        }
    }
}
