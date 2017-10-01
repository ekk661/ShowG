package com.chape.showg.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chape.showg.R;
import com.chape.showg.adapter.VideoAdapter;
import com.chape.showg.base.Video;
import com.chape.showg.listener.EndVideoOnScrollListener;
import com.chape.showg.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class VideoFragment extends Fragment {
    private List<Video> mListVideo;
    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;

    private View view;
    private Video video1=new Video("http://rbv01.ku6.com/FbWgxVgcAki2ntV94N5t3g.mp4");
    private Video video2=new Video("http://rbv01.ku6.com/Jd0dyDVsf80mAf2swCYKCA.mp4");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.video,container,false);
        initRecyclerView();
        return view;
    }
   private void initRecyclerView(){
       mListVideo=new ArrayList<>();
       mLinearLayoutManager=new LinearLayoutManager(getContext());
       mRecyclerView=(RecyclerView)view.findViewById(R.id.video_rv);
       mRecyclerView.setLayoutManager(mLinearLayoutManager);
       mListVideo.add(video1);
       mListVideo.add(video2);
       mVideoAdapter=new VideoAdapter(mListVideo,getContext());
       mRecyclerView.setAdapter(mVideoAdapter);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.vrefresh);
       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               swipeRefreshLayout.setRefreshing(true);
               new getData().execute("http://10.0.2.2/video.json");
           }
       });
        mRecyclerView.addOnScrollListener(new EndVideoOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                for(int i=0;i<2;i++){
                    mListVideo.add(video1);
                    mListVideo.add(video2);
                    mVideoAdapter.notifyDataSetChanged();
                }
            }
        });
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
                Gson gson=new Gson();

                List<Video> more=gson.fromJson(result,new TypeToken<List<Video>>(){}.getType());

                mListVideo.addAll(more);

                mVideoAdapter.notifyDataSetChanged();
            }else {

            }

            swipeRefreshLayout.setRefreshing(false);

        }
    }
}
